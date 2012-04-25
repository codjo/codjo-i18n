package net.codjo.i18n.gui;
import java.io.File;
import java.util.Enumeration;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import net.codjo.i18n.common.Language;
import net.codjo.test.common.fixture.DirectoryFixture;
import net.codjo.util.file.FileUtil;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.SimpleLayout;
import org.junit.Test;
import org.junit.internal.matchers.StringContains;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
/**
 *
 */
public class InternationalizableJTableTest extends AbstractInternationalizableComponentTestCase {
    private JTable table;
    private InternationalizableJTable internationalizableTable;


    @Override
    protected void fillResourcesBundleContent(Map<Language, Object[][]> translationBundleMap) {
        translationBundleMap.put(Language.FR,
                                 new Object[][]{
                                       {"table.key1", "colonne 1"},
                                       {"table.key2", "colonne 2"},
                                       {"table.key3", "colonne 3"},
                                 });
        translationBundleMap.put(Language.EN,
                                 new Object[][]{
                                       {"table.key1", "column 1"},
                                       {"table.key2", "column 2"},
                                       {"table.key3", "column 3"},
                                 });
    }


    @Override
    public void setUp() {
        super.setUp();
        table = new JTable();
        table.setModel(new DefaultTableModel(new String[][]{
              {"1", "2", "3"},
              {"a", "b", "c"},
        }, new String[]{"Header 1", "Header 2", "Header 3"}));
        internationalizableTable =
              new InternationalizableJTable(null, new String[]{"table.key1", "table.key2", "table.key3"}, table);
    }


    @Test
    public void test_nominal() throws Exception {
        assertHeader(new String[]{"Header 1", "Header 2", "Header 3"}, table);

        translationNotifier.addInternationalizableComponent(internationalizableTable);
        assertHeader(new String[]{"colonne 1", "colonne 2", "colonne 3"}, table);

        translationNotifier.setLanguage(Language.EN);
        assertHeader(new String[]{"column 1", "column 2", "column 3"}, table);

        translationNotifier.setLanguage(Language.FR);
        assertHeader(new String[]{"colonne 1", "colonne 2", "colonne 3"}, table);
    }


    @Test
    public void test_moveColumn() {
        assertHeader(new String[]{"Header 1", "Header 2", "Header 3"}, table);
        table.moveColumn(1, 2);
        assertHeader(new String[]{"Header 1", "Header 3", "Header 2"}, table);

        translationNotifier.addInternationalizableComponent(internationalizableTable);
        assertHeader(new String[]{"colonne 1", "colonne 3", "colonne 2"}, table);

        translationNotifier.setLanguage(Language.EN);
        assertHeader(new String[]{"column 1", "column 3", "column 2"}, table);

        table.moveColumn(2, 0);
        assertHeader(new String[]{"column 2", "column 1", "column 3"}, table);
    }


    @Test
    public void test_badKeyCount() throws Exception {
        DirectoryFixture directoryFixture = DirectoryFixture.newTemporaryDirectoryFixture();
        directoryFixture.doSetUp();

        File logFile = new File(directoryFixture, "log.txt");
        FileAppender appender = new FileAppender(new SimpleLayout(), logFile.getPath());

        TranslationNotifier.LOG.addAppender(appender);
        TranslationNotifier.LOG.setLevel(Level.DEBUG);

        InternationalizableJTable i18nTable = new InternationalizableJTable("key", new String[]{"1", "2"}, table);
        translationNotifier.addInternationalizableComponent(i18nTable);

        assertThat(FileUtil.loadContent(logFile), StringContains.containsString(
              "The number of keys specified does not match the column count of the table."));

        TranslationNotifier.LOG.removeAppender(appender);
        appender.close();
        directoryFixture.doTearDown();
    }


    private void assertHeader(String[] expectedHeader, JTable jTable) {
        assertThat(jTable.getColumnCount(), equalTo(expectedHeader.length));
        Enumeration<TableColumn> columns = jTable.getTableHeader().getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = columns.nextElement();
            int column = jTable.convertColumnIndexToView(tableColumn.getModelIndex());
            TableCellRenderer headerRenderer = internationalizableTable.getHeaderRenderer(jTable, tableColumn);
            JLabel headerLabel = (JLabel)headerRenderer.getTableCellRendererComponent(jTable,
                                                                                      tableColumn.getHeaderValue(),
                                                                                      false,
                                                                                      false,
                                                                                      0,
                                                                                      column);
            assertThat(headerLabel.getText(), equalTo(expectedHeader[column]));
        }
    }
}
