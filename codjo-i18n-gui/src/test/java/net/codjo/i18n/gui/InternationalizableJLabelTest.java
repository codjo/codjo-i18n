package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import java.util.Map;
import javax.swing.JLabel;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InternationalizableJLabelTest extends AbstractInternationalizableComponentTestCase {

    @Test
    public void testUpdateTranslation() throws Exception {
        translationNotifier.setLanguage(Language.FR);

        JLabel jLabel = new JLabel("mi etiqueta");
        InternationalizableJLabel label = new InternationalizableJLabel("MyLabelKey", jLabel);

        assertThat(label.getComponent().getText(), equalTo("mi etiqueta"));

        label.updateTranslation(Language.EN, translationManager);
        assertThat(label.getComponent().getText(), equalTo("my label"));

        label.updateTranslation(Language.FR, translationManager);
        assertThat(label.getComponent().getText(), equalTo("mon libellé"));
    }


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"MyLabelKey", "mon libellé"}
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"MyLabelKey", "my label"}
                                 });
    }
}
