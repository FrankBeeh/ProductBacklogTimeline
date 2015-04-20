package de.frankbeeh.productbacklogtimeline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import de.frankbeeh.productbacklogtimeline.view.MainController;

public class ProductBacklogTimelineApplication extends Application {

    private static final String APPLICATION_TITLE = "Product Backlog Timeline";
    private static final String APPLICATION_FXML = "view/main.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource(APPLICATION_FXML));
        final Parent root = (Parent) loader.load();

        final Scene scene = new Scene(root, 1200, 800);
        scene.getStylesheets().add(ProductBacklogTimelineApplication.class.getResource("css/productBacklog.css").toExternalForm());
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();

        final MainController controller = loader.getController();
        controller.initController(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
