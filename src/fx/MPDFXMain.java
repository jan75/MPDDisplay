package fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.bff.javampd.MPD;

import java.io.File;

public class MPDFXMain extends Application {
	//private final static Logger LOGGER = Logger.getLogger("charSheetLogger");

	@Override
	public void start(Stage primaryStage) {
		//initLoggers();
		testMethod();
		//
		try{
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			//
			FXMLLoader loader= new FXMLLoader();
			StackPane root = loader.load(this.getClass().getResource("/fx/javaFXLayout.fxml"));
			Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("MPDDisplay");
			//primaryStage.setFullScreen(true);
			//primaryStage.getIcons().add(new Image(MPDFXMain.class.getResourceAsStream("/fx/ApplicationIcon.png")));
			primaryStage.show();
		} catch(Exception e) {
			//LOGGER.log(Level.SEVERE, "Exception " + e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/*
	private void initLoggers() {
		String datum = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
		//
		try {
			System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tc %2$s%n%4$s: %5$s%6$s%n");
			//
			new File(System.getProperty("user.home") + "/CharacterSheetLogs").mkdir();
			Handler fileHandlerSevere = new FileHandler(System.getProperty("user.home") + "/CharacterSheetLogs/" + datum + "_severe.log", true);
			Handler fileHandlerInfo = new FileHandler(System.getProperty("user.home") + "/CharacterSheetLogs/" + datum + "_info.log", true);
			fileHandlerSevere.setLevel(Level.SEVERE);
			fileHandlerInfo.setFilter(record -> record.getLevel().equals(Level.INFO));
			Formatter singleLineFormat = new SimpleFormatter();
			fileHandlerInfo.setFormatter(singleLineFormat);
			fileHandlerSevere.setFormatter(singleLineFormat);
			//
			LOGGER.addHandler(fileHandlerSevere);
			LOGGER.addHandler(fileHandlerInfo);
			//
			LOGGER.log(Level.INFO, "Info Log initiated");
			LOGGER.log(Level.SEVERE, "Severe Log Initiated");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Exception " + e.getMessage(), e);
		}
	}
	*/

	private void testMethod() {
		try{
			MPD mpd = new MPD.Builder().build();
			//
			//mpd.getPlaylist().clearPlaylist();
			//
			//System.out.println(mpd.getDatabase().findAlbum("21").size());
			//System.out.println(mpd.getPlayer().getCurrentSong().getArtistName().toString());


			//System.out.println(tmpFile.getParent());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}