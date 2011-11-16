package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import javax.swing.JInternalFrame;
import java.lang.ref.WeakReference;
import java.lang.ref.Reference;

public class InternationalizableJInternalFrame extends AbstractInternationalizableComponent<JInternalFrame> {

    private Reference<JInternalFrame> reference;


    public InternationalizableJInternalFrame(String key, JInternalFrame frame) {
        super(key);
        this.reference = new WeakReference<JInternalFrame>(frame);
    }


    public JInternalFrame getComponent() {
        return reference.get();
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        JInternalFrame frame = reference.get();
        if (frame == null) {
            return;
        }
        frame.setTitle(translator.translate(getKey(), language));
    }
}
