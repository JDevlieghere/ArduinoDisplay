package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    private static final ShutdownHook SHUTDOWN_HOOK = new ShutdownHook();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {

        final ResourceBundle rb = ResourceBundle.getBundle("localization");
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), rb);
        final Parent root = loader.load();
        final Scene scene = new Scene(root);

        primaryStage.setTitle(rb.getString("app.name"));
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void stop() {
        SHUTDOWN_HOOK.run();
    }


    private static class ShutdownHook extends Thread {
        @Override
        public void run() {
            // TODO: Implement
        }
    }

}