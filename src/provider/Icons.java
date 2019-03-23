package provider;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class Icons {

    public static final Icon MainIcon20 = load("/icons/pluginIcon20.svg");
    public static final Icon MainIcon40 = load("/icons/pluginIcon40.svg");

    private static Icon load(String path) {
        try {
            return IconLoader.getIcon(path, Icons.class);
        } catch (IllegalStateException e) {
            return null;
        }
    }
}
