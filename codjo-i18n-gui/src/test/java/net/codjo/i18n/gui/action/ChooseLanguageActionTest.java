package net.codjo.i18n.gui.action;
import junit.framework.TestCase;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.gui.TranslationNotifier;
import net.codjo.test.common.LogString;
/**
 *
 */
public class ChooseLanguageActionTest extends TestCase {
    public void test_nominal() throws Exception {
        final LogString log = new LogString();
        TranslationNotifier translationNotifier = new TranslationNotifier(Language.FR, null) {
            @Override
            public void setLanguage(Language language) {
                log.call("setLanguage", language);
            }
        };
        ChooseLanguageAction languageAction = new ChooseLanguageAction(Language.EN, translationNotifier);
        languageAction.actionPerformed(null);
        
        log.assertAndClear("setLanguage(EN)");
    }
}
