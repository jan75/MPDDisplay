package file;

import fx.MPDFXMain;

import java.io.*;
import java.util.Properties;

/**
 * Created by jan on 1/29/16.
 */
public class ReadConfig {
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
            MPDFXMain.mpdGeneratedFiles = prop.getProperty("mpdGeneratedFiles");
            MPDFXMain.mpdHost = prop.getProperty("mpdHost");
            MPDFXMain.mpdPort = Integer.parseInt(prop.getProperty("mpdPort"));
            MPDFXMain.mpdCoversStartup = Integer.parseInt(prop.getProperty("mpdCoversStartup"));
            MPDFXMain.mpdCoversLoad = Integer.parseInt(prop.getProperty("mpdCoversLoad"));

            //
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