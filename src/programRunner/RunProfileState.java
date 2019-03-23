package programRunner;

import com.intellij.execution.*;
import com.intellij.execution.configurations.CommandLineState;
import com.intellij.execution.configurations.ConfigurationPerRunnerSettings;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.runners.ProgramRunner;
import com.intellij.execution.testframework.sm.SMTestRunnerConnectionUtil;
import com.intellij.execution.testframework.sm.runner.SMTRunnerConsoleProperties;
import com.intellij.execution.ui.ConsoleView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class RunProfileState extends CommandLineState {

    private final ExecutionEnvironment environment;
    private ProcessHandler processHandler;

    public RunProfileState(ExecutionEnvironment environment) {
        super(environment);
        this.environment = environment;
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        ConfigurationPerRunnerSettings configurationSettings = environment.getConfigurationSettings();
        GeneralCommandLine commandLine = new GeneralCommandLine(getScriptPathByOS());
        commandLine.setWorkDirectory(environment.getProject().getBaseDir().getCanonicalPath());
        processHandler = new OSProcessHandler(
                commandLine
        );
        return processHandler;
    }

    @NotNull
    private String getScriptPathByOS() {
        String canonicalPath = environment.getProject().getBaseDir().getCanonicalPath();
        String vendorsBin = canonicalPath + "/vendor/bin/";
        if (System.getProperty("os.name").startsWith("Windows")) {
            return vendorsBin + "kahlan.bat";
        }
        return  vendorsBin + "kahlan";
    }

    @Override
    @NotNull
    public ExecutionResult execute(@NotNull final Executor executor, @NotNull final ProgramRunner runner) throws ExecutionException {
        final ProcessHandler processHandler = startProcess();
        final ConsoleView console = createConsole(executor);
        return new DefaultExecutionResult(console, processHandler, createActions(console, processHandler, executor));
    }

    @Nullable
    @Override
    protected ConsoleView createConsole(@NotNull final Executor executor) throws ExecutionException {
        return SMTestRunnerConnectionUtil.createAndAttachConsole(
                "kahlan",
                processHandler,
                new SMTRunnerConsoleProperties(
                        Objects.requireNonNull(environment.getRunnerAndConfigurationSettings()).getConfiguration(),
                        "kahlan",
                        environment.getExecutor()
                )
        );
    }

}
