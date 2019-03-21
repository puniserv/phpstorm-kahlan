package configuration;

import com.intellij.execution.configurations.ConfigurationTypeBase;
import provider.Icons;

import javax.swing.*;

public class KahlanConfigurationType extends ConfigurationTypeBase {
    protected KahlanConfigurationType() {
        super(
                "Kahlan",
                "Kahlan",
                "Kahlan tests and stuff",
                Icons.MainIcon20
        );
        addFactory(new KahlanConfigurationFactory(this));
    }
}
