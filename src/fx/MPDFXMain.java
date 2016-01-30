package fx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mpd.MPDOperations;
import org.bff.javampd.MPD;
import mpd.MPDPlayerReadConfig;

public class MPDFXMain extends Application {
	public static MPD mpd = null;
	public static String mpdLibraryDir = null;
	public static String mpdHost = null;
	public static int mpdPort = 0;

	@Override
	public void start(Stage primaryStage) {
		//
		try{
			Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			//
			FXMLLoader loader= new FXMLLoader();
			GridPane root = loader.load(this.getClass().getResource("/fx/javaFXLayout.fxml"));
			Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
			//Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.setTitle("MPDDisplay");
			primaryStage.setFullScreen(true);

			//primaryStage.setOnCloseRequest(event -> System.exit(0)); // all Threads are running as daemons

			//primaryStage.getIcons().add(new Image(MPDFXMain.class.getResourceAsStream("/fx/ApplicationIcon.png")));

			primaryStage.show();
			//
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}