package mpd;

import fx.MPDFXMain;
import org.bff.javampd.Database;
import org.bff.javampd.MPD;
import org.bff.javampd.objects.MPDAlbum;
import org.bff.javampd.objects.MPDSong;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jan on 1/28/16.
 */
public class MPDOperations {

    public static MPD connectMPD() {
        MPD mpd = null;
        try{
            if(MPDFXMain.mpdHost != "" && MPDFXMain.mpdPort != 0) {
                mpd = new MPD.Builder()
                        .server(MPDFXMain.mpdHost)
                        .port(MPDFXMain.mpdPort)
                        .build();
            } else {
                mpd = new MPD.Builder()
                        .server("localhost")
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

    public static HashMap<String, String> buildCoverDatabase() {
        HashMap<String, String> mpdAlbumStringHashMap = new HashMap<String, String>();
        mpdAlbumStringHashMap.clear();
        MPD mpd = MPDFXMain.mpd;

        String mpdFileFolder = MPDFXMain.mpdLibraryDir;

        Database mpdDatabase = mpd.getDatabase();
        TreeSet<MPDAlbum> mpdAlbumTreeSet;
        ArrayList<MPDSong> mpdSongArrayList = new ArrayList<MPDSong>();

        String mpdCoverPath = null;

        try {
            Collection<MPDAlbum> mpdAlbumCollection = mpdDatabase.listAllAlbums();
            mpdAlbumTreeSet = new TreeSet<MPDAlbum>(mpdAlbumCollection);

            for(MPDAlbum mpdAlbum: mpdAlbumTreeSet) {
                mpdSongArrayList.clear();

                Collection<MPDSong> mpdSongCollection = mpdDatabase.findAlbum(mpdAlbum);
                mpdSongArrayList.addAll(mpdSongCollection);
                String mpdSongFolder = mpdSongArrayList.get(0).getFile();

                String filePath = mpdFileFolder + mpdSongFolder;
                File tmpFile = new File(filePath);
                String coverPathParent = tmpFile.getParent();

                String[] filePathExtension = {mpdAlbum.getName() + ".jpg", mpdAlbum.getName() + ".png", "Cover.jpg", "cover.jpg", "Folder.jpg", "folder.jpg", "Cover.png", "cover.png", "Folder.png", "folder.png", "Front.jpg", "front.jpg", "Front.png", "front.png"};

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

                    if (new File(tmpCoverPath).exists()) {
                        mpdCoverPath = tmpCoverPath;
                        break;
                    }
                }

                if (mpdCoverPath != null) {
                    mpdAlbumStringHashMap.put(mpdAlbum.getName(), mpdCoverPath);
                } else {
                    mpdAlbumStringHashMap.put(mpdAlbum.getName(), "null");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mpdAlbumStringHashMap;
    }
}
