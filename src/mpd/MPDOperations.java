package mpd;

import fx.MPDFXMain;
import org.bff.javampd.MPD;
import org.bff.javampd.StandAloneMonitor;
import org.bff.javampd.events.PlayerBasicChangeEvent;
import org.bff.javampd.events.PlayerBasicChangeListener;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jan on 1/28/16.
 */
public class MPDOperations {

    public static MPD connectMPD() {
        MPD mpd = null;
        try{
            //String mpdHost = '"' + MPDFXMain.mpdHost + '"';

            if(MPDFXMain.mpdHost != "" && MPDFXMain.mpdPort != 0) {
                mpd = new MPD.Builder()
                        .server(MPDFXMain.mpdHost)
                        .port(MPDFXMain.mpdPort)
                        .build();
            } else {
                mpd = new MPD.Builder()
                        .server("192.168.0.18")
                        .port(6600)
                        .build();
            }

        } catch (Exception e) {
            System.out.println("No connection - is MPD running? Check your configuration in config.properties");
            e.printStackTrace();
        }

        if(mpd != null) {
            return mpd;
        } else {
            return null;
        }
    }


    public static String getString() {
        return null;
    }

    public static String getCoverPath() {
        MPD mpd = MPDFXMain.mpd;

        String mpdDatabase = MPDFXMain.mpdLibraryDir;
        //String mpdDatabase = "Z:/Musik/";
        String mpdCoverPath = null;
        try {
            String filePath = mpdDatabase + mpd.getPlayer().getCurrentSong().getFile();
            File tmpFile = new File(filePath);
            String coverPathParent = tmpFile.getParent().toString();
            File tmpFileTester;
            //
            String[] filePathExtension = {mpd.getPlayer().getCurrentSong().getAlbumName().toString() + ".jpg", mpd.getPlayer().getCurrentSong().getAlbumName().toString() + ".png", "Cover.jpg", "cover.jpg", "Folder.jpg", "folder.jpg", "Cover.png", "cover.png", "Folder.png", "folder.png", "Front.jpg", "front.jpg", "Front.png", "front.png"};

            for (int i = 0; i < filePathExtension.length; i++) {
                String tmpCoverPath;
                String osName = System.getProperty("os.name").toLowerCase();
                Matcher findOS = Pattern.compile("Windows").matcher(osName);
                if(osName.contains("windows")) {
                    tmpCoverPath = coverPathParent + "\\" + filePathExtension[i];
                } else if (osName.contains("linux")){
                    tmpCoverPath = coverPathParent + "/" + filePathExtension[i];
                } else {
                    tmpCoverPath = coverPathParent + "/" + filePathExtension[i];
                }
                //System.out.println(tmpCoverPath);
                if (new File(tmpCoverPath).exists()) {
                    mpdCoverPath = tmpCoverPath;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mpdCoverPath != null) {
            return mpdCoverPath;
        } else {
            System.out.println("no cover found!");
            return null;
        }
    }


}
