package net.codjo.i18n.common;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
/**
 *
 */
public class TranslationManagerTest {

    @Test
    public void test_nominal() throws Exception {
        TranslationManager translator = new TranslationManager();
        translator.addBundle(new MyFrenchResources(), Language.FR);
        translator.addBundle(new MyEnglishResources(), Language.EN);
        assertEquals("OK", translator.translate("OkKey", Language.EN));
        assertEquals("D'accord", translator.translate("OkKey", Language.FR));
        assertEquals("Cancel", translator.translate("CancelKey", Language.EN));
        assertEquals("Annuler", translator.translate("CancelKey", Language.FR));
    }


    @Test
    public void test_handleMultipleResourcesForADedicatedLanguage() throws Exception {
        TranslationManager translator = new TranslationManager();
        translator.addBundle(new MyFrenchResources(), Language.FR);
        translator.addBundle(new MyFrenchResources2(), Language.FR);
        assertEquals("D'accord", translator.translate("OkKey", Language.FR));
        assertEquals("Oui", translator.translate("YesKey", Language.FR));
    }


    @Test
    public void test_handleResourceBundleFile() throws Exception {
        TranslationManager translator = new TranslationManager();
        translator.addBundle("net.codjo.i18n.common.Example", Language.FR);
        translator.addBundle("net.codjo.i18n.common.Example", Language.EN);

        assertEquals("Etiquette", translator.translate("TagKey", Language.FR));
        assertEquals("Champ", translator.translate("FieldKey", Language.FR));

        assertEquals("Tag", translator.translate("TagKey", Language.EN));
        assertEquals("Field", translator.translate("FieldKey", Language.EN));
    }


    @Test
    public void test_missingBundleForLanguage() throws Exception {
        TranslationManager translator = new TranslationManager();
        try {
            translator.translate("OkKey", Language.EN);
            fail();
        }
        catch (IllegalArgumentException e) {
            assertEquals("Can't find bundle for 'EN' language.", e.getMessage());
        }
    }


    @Test
    public void test_missingResourceForBundle() throws Exception {
        TranslationManager translator = new TranslationManager();
        translator.addBundle(new MyFrenchResources(), Language.FR);
        try {
            translator.translate("UnknownKey", Language.FR);
            fail();
        }
        catch (MissingResourceException e) {
            assertEquals(
                  "Can't find resource for bundle " + MyFrenchResources.class.getName() + ", key UnknownKey",
                  e.getMessage());
        }
    }


    @Test
    public void test_addFileResourceBundle() throws Exception {
        TranslationManager translator = new TranslationManager();
        translator.addBundle("net.codjo.i18n.common.Example", Language.FR);
        translator.addBundle("net.codjo.i18n.common.Example", Language.EN);

        assertEquals("Tag", translator.translate("TagKey", Language.EN));
        assertEquals("Etiquette", translator.translate("TagKey", Language.FR));
        assertEquals("Field", translator.translate("FieldKey", Language.EN));
        assertEquals("Champ", translator.translate("FieldKey", Language.FR));
    }


    private static class MyFrenchResources extends ListResourceBundle {
        private static final Object[][] CONTENTS = new Object[][]{
              {"OkKey", "D'accord"},
              {"CancelKey", "Annuler"},
        };


        @Override
        public Object[][] getContents() {
            return CONTENTS;
        }
    }

    private static class MyFrenchResources2 extends ListResourceBundle {
        private static final Object[][] CONTENTS = new Object[][]{
              {"YesKey", "Oui"},
              {"NoKey", "Non"},
        };


        @Override
        public Object[][] getContents() {
            return CONTENTS;
        }
    }

    private static class MyEnglishResources extends ListResourceBundle {
        private static final Object[][] CONTENTS = new Object[][]{
              {"OkKey", "OK"},
              {"CancelKey", "Cancel"},
        };


        @Override
        public Object[][] getContents() {
            return CONTENTS;
        }
    }
}
