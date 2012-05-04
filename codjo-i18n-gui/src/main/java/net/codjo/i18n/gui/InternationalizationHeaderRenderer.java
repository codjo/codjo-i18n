package net.codjo.i18n.gui;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;
/**
 *
 */
public class InternationalizationHeaderRenderer extends DefaultTableCellRenderer {
    private String key;
    private TableCellRenderer headerRenderer;
    private TranslationManager translationManager;
    private Language language;


    public InternationalizationHeaderRenderer(String key,
                                              TableCellRenderer headerRenderer,
                                              TranslationManager manager) {
        this.key = key;
        this.headerRenderer = headerRenderer;
        this.translationManager = manager;
    }


    public void setLanguage(Language language) {
        this.language = language;
    }


    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row,
                                                   int column) {
        return headerRenderer.getTableCellRendererComponent(table,
                                                            translateLabel(),
                                                            isSelected,
                                                            hasFocus,
                                                            row,
                                                            column);
    }


    private String translateLabel() {
        return translationManager.translate(key, language);
    }
}
