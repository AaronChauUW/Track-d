package edu.washington.chau93.trackd;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Aaron Chau on 3/8/2015.
 */
public class TrackdApp extends Application {
    private final String TAG = "TrackdApp";

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        initTrackd();
    }


    protected void initTrackd() {
        // Init Trackd singleton
        Trackd.initInstance();
        Trackd.setJsonData(makeJSON());
    }

    public InputStream makeJSON(){
        InputStream is = null;
        try {
            // Lets get our file
            File f = new File(getExternalFilesDir(null), "trackd.json");
            if(f.exists()){
                // If it exist then we'll use it.
                is = new FileInputStream(f);
            } else {
                // Otherwise use the default file from assets folder
                is = this.getAssets().open("data/trackd.json");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }




}
