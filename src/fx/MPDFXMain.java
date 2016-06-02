package fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.bff.javampd.MPD;

public class MPDFXMain extends Application {
	public static MPD mpd = null;
	public static String mpdLibraryDir = null;
	public static String mpdGeneratedFiles = null;
	public static String mpdHost = null;
	public static int mpdCoversStartup = 0;
	public static int mpdCoversLoad = 0;
	public static int mpdPort = 0;

	@Override
	public void start(Stage primaryStage) {
		//
		try{
			//
			FXMLLoader loader= new FXMLLoader();
			BorderPane root = loader.load(this.getClass().getResource("/fx/javaFXLayout.fxml"));
			Scene scene = new Scene(root, 1600, 900);
			//Scene scene = new Scene(root, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(true);
			primaryStage.setTitle("MPDDisplay");

			//primaryStage.getIcons().add(new Image(MPDFXMain.class.getResourceAsStream("/fx/ApplicationIcon.png")));

			primaryStage.show();
			//
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}