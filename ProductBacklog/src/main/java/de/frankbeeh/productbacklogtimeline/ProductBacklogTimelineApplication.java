package de.frankbeeh.productbacklogtimeline;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.view.MainController;

public class ProductBacklogTimelineApplication extends Application {

    private static final String APPLICATION_TITLE = "Product Backlog Timeline";
    private static final String APPLICATION_FXML = "view/main.fxml";
    private static final String MANIFEST_FILE = "/META-INF/MANIFEST.MF";
    private static final String CSS_FILE = "css/productbacklog.css";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(APPLICATION_FXML));
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource(CSS_FILE).toExternalForm());
        primaryStage.setTitle(APPLICATION_TITLE + " - " + readVersionFromManifest());
        primaryStage.setScene(scene);
        primaryStage.show();

        final MainController controller = loader.getController();
        controller.initController(primaryStage);
    }

    private String readVersionFromManifest() throws MalformedURLException, IOException {
        final Class<ProductBacklogTimelineApplication> clazz = ProductBacklogTimelineApplication.class;
        final String className = clazz.getSimpleName() + ".class";
        final String classPath = clazz.getResource(className).toString();
        if (!classPath.startsWith("jar")) {
            return "0.0";
        }
        final String manifestPath = classPath.substring(0, classPath.lastIndexOf("!") + 1) + MANIFEST_FILE;
        final Manifest manifest = new Manifest(new URL(manifestPath).openStream());
        final Attributes attr = manifest.getMainAttributes();
        return attr.getValue("Implementation-Version");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
