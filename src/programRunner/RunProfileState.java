package programRunner;

import com.intellij.execution.*;
import com.intellij.execution.configurations.CommandLineState;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        GeneralCommandLine commandLine = new GeneralCommandLine(getScriptPathByOS());
        commandLine.setWorkDirectory(environment.getProject().getBaseDir().getCanonicalPath());
        processHandler = new OSProcessHandler(
                commandLine
        );
        return processHandler;
    }

    @NotNull
    private String getScriptPathByOS() throws ExecutionException {
        if (System.getProperty("os.name").startsWith("Windows")) {
            return environment.getProject().getBaseDir().getCanonicalPath() + "/vendor/bin/kahlan.bat";
        }
        throw new ExecutionException("Not supported");
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
                        environment.getRunnerAndConfigurationSettings().getConfiguration(),
                        "kahlan",
                        environment.getExecutor()
                )
        );
    }

}
