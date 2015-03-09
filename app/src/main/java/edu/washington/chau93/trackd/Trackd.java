package edu.washington.chau93.trackd;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron Chau on 3/8/2015.
 */
public class Trackd {
    private static final String TAG = "Trackd";
    private static final String JSON_EVENTS = "event";
    private static final String JSON_ORG =  "org";

    private static Trackd instance;
    private static JSONObject jsonData;

    public static void initInstance() {
        if(instance == null){
            instance = new Trackd();
        }
    }

    private Trackd(){
        // Constructor
    }

    public static Trackd getInstance(){
        return instance;
    }

    // Finds a specific event by name.
    public static JSONObject findEventByName(String name) throws JSONException {
        return find(getEvents(), name);
    }

    // Finds a specific organization by name.
    public static JSONObject findOrgByName(String name) throws JSONException {
        return find(getOrgs(), name);

    }

    // Generic find method by name.
    private static JSONObject find(JSONArray array, String name) throws JSONException {
        for(int i = 0; i < array.length(); i++){
            if(array.getJSONObject(i).getString("name").equals(name)){
                return array.getJSONObject(i);
            }
        }
        return null;
    }

    // Returns an array of events
    public static JSONArray getEvents() throws JSONException {
        return jsonData.getJSONArray(JSON_EVENTS);
    }

    // Returns an array of organizations
    public static JSONArray getOrgs() throws JSONException {
        return jsonData.getJSONArray(JSON_ORG);
    }

    public static void setJsonData(JSONObject jsonData) {
        Trackd.jsonData = jsonData;
    }
}
