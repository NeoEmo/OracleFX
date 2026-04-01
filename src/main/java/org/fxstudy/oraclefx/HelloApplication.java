package org.fxstudy.oraclefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1400, 800);
        scene.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        InputStream iconStream = getClass().getResourceAsStream("/org/fxstudy/oraclefx/icon.jpg");

        Image image = new Image(Objects.requireNonNull(iconStream));
        System.setProperty("prism.allowhidpi", "false");
        stage.getIcons().add(image);
        stage.setTitle("OracleFX 0.4");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
