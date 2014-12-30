package ui;

import core.Server;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import twitter.TwitterProducer;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @author Jonas
 * @version 1.0
 * @since 30/12/2014
 */

public class MainController implements Initializable {

    private Server server;
    private final Preferences preferences = Preferences.userNodeForPackage(this.getClass());

    public void setServer(Server server){
        this.server = server;
    }

    @FXML
    public TextField textFieldCom;

    @FXML
    public void save() {
        preferences.put("COM", textFieldCom.getText());
    }

    @FXML
    public void start() {
        if(server != null){
            server.init(textFieldCom.getText());
            server.addComponent(new TwitterProducer());
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
    }
}
