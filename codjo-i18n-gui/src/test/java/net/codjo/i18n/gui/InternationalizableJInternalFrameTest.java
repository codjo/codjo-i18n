package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import java.util.Map;
import javax.swing.JInternalFrame;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InternationalizableJInternalFrameTest extends AbstractInternationalizableComponentTestCase {
    @Test
    public void testUpdateTranslation() {
        translationNotifier.setLanguage(Language.FR);

        JInternalFrame jInternalFrame = new JInternalFrame("mi titulo");
        InternationalizableJInternalFrame frame = new InternationalizableJInternalFrame("MyTitleKey",
                                                                                        jInternalFrame);

        assertThat(frame.getComponent().getTitle(), equalTo("mi titulo"));

        frame.updateTranslation(Language.EN, translationManager);
        assertThat(frame.getComponent().getTitle(), equalTo("my title"));

        frame.updateTranslation(Language.FR, translationManager);
        assertThat(frame.getComponent().getTitle(), equalTo("mon titre"));
    }


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"MyTitleKey", "mon titre"}
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"MyTitleKey", "my title"}
                                 });
    }
}
