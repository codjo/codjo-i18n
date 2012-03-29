package net.codjo.i18n.common.plugin;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import net.codjo.plugin.common.AbstractApplicationPlugin;
/**
 *
 */
public class InternationalizationPlugin extends AbstractApplicationPlugin {
    private InternationalizationPluginConfiguration configuration;


    public InternationalizationPlugin() {
        configuration = new InternationalizationPluginConfiguration(new TranslationManager());
        InternationalizationUtil.setTranslationManager(configuration.getTranslationManager());
        InternationalizationUtil.setLanguage(Language.FR);
    }


    public InternationalizationPluginConfiguration getConfiguration() {
        return configuration;
    }


    public class InternationalizationPluginConfiguration {
        private TranslationManager translationManager;


        public InternationalizationPluginConfiguration(TranslationManager translationManager) {
            this.translationManager = translationManager;
        }


        public TranslationManager getTranslationManager() {
            return translationManager;
        }


        public void setLanguage(Language language) {
            InternationalizationUtil.setLanguage(language);
        }
    }
}
