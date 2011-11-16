package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InternationalizableJTabbedPaneTest extends AbstractInternationalizableComponentTestCase {
    @Test
    public void testUpdateTranslation() {
        translationNotifier.setLanguage(Language.FR);

        JTabbedPane jTabbedPane = new JTabbedPane();
        jTabbedPane.add("Ceja 1", new JPanel());
        jTabbedPane.add("Ceja 2", new JPanel());
        jTabbedPane.add("Ceja 3", new JPanel());

        String[] tabKeys = {"key1", "key2", "key3"};
        InternationalizableJTabbedPane pane = new InternationalizableJTabbedPane("myTabbedPane",
                                                                                 tabKeys,
                                                                                 jTabbedPane);

        assertThat(jTabbedPane.getTitleAt(0), equalTo("Ceja 1"));
        assertThat(jTabbedPane.getTitleAt(1), equalTo("Ceja 2"));
        assertThat(jTabbedPane.getTitleAt(2), equalTo("Ceja 3"));

        pane.updateTranslation(Language.EN, translationManager);
        assertThat(jTabbedPane.getTitleAt(0), equalTo("Tab 1"));
        assertThat(jTabbedPane.getTitleAt(1), equalTo("Tab 2"));
        assertThat(jTabbedPane.getTitleAt(2), equalTo("Tab 3"));

        pane.updateTranslation(Language.FR, translationManager);
        assertThat(jTabbedPane.getTitleAt(0), equalTo("Onglet 1"));
        assertThat(jTabbedPane.getTitleAt(1), equalTo("Onglet 2"));
        assertThat(jTabbedPane.getTitleAt(2), equalTo("Onglet 3"));
    }


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"key1", "Onglet 1"},
                                       {"key2", "Onglet 2"},
                                       {"key3", "Onglet 3"}
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"key1", "Tab 1"},
                                       {"key2", "Tab 2"},
                                       {"key3", "Tab 3"}
                                 });
    }
}