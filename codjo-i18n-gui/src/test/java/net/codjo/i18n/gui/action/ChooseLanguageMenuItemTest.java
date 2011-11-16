package net.codjo.i18n.gui.action;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.gui.TranslationNotifier;
import junit.framework.TestCase;
/**
 *
 */
public class ChooseLanguageMenuItemTest extends TestCase {
    public void test_menuIsSelectedByDefault() throws Exception {
        TranslationNotifier translationNotifier = new TranslationNotifier(Language.FR, null);
        ChooseLanguageMenuItem languageMenu = new ChooseLanguageMenuItem(Language.FR, translationNotifier);
        assertTrue(languageMenu.isSelected());
    }


    public void test_nominal() throws Exception {
        TranslationNotifier translationNotifier = new TranslationNotifier(Language.FR, null);
        ChooseLanguageMenuItem languageMenu = new ChooseLanguageMenuItem(Language.EN, translationNotifier);
        assertFalse(languageMenu.isSelected());

        translationNotifier.setLanguage(Language.EN);
        assertTrue(languageMenu.isSelected());

        translationNotifier.setLanguage(Language.EN);
        assertTrue(languageMenu.isSelected());

        translationNotifier.setLanguage(Language.FR);
        assertFalse(languageMenu.isSelected());
    }
}
