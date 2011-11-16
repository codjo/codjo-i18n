package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import javax.swing.JTabbedPane;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class InternationalizableJTabbedPane extends AbstractInternationalizableComponent<JTabbedPane> {

    private Reference<JTabbedPane> reference;
    private String[] tabKeys;


    public InternationalizableJTabbedPane(String tabbedPaneKey, String[] tabKeys, JTabbedPane tabbedPane) {
        super(tabbedPaneKey);
        this.reference = new WeakReference<JTabbedPane>(tabbedPane);
        this.tabKeys = tabKeys;
    }


    public JTabbedPane getComponent() {
        return reference.get();
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        JTabbedPane tabbedPane = reference.get();
        if (null == tabbedPane) {
            return;
        }
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setTitleAt(i, translator.translate(tabKeys[i], language));
        }
    }
}