package net.codjo.i18n.gui.action;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.gui.TranslationNotifier;
/**
 *
 */
public class ChooseLanguageAction extends AbstractAction {
    private Language language;
    private TranslationNotifier translationNotifier;


    public ChooseLanguageAction(Language language, TranslationNotifier translationNotifier) {
        super(language.getLocale().getDisplayLanguage());
        this.language = language;
        this.translationNotifier = translationNotifier;
    }


    public void actionPerformed(ActionEvent e) {
        translationNotifier.setLanguage(language);
    }
}
