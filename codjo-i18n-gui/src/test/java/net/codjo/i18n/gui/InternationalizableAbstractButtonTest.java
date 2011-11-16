package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class InternationalizableAbstractButtonTest extends AbstractInternationalizableComponentTestCase {
    @Test
    public void testUpdateTranslationOnJButton() {
        translationNotifier.setLanguage(Language.FR);

        JButton jButton = new JButton("Agregar");
        jButton.setToolTipText("Agregar una marca");
        InternationalizableAbstractButton button = new InternationalizableAbstractButton("ButtonTextKey",
                                                                                         "ButtonDescriptionKey",
                                                                                         jButton);

        assertThat(button.getComponent().getText(), equalTo("Agregar"));
        assertThat(button.getComponent().getToolTipText(), equalTo("Agregar una marca"));

        button.updateTranslation(Language.EN, translationManager);
        assertThat(button.getComponent().getText(), equalTo("Add"));
        assertThat(button.getComponent().getToolTipText(), equalTo("Add a record"));

        button.updateTranslation(Language.FR, translationManager);
        assertThat(button.getComponent().getText(), equalTo("Ajouter"));
        assertThat(button.getComponent().getToolTipText(), equalTo("Ajouter un enregistrement"));
    }


    @Test
    public void testUpdateTranslationOnJMenuItem() {
        translationNotifier.setLanguage(Language.FR);

        JMenuItem menuItem = new JMenuItem("Carta");
        menuItem.setToolTipText("Tortillas");
        InternationalizableAbstractButton internationalizableMenuItem
              = new InternationalizableAbstractButton("MenuItemTextKey",
                                                      "MenuItemDescriptionKey",
                                                      menuItem);

        assertThat(internationalizableMenuItem.getComponent().getText(), equalTo("Carta"));
        assertThat(internationalizableMenuItem.getComponent().getToolTipText(), equalTo("Tortillas"));

        internationalizableMenuItem.updateTranslation(Language.EN, translationManager);
        assertThat(internationalizableMenuItem.getComponent().getText(), equalTo("Menu"));
        assertThat(internationalizableMenuItem.getComponent().getToolTipText(), equalTo("Porridge"));

        internationalizableMenuItem.updateTranslation(Language.FR, translationManager);

        assertThat(internationalizableMenuItem.getComponent().getText(), equalTo("Menu"));
        assertThat(internationalizableMenuItem.getComponent().getToolTipText(), equalTo("Blanquette"));
    }


    @Test
    public void testUpdateTranslationOnJCheckBox() {
        translationNotifier.setLanguage(Language.FR);
        JCheckBox checkbox = new JCheckBox("Visto");
        InternationalizableAbstractButton internationalizableJCheckBox
              = new InternationalizableAbstractButton("CheckBoxTextKey", null, checkbox);
        assertThat(internationalizableJCheckBox.getComponent().getText(), equalTo("Visto"));
        assertThat(internationalizableJCheckBox.getComponent().getToolTipText(), equalTo(null));

        internationalizableJCheckBox.updateTranslation(Language.EN, translationManager);

        assertThat(internationalizableJCheckBox.getComponent().getText(), equalTo("Check"));
        assertThat(internationalizableJCheckBox.getComponent().getToolTipText(), equalTo(null));

        internationalizableJCheckBox.updateTranslation(Language.FR, translationManager);
        assertThat(internationalizableJCheckBox.getComponent().getText(), equalTo("Coche"));
        assertThat(internationalizableJCheckBox.getComponent().getToolTipText(), equalTo(null));
    }


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"ButtonTextKey", "Ajouter"},
                                       {"ButtonDescriptionKey", "Ajouter un enregistrement"},
                                       {"MenuItemTextKey", "Menu"},
                                       {"MenuItemDescriptionKey", "Blanquette"},
                                       {"CheckBoxTextKey", "Coche"},
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"ButtonTextKey", "Add"},
                                       {"ButtonDescriptionKey", "Add a record"},
                                       {"MenuItemTextKey", "Menu"},
                                       {"MenuItemDescriptionKey", "Porridge"},
                                       {"CheckBoxTextKey", "Check"},
                                 });
    }
}