package edu.washington.chau93.trackd;

import android.app.Application;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public JSONObject makeJSON(){
        JSONObject dataObj = null;
        try {
            InputStream is = this.getAssets().open("data/trackd.json");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String jsonString = new String(buffer);
            dataObj = new JSONObject(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataObj;
    }


}
