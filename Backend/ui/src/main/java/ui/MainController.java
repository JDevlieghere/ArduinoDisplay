package ui;

import core.Component;
import core.Server;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import reddit.RedditProducer;
import reddit.StatusProducer;
import reddit.UserProducer;
import twitter4j.FilterQuery;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @author Jonas
 * @version 1.0
 * @since 1/01/2015
 */

public class MainController extends Component implements Initializable {

    private final Logger log = org.slf4j.LoggerFactory.getLogger(MainController.class);

    @FXML
    public TextField textKeywords;

    @FXML
    public TextField textComPort;

    @FXML
    public Label labelQueueItems;

    @FXML
    public CheckBox checkTwitter;

    @FXML
    public CheckBox checkReddit;

    @FXML
    public TextField textSubreddit;

    private Server server;

    private final Preferences preferences = Preferences.userNodeForPackage(this.getClass());

    public void setServer(Server server){
        this.server = server;
    }

    public void actionStop() {
        if(server != null){
            server.stop();
        }
    }

    public void actionStart() {
        if(server != null){
            String[] keywords = textKeywords.getText().split(",");
            server.init(textComPort.getText());
            server.addComponent(this);
            if(checkTwitter.isSelected()){
                server.addComponent(new UserProducer());
                server.addComponent(new StatusProducer(new FilterQuery().track(keywords)));
            }
            if(checkReddit.isSelected()){
                server.addComponent(new RedditProducer(textSubreddit.getText()));
            }
            server.start();
        }
    }

    public void actionSave() {
        preferences.put("COM", textComPort.getText());
        preferences.put("Keywords", textKeywords.getText());
        preferences.put("Subreddit", textSubreddit.getText());
        preferences.putBoolean("EnableTwitter", checkTwitter.isSelected());
        preferences.putBoolean("EnableReddit", checkReddit.isSelected());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.textComPort.setText(preferences.get("COM", "COM1"));
        this.textKeywords.setText(preferences.get("Keywords", ""));
        this.textSubreddit.setText(preferences.get("Subreddit", ""));
        this.checkTwitter.setSelected(preferences.getBoolean("EnableTwitter", false));
        this.checkReddit.setSelected(preferences.getBoolean("EnableReddit", false));

    }

    @Override
    public void run() {
        log.info(this.toString() + " started.");
        while (!isStopped()) {
            try {
                Platform.runLater(() -> labelQueueItems.setText(String.valueOf(getQueue().size())));
                Thread.sleep(2000);
            }catch(InterruptedException e){
                return;
            }
        }
    }
}
