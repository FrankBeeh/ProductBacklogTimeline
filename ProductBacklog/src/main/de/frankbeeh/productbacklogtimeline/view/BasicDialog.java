package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class BasicDialog<T> extends Stage {

    private T result;

    public BasicDialog(String title) {
        super();
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getFXMLResourceURL());
            final AnchorPane page = (AnchorPane) loader.load();
            setTitle(title);
            initModality(Modality.APPLICATION_MODAL);
            initOwner(null);
            final Scene scene = new Scene(page);
            setScene(scene);
            loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract URL getFXMLResourceURL();

    // TODO: Return entity on save, return null on cancel or exit, or if nothing needs to be returned
    public T openDialog() {
        super.showAndWait();
        return result;
    }
}
