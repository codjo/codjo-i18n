package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
import net.codjo.test.common.LogString;
import java.awt.Component;
import java.util.List;
import java.util.ListResourceBundle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TranslationNotifierTest {

    private LogString log = new LogString();
    private TranslationNotifier notifier;


    @Test
    public void test_noBundle() {
        notifier = new TranslationNotifier(Language.FR, new TranslationManager());
        MyInternationalizableComponentMock internationalizable = new MyInternationalizableComponentMock(
              "JPanel", "MyPanel.title", new JPanel());

        notifier.addInternationalizableComponent(internationalizable);

        List<InternationalizableComponent> list = notifier.getInternationalizableComponents();
        assertThat(list.size(), equalTo(0));

        notifier.setLanguage(Language.EN);
        log.assertContent("");
    }


    @Test
    public void test_keyNotFound() throws Exception {
        MyInternationalizableComponentMock internationalizable = new MyInternationalizableComponentMock(
              "JPanel", "MyPanel.WRONG_KEY", new JPanel());

        notifier.addInternationalizableComponent(internationalizable);

        List<InternationalizableComponent> list = notifier.getInternationalizableComponents();
        assertThat(list.size(), equalTo(0));

        notifier.setLanguage(Language.EN);
        log.assertContent("");
    }


    @Test
    public void test_notifyContainerComponents() throws Exception {
        notifier.addInternationalizableContainer(new MyInternationalizableContainerMock());
        notifier.addInternationalizableComponent(new MyInternationalizableComponentMock("JPanel",
                                                                                        "MyPanel.title",
                                                                                        new JPanel()));
        log.assertAndClear(
              "JLabel.updateTranslation(FR), JButton.updateTranslation(FR), JPanel.updateTranslation(FR)");

        notifier.setLanguage(Language.FR);
        log.assertContent(
              "JLabel.updateTranslation(FR), JButton.updateTranslation(FR), JPanel.updateTranslation(FR)");
    }


    @Test
    public void test_notifyInternationalizableObject() throws Exception {
        Internationalizable internationalizableObj = new Internationalizable() {
            public void updateTranslation(Language language, TranslationManager translator) {
                log.call("Object.updateTranslation", language);
            }
        };
        notifier.addInternationalizable(internationalizableObj);

        notifier.setLanguage(Language.FR);
        log.assertAndClear("Object.updateTranslation(FR)");

        notifier.removeInternationalizable(internationalizableObj);

        notifier.setLanguage(Language.EN);
        log.assertContent("");
    }


    @Before
    public void setUp() throws Exception {
        TranslationManager manager = new TranslationManager();
        manager.addBundle(new MyFrenchResourceBundle(), Language.FR);
        notifier = new TranslationNotifier(Language.FR, manager);
    }


    private class MyInternationalizableContainerMock implements InternationalizableContainer {
        public void addInternationalizableComponents(TranslationNotifier translationNotifier) {
            translationNotifier.addInternationalizableComponent(new MyInternationalizableComponentMock(
                  "JLabel",
                  "JLabel.text",
                  new JLabel()));
            translationNotifier.addInternationalizableComponent(new MyInternationalizableComponentMock(
                  "JButton",
                  "JButton.text",
                  new JButton()));
        }
    }

    private class MyInternationalizableComponentMock implements InternationalizableComponent {
        private Component component;
        private String id;
        private String key;


        private MyInternationalizableComponentMock(String id, String key, Component component) {
            this.id = id;
            this.key = key;
            this.component = component;
        }


        public Component getComponent() {
            return component;
        }


        public String getKey() {
            return key;
        }


        public void updateTranslation(Language language, TranslationManager translator) {
            translator.translate(getKey(), language);
            log.call(id + ".updateTranslation", language);
        }
    }

    private static class MyFrenchResourceBundle extends ListResourceBundle {
        private static final Object[][] OBJECT = {
              {"JLabel.text", "libellé"},
              {"JButton.text", "bouton"},
              {"MyPanel.title", "panneau"},
        };


        @Override
        protected Object[][] getContents() {
            return OBJECT;
        }
    }
}
