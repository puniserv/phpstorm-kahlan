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
import com.intellij.openapi.project.Project;
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

    public static void runScript(String path, String... args) {
        try {
            String[] cmd = new String[args.length + 1];
            cmd[0] = path;
            int count = 0;
            for (String s : args) {
                cmd[++count] = args[count - 1];
            }
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            try {
                process.waitFor();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            while (bufferedReader.ready()) {
                System.out.println("Received from script: " + bufferedReader.readLine());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }
    }

    @NotNull
    @Override
    protected ProcessHandler startProcess() throws ExecutionException {
        ConfigurationPerRunnerSettings configurationSettings = environment.getConfigurationSettings();
        GeneralCommandLine commandLine = new GeneralCommandLine(
                environment.getProject().getBaseDir().getCanonicalPath() + "/protected/vendors/bin/kahlan"
        ); //project.getConfig().binaryPath()
//        commandLine.addParameter("run");
//        commandLine.addParameter("--config=phpspec.yml");
//        commandLine.addParameter("--format=teamcity");
        commandLine.setWorkDirectory(environment.getProject().getBaseDir().getCanonicalPath() + "/protected");
        processHandler = new OSProcessHandler(
                commandLine
        );
//        runScript(environment.getProject().getBaseDir().getCanonicalPath() + "/vendor/bin/kahlan.bat");
        return processHandler;
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
