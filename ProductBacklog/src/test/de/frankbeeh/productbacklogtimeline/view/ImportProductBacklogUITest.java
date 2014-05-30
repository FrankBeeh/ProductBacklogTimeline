package de.frankbeeh.productbacklogtimeline.view;

import static junit.framework.Assert.assertEquals;
import javafx.application.Platform;
import javafx.scene.control.ComboBox;

import org.junit.Test;

public class ImportProductBacklogUITest extends AbstractBaseUITest {

    private static final String FILE_NAME_2 = "PBL2.csv";
    private static final String FILE_NAME_1 = "PBL1.csv";

    @Test
    public void importBacklog() throws Exception {
        openPBLImportDialog();
        enterFileName(FILE_NAME_1);
        selectTab("PBL");
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

        selectTab("PBL");
    }

    private String getSelectedProductBacklog() {
        return getSelectProductBacklogComboBox().getSelectionModel().getSelectedItem();
    }

    private void selectProductBacklog(String productBacklogName) {
        getSelectProductBacklogComboBox().getSelectionModel().select(productBacklogName);
    }

    private ComboBox<String> getSelectProductBacklogComboBox() {
        final ComboBox<String> comboBox = getUniqueNode("#selectedProductBacklog");
        return comboBox;
    }

    @Test
    public void cancelImportBacklog() throws Exception {
        openPBLImportDialog();
        closeDialog();
        selectTab("PBL");
    }

    private void openPBLImportDialog() {
        click("File");
        click("Import PBL");
    }

}
