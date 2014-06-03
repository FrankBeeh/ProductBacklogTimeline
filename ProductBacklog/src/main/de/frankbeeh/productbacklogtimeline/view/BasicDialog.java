package de.frankbeeh.productbacklogtimeline.view;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class BasicDialog<T> extends Stage {

    public BasicDialog(String title) {
        super();
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getFXMLResourceURL());
            loader.setController(getDialogController());
            final AnchorPane page = (AnchorPane) loader.load();
            setTitle(title);
            initModality(Modality.APPLICATION_MODAL);
            initOwner(null);
            final Scene scene = new Scene(page);
            setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected abstract Object getDialogController();

    protected abstract URL getFXMLResourceURL();

    protected abstract T createResult();

    public void closeDialog() {
        hide();
    }

    public T openDialog() {
        showAndWait();
        final T dialogResult = createResult();
        return dialogResult;
    }

    public abstract void setEntity(T selectedItem);
}
