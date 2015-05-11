package de.frankbeeh.productbacklogtimeline.view;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Skin;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import org.junit.After;
import org.testfx.framework.junit.ApplicationTest;

import de.frankbeeh.productbacklogtimeline.service.ServiceLocator;
import de.frankbeeh.productbacklogtimeline.service.database.UITestServiceRegistry;
import de.frankbeeh.productbacklogtimeline.view.accessor.MainAccessor;
import de.frankbeeh.productbacklogtimeline.view.accessor.MenuAccessor;

public class BaseUITest extends ApplicationTest {
    private static final String CSS_FILE = "../css/productbacklog.css";
    
    private final MenuAccessor menuAccessor;
    private final MainAccessor mainAccessor;

    public BaseUITest() {
        menuAccessor = new MenuAccessor(this);
        mainAccessor = new MainAccessor(this);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ServiceLocator.init(new UITestServiceRegistry());
        final FXMLLoader loader = new FXMLLoader(getFXMLResourceURL());
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource(CSS_FILE).toExternalForm());
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

    public MenuAccessor getMenuAccessor() {
        return menuAccessor;
    }
    
    public MainAccessor getMainAccessor() {
        return mainAccessor;
    }
    
    public String getSelectedTabTitle() {
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
