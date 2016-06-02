package fx;

import file.FileOperations;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import mpd.MPDOperations;
import file.ReadConfig;
import org.bff.javampd.Database;
import org.bff.javampd.MPD;
import org.bff.javampd.exception.MPDDatabaseException;
import org.bff.javampd.objects.MPDAlbum;
import org.bff.javampd.objects.MPDSong;

import java.net.URL;
import java.util.*;

/**
 * Created by Jan on 02.01.2016.
 */
public class FXMLController implements Initializable {
    //private final static Logger LOGGER = Logger.getLogger("charSheetLogger");

    @FXML private Label labelBottom;
    @FXML private MenuItem findCovers;
    @FXML private TilePane tilePaneCovers;
    @FXML private ScrollPane scrollPaneCenter;
    //@FXML private ImageView imageViewCover;

    private HashMap<String, String> mpdAlbumStringHashMap;
    MPD mpd;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ReadConfig.readConfig();
        MPDFXMain.mpd = MPDOperations.connectMPD();
        mpd = MPDFXMain.mpd;

        if(FileOperations.checkIfCSVExists(MPDFXMain.mpdGeneratedFiles + "\\AlbumsCovers.csv")) {
            mpdAlbumStringHashMap = FileOperations.CSVFileToHashMap(MPDFXMain.mpdGeneratedFiles + "\\AlbumsCovers.csv");
        } else {
            mpdAlbumStringHashMap = MPDOperations.buildCoverDatabase();
            FileOperations.hashMapToCSVFile(MPDFXMain.mpdGeneratedFiles + "\\AlbumsCovers.csv", mpdAlbumStringHashMap);
        }


        /*
        ExecutorService service = Executors.newFixedThreadPool(2, r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        service.execute(() -> checkForChanges());
        */

        scrollPaneCenter.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneCenter.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPaneCenter.setFitToWidth(true);
        scrollPaneCenter.setFitToHeight(true);

        scrollPaneCenter.vvalueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
           if(newValue.doubleValue() >= 0.8) {
               System.out.println("at bottom");
           }
        });

        tilePaneCovers.setVgap(5);
        tilePaneCovers.setHgap(5);

        Image tmpImage;
        String tmpAlbumName, tmpCoverPath;
        int l = 0;
        for(Map.Entry<String, String> entry: mpdAlbumStringHashMap.entrySet()) {
            tmpAlbumName = entry.getKey().toString();
            tmpCoverPath = entry.getValue().toString();

            if(tmpCoverPath != "null") {
                System.out.println(tmpCoverPath);

                tmpImage = new Image("file:///" + tmpCoverPath);
                ImageView imageView;
                imageView = createImageView(tmpImage, tmpAlbumName);
                tilePaneCovers.getChildren().add(imageView);
            } else {
                continue;
            }

            if(l == MPDFXMain.mpdCoversStartup) {
                break;
            } else {
                l++;
            }
        }
    }

    private ImageView createImageView(Image image, String userData) {
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(240);
        imageView.setFitWidth(240);
        imageView.setUserData(userData);
        imageView.setOnMouseClicked(event -> {
            labelBottom.setText(imageView.getUserData().toString());
            Database mpdDatabase = mpd.getDatabase();
            Collection<MPDSong> mpdSongCollection = new ArrayList<MPDSong>();
            try {
                mpdSongCollection = mpdDatabase.findAlbum(imageView.getUserData().toString());
            } catch (MPDDatabaseException e) {
                e.printStackTrace();
            }
            ArrayList<MPDSong> arrayListSongs = new ArrayList<MPDSong>();
            arrayListSongs.addAll(mpdSongCollection);
            labelBottom.setText(arrayListSongs.get(0).getArtistName() + " - " + arrayListSongs.get(0).getAlbumName());

            System.out.println(scrollPaneCenter.getVvalue());
        });
        return imageView;
    }

    @FXML
    private void findCovers() {
        Alert findCoversAlert = new Alert(Alert.AlertType.CONFIRMATION);
        findCoversAlert.setTitle("Scan Music folder for Files?");
        findCoversAlert.setHeaderText("Scanning the folders and creating the CSV might take some time, are you sure you want to continue?");
        findCoversAlert.showAndWait();
        if(!findCoversAlert.getResult().getButtonData().isCancelButton()) {
            mpdAlbumStringHashMap = MPDOperations.buildCoverDatabase();
            FileOperations.hashMapToCSVFile(MPDFXMain.mpdGeneratedFiles + "\\AlbumsCovers.csv", mpdAlbumStringHashMap);
        }
    }

    @FXML
    private void about() {
        Alert aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About MPDDisplay");
        aboutAlert.setHeaderText("A program to display local album covers from your MPD library");
        aboutAlert.setContentText("This program is still under development.\nwww.github.com/jan75/MPDDisplay");
        aboutAlert.showAndWait();
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