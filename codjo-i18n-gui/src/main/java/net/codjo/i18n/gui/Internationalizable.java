package net.codjo.i18n.gui;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;

public interface Internationalizable {
    void updateTranslation(Language language, TranslationManager translator);    
}
