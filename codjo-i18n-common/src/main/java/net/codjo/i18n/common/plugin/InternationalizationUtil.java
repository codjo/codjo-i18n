package net.codjo.i18n.common.plugin;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
/**
 *
 */
public class InternationalizationUtil {
    private static TranslationManager translationManager = null;
    private static Language language = null;


    private InternationalizationUtil() {
    }


    public static String translate(String key) {
        if ((translationManager == null) || (language == null)) {
            throw new IllegalArgumentException(
                  "Cannot launch internationalization. "
                  + InternationalizationPlugin.class.getName()
                  + " must be added to your application.");
        }
        return translationManager.translate(key, language);
    }


    static void setTranslationManager(TranslationManager translationManager) {
        InternationalizationUtil.translationManager = translationManager;
    }


    static void setLanguage(Language language) {
        InternationalizationUtil.language = language;
    }
}
