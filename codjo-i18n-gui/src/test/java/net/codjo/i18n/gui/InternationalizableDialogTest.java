package net.codjo.i18n.gui;
import java.awt.Frame;
import java.util.Map;
import javax.swing.JDialog;
import net.codjo.i18n.common.Language;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
/**
 *
 */
public class InternationalizableDialogTest extends AbstractInternationalizableComponentTestCase {
    @Test
    public void testUpdateTranslation() {
        translationNotifier.setLanguage(Language.FR);

        JDialog dialog = new JDialog((Frame)null, "mi titulo", false);
        InternationalizableDialog i18nDialog = new InternationalizableDialog("MyTitleKey", dialog);

        assertThat(i18nDialog.getComponent().getTitle(), equalTo("mi titulo"));

        i18nDialog.updateTranslation(Language.EN, translationManager);
        assertThat(i18nDialog.getComponent().getTitle(), equalTo("my title"));

        i18nDialog.updateTranslation(Language.FR, translationManager);
        assertThat(i18nDialog.getComponent().getTitle(), equalTo("mon titre"));
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