package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import java.util.HashMap;
import java.util.ListResourceBundle;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Before;

public abstract class AbstractInternationalizableComponentTestCase {

    private Map<Language, Object[][]> translationBundleMap = new HashMap<Language, Object[][]>();

    protected TranslationManager translationManager;

    protected TranslationNotifier translationNotifier;


    protected abstract void fillResourcesBundleContent(Map<Language, Object[][]> bundle);


    @Before
    public void setUp() {
        translationManager = new TranslationManager();

        fillResourcesBundleContent(translationBundleMap);
        for (Entry<Language, Object[][]> languageContent : translationBundleMap.entrySet()) {
            translationManager.addBundle(new TranslationResources(languageContent.getValue()),
                                         languageContent.getKey());
        }

        translationNotifier = new TranslationNotifier(Language.FR, translationManager);
    }


    private static class TranslationResources extends ListResourceBundle {
        private Object[][] content;


        private TranslationResources(Object[][] content) {
            this.content = content;
        }


        @Override
        public Object[][] getContents() {
            return content;
        }
    }
}
