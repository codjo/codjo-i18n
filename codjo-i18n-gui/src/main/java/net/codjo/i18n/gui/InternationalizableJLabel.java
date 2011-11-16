package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import javax.swing.JLabel;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class InternationalizableJLabel extends AbstractInternationalizableComponent<JLabel> {

    private Reference<JLabel> reference;


    public InternationalizableJLabel(String key, JLabel label) {
        super(key);
        this.reference = new WeakReference<JLabel>(label);
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        JLabel label = reference.get();
        if (label == null) {
            return;
        }
        label.setText(translator.translate(getKey(), language));
    }


    public JLabel getComponent() {
        return reference.get();
    }
}
