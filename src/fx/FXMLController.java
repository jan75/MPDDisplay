package fx;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import mpd.MPDOperations;
import mpd.MPDPlayerReadConfig;
import org.bff.javampd.MPD;
import org.bff.javampd.MPDSocket;
import org.bff.javampd.Player;
import org.bff.javampd.StandAloneMonitor;
import org.bff.javampd.events.PlayerChangeEvent;
import org.bff.javampd.events.PlayerChangeListener;
import org.bff.javampd.exception.MPDPlayerException;
import org.bff.javampd.exception.MPDResponseException;
import org.bff.javampd.monitor.MPDConnectionMonitor;
import org.bff.javampd.monitor.MPDStandAloneMonitor;
import org.bff.javampd.monitor.PlayerStatus;

import java.lang.management.OperatingSystemMXBean;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jan on 02.01.2016.
 */
public class FXMLController implements Initializable {
    //private final static Logger LOGGER = Logger.getLogger("charSheetLogger");
    static double tmpSize = Screen.getPrimary().getVisualBounds().getHeight() * 0.8;

    @FXML
    private ImageView imgCover;
    @FXML
    private GridPane gridPaneRoot;
    @FXML
    private StackPane paneCenter;
    @FXML
    private RowConstraints row1;
    @FXML
    private VBox vboxImg;
    @FXML
    private Label infoTrack;
    @FXML
    private Label infoArtistAlbum;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MPDPlayerReadConfig.readConfig();

        MPDFXMain.mpd = MPDOperations.connectMPD();

        loadTrack();
        loadImage();

        paneCenter.setMinSize(tmpSize, tmpSize);
        paneCenter.setPrefSize(tmpSize, tmpSize);
        paneCenter.setMaxSize(tmpSize, tmpSize);

        fitImage();

        ExecutorService service = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        service.execute(() -> checkForChanges());

        //System.out.println(paneCenter.getMinWidth() + " " + paneCenter.getMinHeight());
        //imgCover.setFitWidth(imgCover.getFitHeight());
        //

        //mpdStandAloneMonitor.start();

        //mpdMonitor.getMonitor().addPlayerChangeListener(event -> System.out.println("monitor playerchangelistener event fired"));

    }

    private void checkForChanges() {
        MPD mpd = MPDFXMain.mpd;
        Player mpdPlayer = mpd.getPlayer();

        try {
            String currentSong = mpdPlayer.getCurrentSong().getTitle();

            while (true) {
                if (currentSong != mpdPlayer.getCurrentSong().getTitle()) {
                    Platform.runLater(() -> updateUI());
                    currentSong = mpdPlayer.getCurrentSong().getTitle();
                }
                Thread.sleep(2000);
            }
        } catch (MPDPlayerException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadTrack() {
        try {
            System.out.println(MPDFXMain.mpd.getPlayer().getCurrentSong().getTitle());
            infoTrack.setText("");
            infoArtistAlbum.setText("");
            infoTrack.setText(MPDFXMain.mpd.getPlayer().getCurrentSong().getTitle());
            infoArtistAlbum.setText(MPDFXMain.mpd.getPlayer().getCurrentSong().getAlbumName() + " - " + MPDFXMain.mpd.getPlayer().getCurrentSong().getArtistName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fitImage() {

        if (imgCover.getImage() != null) {

            if (imgCover.getImage().getHeight() > tmpSize || imgCover.getImage().getWidth() > tmpSize) {
                //System.out.println("bigger");
                imgCover.fitWidthProperty().bind(paneCenter.widthProperty());
                imgCover.fitHeightProperty().bind(paneCenter.heightProperty());
            } else {
                //System.out.println("smaller or equal");
                imgCover.fitWidthProperty().bind(imgCover.getImage().widthProperty());
                imgCover.fitHeightProperty().bind(imgCover.getImage().heightProperty());
            }
        }
    }

    private void updateUI() {
        loadImage();
        loadTrack();
    }

    @FXML
    private void loadImage() {
        String pathToCover = MPDOperations.getCoverPath();

        //System.out.println(pathToCover);

        Image tmpImage;
        //
        if (pathToCover != null) {
            try {
                tmpImage = new Image(String.valueOf(new URL("file:" + pathToCover)));
                imgCover.setImage(tmpImage);
                fitImage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tmpImage = new Image("resources/noCover.jpg"); // source http://cmdrobot.deviantart.com/art/No-album-art-no-cover-placeholder-picture-458050685
            imgCover.setImage(tmpImage);
            fitImage();
        }
    }

    @FXML
    private void prevTrack() {
        MPD mpd = MPDFXMain.mpd;

        try {
            mpd.getPlayer().playPrev();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void nextTrack() {
        MPD mpd = MPDFXMain.mpd;

        try {
            mpd.getPlayer().playNext();
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void togglePlayPause() {
        MPD mpd = MPDFXMain.mpd;

        try {
            if (mpd.getMonitor().isStopped()) {
                mpd.getPlayer().play();
            } else {
                mpd.getPlayer().pause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    // remains of old code
    @FXML
    private void exitApplication() {
        System.exit(0);
    }
    */
}