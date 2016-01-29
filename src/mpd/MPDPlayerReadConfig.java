package mpd;

import fx.MPDFXMain;

import java.io.*;
import java.util.Properties;

/**
 * Created by jan on 1/29/16.
 */
public class MPDPlayerReadConfig {
    public static void readConfig() {
        Properties prop = new Properties();
        InputStream input = null;

        try{
            input = new FileInputStream("src/resources/config.properties");
            //System.out.println(new File(".").getAbsolutePath());
            //
            prop.load(input);
            //
            MPDFXMain.mpdLibraryDir = prop.getProperty("mpdLibraryPath");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}