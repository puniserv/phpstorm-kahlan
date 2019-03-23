package programRunner;

import com.intellij.execution.runners.BasicProgramRunner;
import org.jetbrains.annotations.NotNull;

public class KahlanRunner extends BasicProgramRunner {
    @NotNull
    @Override
    public String getRunnerId() {
        return "KahlanRunner";
    }
}
