package ui;

import core.Server;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MainApplication extends Application {

    private static final ShutdownHook SHUTDOWN_HOOK = new ShutdownHook();
    private Server server;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws IOException {
        // Create Server
        this.server = new Server();

        SHUTDOWN_HOOK.addTask(() -> server.stop());

        final ResourceBundle rb = ResourceBundle.getBundle("localization");
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), rb);
        final Parent root = loader.load();
        final Scene scene = new Scene(root);
        final MainController ctrl = loader.getController();

        ctrl.setServer(server);

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

        private Set<Runnable> tasks = Collections.newSetFromMap(new ConcurrentHashMap<>());

        public void addTask(Runnable r){
            tasks.add(r);
        }

        public void removeTask(Runnable r){
            tasks.remove(r);
        }

        @Override
        public void run() {
            tasks.forEach(r -> r.run());
        }
    }

}