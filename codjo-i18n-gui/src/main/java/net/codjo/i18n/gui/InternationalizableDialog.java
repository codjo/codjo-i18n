package net.codjo.i18n.gui;
import java.awt.Dialog;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;

public class InternationalizableDialog extends AbstractInternationalizableComponent<Dialog> {

    private Reference<Dialog> reference;


    public InternationalizableDialog(String key, Dialog dialog) {
        super(key);
        this.reference = new WeakReference<Dialog>(dialog);
    }


    public Dialog getComponent() {
        return reference.get();
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        Dialog dialog = reference.get();
        if (dialog == null) {
            return;
        }
        dialog.setTitle(translator.translate(getKey(), language));
    }
}
