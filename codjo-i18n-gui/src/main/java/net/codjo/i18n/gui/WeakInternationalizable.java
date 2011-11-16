package net.codjo.i18n.gui;
import java.lang.ref.WeakReference;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;

public class WeakInternationalizable extends WeakReference<Internationalizable> implements Internationalizable {
    public WeakInternationalizable(Internationalizable referent) {
        super(referent);
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        Internationalizable referent = get();
        if (null != referent) {
            referent.updateTranslation(language, translator);
        }
    }


    public boolean isReferenced() {
        return null != get();
    }
}
