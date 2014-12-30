package ui;

import core.Component;
import core.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import twitter.StatusProducer;
import twitter.UserProducer;
import twitter4j.FilterQuery;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @author Jonas
 * @version 1.0
 * @since 30/12/2014
 */

public class MainController extends Component implements Initializable {

    private Server server;
    private final Preferences preferences = Preferences.userNodeForPackage(this.getClass());

    public void setServer(Server server){
        this.server = server;
    }

    @FXML
    public TextField textFieldCom;

    @FXML
    public TextField textFieldQuery;

    @FXML
    public Label labelQueue;

    @FXML
    public void save() {
        preferences.put("COM", textFieldCom.getText());
        preferences.put("Keywords", textFieldQuery.getText());
    }

    @FXML
    public void start() {
        if(server != null){
            String[] keywords = textFieldQuery.getText().split(",");
            server.init(textFieldCom.getText());
            server.addComponent(this);
            server.addComponent(new UserProducer());
            server.addComponent(new StatusProducer(new FilterQuery().track(keywords)));
            server.start();
        }
    }

    @FXML
    public void stop() {
        if(server != null){
            server.stop();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.textFieldCom.setText(preferences.get("COM", "COM1"));
        this.textFieldQuery.setText(preferences.get("Keywords", ""));
    }

    @Override
    public void run() {
        while (true) {
            try {
                Platform.runLater(() -> labelQueue.setText(String.valueOf(getQueue().size())));
                Thread.sleep(2000);
            }catch(InterruptedException e){
                return;
            }
        }
    }
}
