package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import javax.swing.AbstractButton;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class InternationalizableAbstractButton extends AbstractInternationalizableComponent<AbstractButton> {

    private String toolTipKey;
    private Reference<AbstractButton> reference;


    public InternationalizableAbstractButton(String textKey, String toolTipKey, AbstractButton button) {
        super(textKey);
        this.toolTipKey = toolTipKey;
        this.reference = new WeakReference<AbstractButton>(button);
    }


    public AbstractButton getComponent() {
        return reference.get();
    }


    public String getToolTipKey() {
        return toolTipKey;
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        AbstractButton button = reference.get();
        if (button == null) {
            return;
        }
        if (getKey() != null) {
            button.setText(translator.translate(getKey(), language));
        }
        if (toolTipKey != null) {
            button.setToolTipText(translator.translate(toolTipKey, language));
        }
    }
}