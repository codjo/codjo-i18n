package net.codjo.i18n.gui;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Enumeration;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.codjo.i18n.common.Language;
import net.codjo.i18n.common.TranslationManager;

public class InternationalizableJTable extends AbstractInternationalizableComponent<JTable> {

    private Reference<JTable> reference;
    private String[] keys;


    public InternationalizableJTable(String key, String[] keys, JTable table) {
        super(key);
        this.keys = keys;
        this.reference = new WeakReference<JTable>(table);
    }


    public void updateTranslation(Language language, TranslationManager translator) {
        JTable table = reference.get();
        if (table == null) {
            return;
        }

        setTranslationRenderers(table, language, translator);

        table.getTableHeader().repaint();
    }


    private void setTranslationRenderers(JTable table,
                                         Language language,
                                         TranslationManager translator) {
        Enumeration<TableColumn> columnEnumeration = table.getColumnModel().getColumns();
        int index = 0;
        while (columnEnumeration.hasMoreElements()) {
            TableColumn tableColumn = columnEnumeration.nextElement();
            TableCellRenderer headerRenderer = getHeaderRenderer(table, tableColumn);

            int column = table.convertColumnIndexToModel(index);

            if (column > keys.length - 1) {
                throw new IllegalArgumentException(
                      "The number of keys specified does not match the column count of the table.");
            }

            InternationalizationHeaderRenderer i18nRenderer;
            if (!InternationalizationHeaderRenderer.class.isInstance(headerRenderer)) {
                i18nRenderer = new InternationalizationHeaderRenderer(keys[column],
                                                                      headerRenderer,
                                                                      translator);
                tableColumn.setHeaderRenderer(i18nRenderer);
            }
            else {
                i18nRenderer = (InternationalizationHeaderRenderer)headerRenderer;
            }
            i18nRenderer.setLanguage(language);
            index++;
        }
    }


    TableCellRenderer getHeaderRenderer(JTable table, TableColumn tableColumn) {
        TableCellRenderer columnRenderer = tableColumn.getHeaderRenderer();
        if (columnRenderer != null) {
            return columnRenderer;
        }
        return table.getTableHeader().getDefaultRenderer();
    }


    public JTable getComponent() {
        return reference.get();
    }
}
