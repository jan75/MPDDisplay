package mpd;

import org.bff.javampd.MPD;

import java.io.File;

/**
 * Created by jan on 1/28/16.
 */
public class MPDOperations {


    public static String getString() {
        return null;
    }

    public String getCoverPath() {
        String mpdDatabase = "/mnt/media/Musik/";
        String mpdCoverPath = null;
        String filePath = mpdDatabase + mpd.getPlayer().getCurrentSong().getFile();
        File tmpFile = new File(filePath);
        String coverPathParent = tmpFile.getParent().toString();
        File tmpFileTester;
        //
        String[] filePathExtension = {mpd.getPlayer().getCurrentSong().getAlbumName().toString() + ".jpg", mpd.getPlayer().getCurrentSong().getAlbumName().toString() + ".png", "Cover.jpg", "cover.jpg", "Folder.jpg", "folder.jpg", "Cover.png", "cover.png", "Folder.png", "folder.png"};



        for(int i = 0; i < filePathExtension.length; i++) {
            String tmpCoverPath = coverPathParent + "/" + filePathExtension[i];
            if(new File(tmpCoverPath).exists()) {
                //System.out.println(tmpCoverPath);
                mpdCoverPath = tmpCoverPath;
                break;
            }
        }

        if (mpdCoverPath != null) {
            return mpdCoverPath;
        } else {
            System.out.println("no cover found!");
            return null;
        }
    }


}
