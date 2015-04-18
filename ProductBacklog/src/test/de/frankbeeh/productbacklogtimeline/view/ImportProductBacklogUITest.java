package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import org.junit.Test;

public class ImportProductBacklogUITest extends BaseUITest {
    private static final TableViewContent PBL_1 = new TableViewContent(new String[][] { { "1", "3.0", "Done", "PBI 1", " Description 1", "3.0", "Sprint 1", "", "", "" },
            { "2", "1.0", "Canceled", "PBI 2", " Description 2", "3.0", "Sprint 1", "", "", "" }, { "3", "5.0", "Todo", "PBI 3", " Description 3", "8.0", "", "", "", "" },
            { "4", "8.0", "Todo", "PBI 4", " Description 4", "16.0", "", "", "", "" } });
    private static final TableViewContent PBL_2 = new TableViewContent(new String[][] { { "1", "3.0", "Done", "PBI 1", " Description 1", "3.0", "Sprint 1", "", "", "" },
            { "2", "1.0", "Canceled", "PBI 2", " Description 2", "3.0", "Sprint 1", "", "", "" }, { "5", "13.0", "Done", "PBI 5", " Description 5", "16.0", "Sprint 2", "", "", "" },
            { "3", "5.0", "Todo", "PBI 3", " Description 3", "21.0", "", "", "", "" }, { "4", "8.0", "Todo", "PBI 4", " Description 4", "29.0", "", "", "", "" },
            { "6", "2.0", "Todo", "PBI 6", " Description 6", "31.0", "", "", "", "" } });

    private static final String FILE_NAME_2 = "PBL2.csv";
    private static final String FILE_NAME_1 = "PBL1.csv";

    @Test
    public void importBacklog() throws Exception {
        importProductBacklog(FILE_NAME_1);
        assertEquals(FILE_NAME_1, getSelectedProductBacklog());
        assertContentOfTableView(getProductBacklogTable(), PBL_1);
    }

    @Test
    public void selectBacklog() throws Exception {
        importProductBacklog(FILE_NAME_1);
        importProductBacklog(FILE_NAME_2);
        assertEquals(FILE_NAME_2, getSelectedProductBacklog());

        selectProductBacklogTab();
        assertContentOfTableView(getProductBacklogTable(), PBL_2);

        selectProductBacklog(FILE_NAME_1);
        assertContentOfTableView(getProductBacklogTable(), PBL_1);
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        openPBLImportDialog();
        closeCurrentWindow();
    }

    private void selectProductBacklogTab() {
        selectTab("PBL");
    }

    private String getSelectedProductBacklog() {
        return getSelectProductBacklogComboBox().getSelectionModel().getSelectedItem();
    }

    private void selectProductBacklog(final String productBacklogName) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                getSelectProductBacklogComboBox().getSelectionModel().select(productBacklogName);
            }
        });
    }

    private ComboBox<String> getSelectProductBacklogComboBox() {
        return this.<ComboBox<String>> getUniqueNode("#selectedProductBacklog");
    }

    private void openPBLImportDialog() {
        clickOn("File");
        clickOn("Import PBL");
    }

    private void importProductBacklog(String fileName1) {
        openPBLImportDialog();
        enterFileName(fileName1);
    }

    private TableView<?> getProductBacklogTable() {
        return (TableView<?>) getUniqueNode("#productBacklogTable");
    }
}
