package net.codjo.i18n.gui.plugin;
import net.codjo.agent.AgentContainer;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import net.codjo.i18n.gui.TranslationNotifier;
import net.codjo.i18n.gui.action.ChooseLanguageMenuItem;
import net.codjo.plugin.common.AbstractApplicationPlugin;
import net.codjo.plugin.common.ApplicationCore;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class InternationalizationGuiPlugin extends AbstractApplicationPlugin {
    private InternationalizationGuiPluginConfiguration configuration;
    private final ApplicationCore applicationCore;


    public static JMenu getLanguagesMenu(TranslationNotifier translationNotifier) {
        JMenu menu = new JMenu("Langues");
        translationNotifier.addInternationalizableComponent(menu, "net.codjo.i18n.gui.menu.Languages", null);
        Language[] languages = Language.values();
        for (Language language : languages) {
            JMenuItem menuItem = new ChooseLanguageMenuItem(language, translationNotifier);
            String menuItemKey = "net.codjo.i18n.gui.menu." + language;
            translationNotifier.addInternationalizableComponent(menuItem,
                                                                menuItemKey,
                                                                menuItemKey + ".tooltip");
            menu.add(menuItem);
        }
        return menu;
    }


    @Override
    public void start(AgentContainer agentContainer) throws Exception {
        applicationCore.addGlobalComponent(configuration.getTranslationManager());
        applicationCore.addGlobalComponent(configuration.getTranslationNotifier());

        registerLanguageBundles(configuration.getTranslationManager());
    }


    @Override
    public void stop() throws Exception {
        applicationCore.removeGlobalComponent(configuration.getTranslationManager().getClass());
        applicationCore.removeGlobalComponent(configuration.getTranslationNotifier().getClass());
    }


    public InternationalizationGuiPlugin(ApplicationCore applicationCore) {
        this.applicationCore = applicationCore;
        TranslationManager translationManager = new TranslationManager();
        configuration =
              new InternationalizationGuiPluginConfiguration(translationManager,
                                                             new TranslationNotifier(Language.FR,
                                                                                     translationManager));
    }


    public InternationalizationGuiPluginConfiguration getConfiguration() {
        return configuration;
    }


    private void registerLanguageBundles(TranslationManager translationManager) {
        translationManager.addBundle("net.codjo.i18n.gui.i18n", Language.FR);
        translationManager.addBundle("net.codjo.i18n.gui.i18n", Language.EN);
    }


    public class InternationalizationGuiPluginConfiguration {
        private TranslationManager translationManager;
        private TranslationNotifier translationNotifier;


        public InternationalizationGuiPluginConfiguration(TranslationManager translationManager,
                                                          TranslationNotifier translationNotifier) {
            this.translationManager = translationManager;
            this.translationNotifier = translationNotifier;
        }


        public TranslationManager getTranslationManager() {
            return translationManager;
        }


        public TranslationNotifier getTranslationNotifier() {
            return translationNotifier;
        }
    }
}
