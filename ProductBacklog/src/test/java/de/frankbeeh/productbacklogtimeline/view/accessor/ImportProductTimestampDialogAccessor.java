package de.frankbeeh.productbacklogtimeline.view.accessor;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import org.testfx.api.FxRobot;

import de.frankbeeh.productbacklogtimeline.service.DateConverter;
import de.frankbeeh.productbacklogtimeline.view.BaseAccessor;

public class ImportProductTimestampDialogAccessor extends BaseAccessor {
    public ImportProductTimestampDialogAccessor(FxRobot fxRobot) {
        super(fxRobot);
    }

    public void enter(String timestampName, LocalDate timestampDate, String productBacklogFileName, String velocityForecastFileName, String releasesFileName) {
        enterTimestampName(timestampName);
        enterTimestampDate(timestampDate);
        enterProductBacklogFileName(productBacklogFileName);
        enterVelocityForecastFileName(velocityForecastFileName);
        enterReleasesFileName(releasesFileName);
        clickOk();
    }

    private void enterTimestampDate(LocalDate timestampDate) {
        enterText("#datePicker", DateConverter.formatLocalDate(timestampDate));
    }

    private void enterTimestampName(String timestampName) {
        enterText("#name", timestampName);
    }

    private void enterVelocityForecastFileName(String velocityForecastFileName) {
        clickOn(getUniqueNode("#chooseVelocityForecast"));
        enterFileName(velocityForecastFileName);
        assertEquals(getExpectedAbsolutePath(velocityForecastFileName), this.<TextField> getUniqueNode("#velocityForecastFile").getText());
    }

    private void enterProductBacklogFileName(String productBacklogFileName) {
        clickOn(getUniqueNode("#chooseProductBacklog"));
        enterFileName(productBacklogFileName);
        assertEquals(getExpectedAbsolutePath(productBacklogFileName), this.<TextField> getUniqueNode("#productBacklogFile").getText());
    }

    private void enterReleasesFileName(String releasesFileName) {
        clickOn(getUniqueNode("#chooseReleases"));
        enterFileName(releasesFileName);
        assertEquals(getExpectedAbsolutePath(releasesFileName), this.<TextField> getUniqueNode("#releasesFile").getText());
    }

    private void clickOk() {
        clickOn(getUniqueNode("#okDialog"));
    }

    private void enterFileName(String fileName) {
        typeString(fileName);
        typeKeyCode(KeyCode.ENTER);
    }

    private String getExpectedAbsolutePath(String velocityForecastFileName) {
        return System.getProperty("user.dir") + "\\" + velocityForecastFileName;
    }
}
