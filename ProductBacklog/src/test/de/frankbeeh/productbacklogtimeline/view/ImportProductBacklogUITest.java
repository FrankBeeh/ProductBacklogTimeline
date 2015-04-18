package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import org.junit.Test;

public class ImportProductBacklogUITest extends BaseUITest {

    private static final String FILE_NAME_2 = "PBL2.csv";
    private static final String FILE_NAME_1 = "PBL1.csv";

    @Test
    public void importBacklog() throws Exception {
        openPBLImportDialog();
        enterFileName(FILE_NAME_1);
        selectProductBacklogTab();
    }

    @Test
    public void selectBacklog() throws Exception {
        openPBLImportDialog();
        enterFileName(FILE_NAME_1);
        assertEquals(FILE_NAME_1, getSelectedProductBacklog());
        openPBLImportDialog();
        enterFileName(FILE_NAME_2);
        assertEquals(FILE_NAME_2, getSelectedProductBacklog());

        // FIXME: Get rid of Workaround
        Platform.runLater(new Runnable() {

            @Override
            public void run() {
                selectProductBacklog(FILE_NAME_1);
            }
        });

        selectProductBacklogTab();
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        openPBLImportDialog();
        closeCurrentWindow();
        selectProductBacklogTab();
    }

    private void selectProductBacklogTab() {
        selectTab("PBL");
    }

    private String getSelectedProductBacklog() {
        return getSelectProductBacklogComboBox().getSelectionModel().getSelectedItem();
    }

    private void selectProductBacklog(String productBacklogName) {
        getSelectProductBacklogComboBox().getSelectionModel().select(productBacklogName);
    }

    private ComboBox<String> getSelectProductBacklogComboBox() {
        return this.<ComboBox<String>>getUniqueNode("#selectedProductBacklog");
    }

    private void openPBLImportDialog() {
        clickOn("File");
        clickOn("Import PBL");
    }
}
