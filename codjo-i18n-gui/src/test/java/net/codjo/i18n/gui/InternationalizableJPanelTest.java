package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InternationalizableJPanelTest extends AbstractInternationalizableComponentTestCase {
    @Test
    public void testUpdateTranslation() {
        translationNotifier.setLanguage(Language.FR);
        
        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createTitledBorder("Borde"));
        InternationalizableJPanel panel = new InternationalizableJPanel("MyBorderKey", jPanel);

        TitledBorder border = ((TitledBorder)panel.getComponent().getBorder());
        assertThat(border.getTitle(), equalTo("Borde"));

        panel.updateTranslation(Language.EN, translationManager);
        assertThat(border.getTitle(), equalTo("Border"));

        panel.updateTranslation(Language.FR, translationManager);
        assertThat(border.getTitle(), equalTo("Bordure"));
    }


    @Test
    public void testUpdateTranslationWithNonTitledBorderShouldFail() throws Exception {
        JPanel jPanel = new JPanel();
        try {
            new InternationalizableJPanel("MyBorderKey", jPanel);
        }
        catch (IllegalStateException ex) {
            assertThat(ex.getMessage(), equalTo("Panel with borderKey 'MyBorderKey' has no titled border."));
        }

        jPanel.setBorder(BorderFactory.createEtchedBorder());

        try {
            new InternationalizableJPanel("MyBorderKey", jPanel);
        }
        catch (IllegalStateException ex) {
            assertThat(ex.getMessage(), equalTo("Panel with borderKey 'MyBorderKey' has no titled border."));
        }
    }


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"MyBorderKey", "Bordure"}
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"MyBorderKey", "Border"}
                                 });
    }
}
