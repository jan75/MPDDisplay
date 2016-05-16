package fx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import mpd.MPDOperations;
import mpd.MPDPlayerReadConfig;
import org.bff.javampd.MPD;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Jan on 02.01.2016.
 */
public class FXMLController implements Initializable {
    //private final static Logger LOGGER = Logger.getLogger("charSheetLogger");

    @FXML private TextArea textAreaMain;
    @FXML private MenuItem findCovers;
    @FXML private ImageView imageViewCover;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        MPDPlayerReadConfig.readConfig();
        MPDFXMain.mpd = MPDOperations.connectMPD();
        MPD mpd = MPDFXMain.mpd;

        /*
        ExecutorService service = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        service.execute(() -> checkForChanges());
        */
    }

    @FXML
    private void findCovers() {
        HashMap<String, String> mpdAlbumStringHashMap = MPDOperations.buildCoverDatabase();
        for(Map.Entry<String, String> mpdAlbum: mpdAlbumStringHashMap.entrySet()) {
            textAreaMain.appendText(mpdAlbum.getKey() + ";" + mpdAlbum.getValue() + "\n");
        }
    }

    @FXML
    private void loadTrack() {
        try {
            System.out.println(MPDFXMain.mpd.getPlayer().getCurrentSong().getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI() {
        loadImage();
        loadTrack();
    }

    @FXML
    private void loadImage() {
        //String pathToCover = MPDOperations.getCoverPath();
        //System.out.println(pathToCover);

        /*
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
        */
    }

    @FXML
    private void prevTrack() {
        MPD mpd = MPDFXMain.mpd;

        try {
            mpd.getPlayer().playPrev();
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
    */
}