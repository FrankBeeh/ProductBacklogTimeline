package de.frankbeeh.productbacklogtimeline.view.accessor;

import org.testfx.api.FxRobot;

import de.frankbeeh.productbacklogtimeline.view.BaseAccessor;

public class MenuAccessor extends BaseAccessor {
    public MenuAccessor(FxRobot fxRobot) {
        super(fxRobot);
    }

    public ImportProductTimestampDialogAccessor openProductTimelineImportDialog() {
        clickOn(getUniqueNode("File"));
        clickOn(getUniqueNode("Import Product Timestamp"));
        return new ImportProductTimestampDialogAccessor(getFxRobot());
    }
}
