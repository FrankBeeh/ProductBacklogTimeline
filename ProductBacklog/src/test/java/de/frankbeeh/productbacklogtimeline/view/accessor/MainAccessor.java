package de.frankbeeh.productbacklogtimeline.view.accessor;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

import org.testfx.api.FxRobot;
import org.testfx.service.support.WaitUntilSupport;

import com.google.common.base.Predicate;

import de.frankbeeh.productbacklogtimeline.service.FormatUtility;
import de.frankbeeh.productbacklogtimeline.view.BaseAccessor;
import de.frankbeeh.productbacklogtimeline.view.BaseUITest;
import de.frankbeeh.productbacklogtimeline.view.TableViewContent;

public class MainAccessor extends BaseAccessor {
    private static final String VELOCITY_FORECAST_TABLE_ID = "#velocityForecastTable";
    private static final String PRODUCT_BACKLOG_TABLE_ID = "#productBacklogTable";
    private static final String RELEASES_TABLE_ID = "#releasesTable";
    
    public MainAccessor(FxRobot fxRobot) {
        super(fxRobot);
    }

    public void assertSelectedProductTimestampEquals(String timestampName, LocalDate timestampDate) {
        assertEquals(getProductTimestampName(timestampName, timestampDate), getSelectedProductTimestamp());
    }
    
    public void selectProductTimestamp(final String productTimestampName, final LocalDate productTimestampDate) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getSelectedProductTimestampComboBox().getSelectionModel().select(getProductTimestampName(productTimestampName, productTimestampDate));
            }
        });
    }

    public void referenceProductTimestamp(final String productTimestampName, final LocalDate productTimestampDate) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getReferencedProductTimestampComboBox().getSelectionModel().select(getProductTimestampName(productTimestampName, productTimestampDate));
            }
        });
    }

    public void selectProductBacklogTab(BaseUITest baseUITest) {
        selectTab(baseUITest, "PBL");
    }

    public void selectVelocityForecastTab(BaseUITest baseUITest) {
        selectTab(baseUITest, "Velocity Forecast");
    }

    public void assertContentOfProductBacklogTableView(TableViewContent expectedContent) {
        assertContentOfTableView(PRODUCT_BACKLOG_TABLE_ID, expectedContent);
    }
    
    public void assertContentOfVelocityForecastTableView(TableViewContent expectedContent) {
        assertContentOfTableView(VELOCITY_FORECAST_TABLE_ID, expectedContent);
    }
    
    public void assertContentOfReleasesTableView(TableViewContent expectedContent) {
        assertContentOfTableView(RELEASES_TABLE_ID, expectedContent);
    }
    
    private void assertContentOfTableView(String selector, TableViewContent expectedContent) {
        waitUntilTableViewContentChanged(this.<TableView<?>> getUniqueNode(selector), expectedContent);
        final TableViewContent actualContent = getActualTableViewContent(this.<TableView<?>> getUniqueNode(selector));
        assertEquals(expectedContent.toString(), actualContent.toString());
    }
    
    private void waitUntilTableViewContentChanged(TableView<?> tableView, final TableViewContent expectedContent) {
        new WaitUntilSupport().waitUntil(tableView, new Predicate<TableView<?>>() {
            @Override
            public boolean apply(TableView<?> input) {
                return getActualTableViewContent(input).getRowCount() == expectedContent.getRowCount();
            }
        }, 2);
    }
    
    private String getSelectedProductTimestamp() {
        return getSelectedProductTimestampComboBox().getSelectionModel().getSelectedItem();
    }

    private String getProductTimestampName(final String productTimestampName, final LocalDate productTimestampDate) {
        return FormatUtility.formatLocalDateTime(productTimestampDate.atStartOfDay()) + " - " + productTimestampName;
    }

    private ComboBox<String> getSelectedProductTimestampComboBox() {
        return getUniqueNode("#selectedProductTimestamp");
    }

    private ComboBox<String> getReferencedProductTimestampComboBox() {
        return getUniqueNode("#referencedProductTimestamp");
    }

    private void selectTab(BaseUITest baseUITest, String tabTitle) {
        clickOn(getUniqueNode(tabTitle));
        assertEquals(tabTitle, baseUITest.getSelectedTabTitle());
    }

}
