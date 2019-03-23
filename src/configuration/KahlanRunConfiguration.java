package configuration;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.Executor;
import com.intellij.execution.configurations.RunConfigurationBase;
import com.intellij.execution.configurations.RuntimeConfigurationException;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SettingsEditor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

import programRunner.RunProfileState;

public class KahlanRunConfiguration extends RunConfigurationBase {

    KahlanRunConfiguration(@NotNull Project project, @Nullable KahlanConfigurationFactory factory, @Nullable String name) {
        super(project, factory, name);
    }

    @Nullable
    @Override
    public RunProfileState getState(@NotNull Executor executor, @NotNull ExecutionEnvironment executionEnvironment) throws ExecutionException {
        return new RunProfileState(executionEnvironment);
    }

    @NotNull
    @Override
    public SettingsEditor<? extends com.intellij.execution.configurations.RunConfiguration> getConfigurationEditor() {
        return new SettingsEditor<com.intellij.execution.configurations.RunConfiguration>() {
            @Override
            protected void resetEditorFrom(com.intellij.execution.configurations.RunConfiguration s) {
                int x = 1;
            }

            @Override
            protected void applyEditorTo(com.intellij.execution.configurations.RunConfiguration s) throws ConfigurationException {
                int x = 1;
            }

            @NotNull
            @Override
            protected JComponent createEditor() {
                JLabel jLabel = new JLabel("There are no configuration settings for kahlan.");
                FlowLayout flowLayout = new FlowLayout();
                flowLayout.addLayoutComponent("label", jLabel);
                JPanel jPanel = new JPanel(flowLayout);
                jPanel.add(new JLabel("Kahlan Bin path. Default vendor/bin/kahlan"));
                jPanel.add(getKahlanBinPathField());
                return jPanel;
            }

            @NotNull
            private JTextField getKahlanBinPathField() {
                JTextField jTextField = new JTextField();
                jTextField.setName("kahlanBinPath");
                return jTextField;
            }
        };
    }

    @Override
    public void checkConfiguration() throws RuntimeConfigurationException {

    }

}
