package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class InternationalizableJPanel extends AbstractInternationalizableComponent<JPanel> {

    private Reference<JPanel> reference;


    public InternationalizableJPanel(String borderKey, JPanel panel) {
        super(borderKey);
        this.reference = new WeakReference<JPanel>(panel);
    }


    public JPanel getComponent() {
        return reference.get();
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        JPanel panel = reference.get();
        if (panel == null) {
            return;
        }
        Border border = panel.getBorder();
        if (border != null && border instanceof TitledBorder) {
            ((TitledBorder)border).setTitle(translator.translate(getKey(), language));
        }
        else {
            throw new IllegalStateException("Panel with borderKey '" + getKey() + "' has no titled border.");
        }
    }
}
