package net.codjo.i18n.gui.action;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import net.codjo.i18n.gui.Internationalizable;
import net.codjo.i18n.gui.TranslationNotifier;
import javax.swing.JCheckBoxMenuItem;
/**
 *
 */
public class ChooseLanguageMenuItem extends JCheckBoxMenuItem implements Internationalizable {
    private Language language;


    public ChooseLanguageMenuItem(Language language, TranslationNotifier translationNotifier) {
        this.language = language;
        translationNotifier.addInternationalizable(this);
        setAction(new ChooseLanguageAction(language, translationNotifier));
        setSelected(translationNotifier.getLanguage().equals(language));
    }


    public void updateTranslation(Language currentLanguage, TranslationManager translator) {
        setSelected(currentLanguage.equals(language));
    }
}
