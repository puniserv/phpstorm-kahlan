package configuration;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class KahlanSettingsEditor extends SettingsEditor {

    @Override
    protected void resetEditorFrom(@NotNull Object o) {
        System.out.println("KahlanSettingsEditor.resetEditorFrom");
    }

    @Override
    protected void applyEditorTo(@NotNull Object o) throws ConfigurationException {
        System.out.println("KahlanSettingsEditor.applyEditorTo");
    }

    @NotNull
    @Override
    protected JComponent createEditor() {
        return new JPanel();
    }
}
