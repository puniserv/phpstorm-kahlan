import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;

public class RunKahlanAction extends AnAction {
    public RunKahlanAction() {
        super("Hello");
    }

    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Messages.showMessageDialog(
                project,
                "Hello world!",
                "Greeting",
                Messages.getInformationIcon()
        );
    }
}