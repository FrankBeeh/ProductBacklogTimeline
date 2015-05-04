package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Skin;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import org.junit.After;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.support.WaitUntilSupport;

import com.google.common.base.Predicate;
import com.sun.javafx.scene.control.skin.LabeledText;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.UITestServiceRegistry;
import de.frankbeeh.productbacklogtimeline.view.accessor.ImportProductTimestampDialogAccessor;

public class BaseUITest extends ApplicationTest {
    final BaseAccessor baseAccessor;

    public BaseUITest() {
        baseAccessor = new BaseAccessor(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServiceLocator.init(new UITestServiceRegistry());
        final FXMLLoader loader = new FXMLLoader(getFXMLResourceURL());
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);
        stage.show();

        final MainController controller = loader.getController();
        controller.initController(stage);
    }

    @After
    public void closeServiceLocator() throws Exception {
        ServiceLocator.close();
    }

    // TODO Remove this duplicate of BaseAccessor.getUniqueNode()
    @SuppressWarnings("unchecked")
    public <T extends Node> T getUniqueNode(final String selector) {
        final Set<Node> foundNodes = removeSkinNodes(lookup(selector).<Node> queryAll());
        if (foundNodes.isEmpty()) {
            throw new RuntimeException("No node found for selector '" + selector + "'!");
        }
        if (foundNodes.size() > 1) {
            throw new RuntimeException("Multiple nodes found for selector '" + selector + ": " + foundNodes + "!");
        }
        return (T) foundNodes.iterator().next();
    }

    protected void selectVelocityForecastTab() {
        selectTab("Velocity Forecast");
    }

    protected void selectProductBacklogTab() {
        selectTab("PBL");
    }

    protected void assertContentOfTableView(String selector, TableViewContent expectedContent) {
        waitUntilTableViewContentChanged(this.<TableView<?>> getUniqueNode(selector), expectedContent);
        final TableViewContent actualContent = getActualTableViewContent(this.<TableView<?>> getUniqueNode(selector));
        assertEquals(expectedContent.toString(), actualContent.toString());
    }

    protected void importProductTimestamp(String productBacklogFileName, String velocityForecastFileName) {
        final ImportProductTimestampDialogAccessor dialogAccessor = openProductTimelineImportDialog();
        dialogAccessor.enterFileNames(productBacklogFileName, velocityForecastFileName);
    }

    private ImportProductTimestampDialogAccessor openProductTimelineImportDialog() {
        clickOn("File");
        clickOn("Import Product Timestamp");
        return new ImportProductTimestampDialogAccessor(this);
    }
    
    private void selectTab(String tabTitle) {
        clickOn(tabTitle);
        assertEquals(tabTitle, getSelectedTabTitle());
    }

    private TableViewContent getActualTableViewContent(TableView<?> tableView) {
        final TableViewContent actualContent = new TableViewContent();
        final Predicate<Node> nodePredicate = new Predicate<Node>() {
            @Override
            public boolean apply(Node node) {
                if (node instanceof TableRow) {
                    actualContent.addRow();
                }
                if (node instanceof LabeledText) {
                    actualContent.addCellContent(((LabeledText) node).getText());
                }
                if (node instanceof StackPane) {
                    actualContent.stopAdding();
                }
                return false;
            }
        };
        from(tableView).lookup(nodePredicate).queryFirst();
        return actualContent;
    }

    private void waitUntilTableViewContentChanged(TableView<?> tableView, final TableViewContent expectedContent) {
        new WaitUntilSupport().waitUntil(tableView, new Predicate<TableView<?>>() {
            @Override
            public boolean apply(TableView<?> input) {
                return getActualTableViewContent(input).getRowCount() == expectedContent.getRowCount();
            }
        }, 2);
    }

    private String getSelectedTabTitle() {
        return getTabPane().getSelectionModel().getSelectedItem().getText();
    }

    private TabPane getTabPane() {
        return getUniqueNode("#mainTabPane");
    }

    private URL getFXMLResourceURL() {
        return MainController.class.getResource("main.fxml");
    }

    // TODO Remove this duplicate of BaseAccessor.removeSkinNodes()
    private Set<Node> removeSkinNodes(Set<Node> foundNodes) {
        final Set<Node> filteredNodes = new HashSet<Node>();
        for (final Node node : foundNodes) {
            if (!(node instanceof Skin) && !(node.getClass().getName().contains("Skin"))) {
                filteredNodes.add(node);
            }
        }
        return filteredNodes;
    }
}
