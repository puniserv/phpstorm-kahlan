package configuration;

import com.intellij.execution.configurations.ConfigurationTypeBase;

import javax.swing.*;

public class KahlanConfigurationType extends ConfigurationTypeBase {
    protected KahlanConfigurationType() {
        super(
                "Kahlan",
                "Kahlan",
                "Kahlan tests and stuff",
                new ImageIcon("META-INF/pluginIcon.svg")
        );
        addFactory(new KahlanConfigurationFactory(this));
    }
}
