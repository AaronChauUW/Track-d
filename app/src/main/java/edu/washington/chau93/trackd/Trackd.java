package edu.washington.chau93.trackd;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static android.os.Environment.*;

/**
 * Created by Aaron Chau on 3/8/2015.
 */
public class Trackd {
    private static final String TAG = "Trackd";
    private static final String JSON_EVENTS = "event";
    private static final String JSON_ORG =  "org";

    private static Trackd instance;
    private static JSONObject jsonData;
    private static ArrayList<EventObj> eventList;
    private static ArrayList<OrganizationObj> orgList;
    private static boolean scheduleStarted;
    private static boolean updating;

    // Instantiate a new Trackd singleton if it's not already made.
    public static void initInstance() {
        if(instance == null){
            instance = new Trackd();
        }
    }

    // Trackd singleton constructor.
    private Trackd(){

        scheduleStarted = false;
        updating = false;

        // TODO: Might want to change these to tree sets so they can organize chronologically
        eventList = new ArrayList<>();
        orgList = new ArrayList<>();


    }

    // Get the Trackd singleton instance. Not exactly sure why this is needed.
    public static Trackd getInstance(){
        return instance;
    }

    // Finds a specific event by name.
    public static EventObj findEventById(String id)  {
        return (EventObj) findEventByName(getEvents(), id);
    }

    // Finds a specific organization by name.
    public static OrganizationObj findOrgById(String id) {
        return (OrganizationObj) findOrgByName(getOrgs(), id);

    }

    /**
    // Generic find method by name.
    private static Object findByName(ArrayList<?> list, String id) {
        for(Object o : list){
            if(o instanceof EventObj && ((EventObj) o).getId().equals(id)){
                return o;
            } else if(o instanceof OrganizationObj && ((OrganizationObj) o).getName().equals(id)){
                return o;
            }
        }
        return null;
    }
     */

    // Generic find method by name.
    private static EventObj findEventByName(ArrayList<EventObj> list, String id) {
        for(EventObj e : list){
            if(e.getId().equals(id)){

                return e;
            }
        }
        return null;
    }

    // Generic find method by name.
    private static OrganizationObj findOrgByName(ArrayList<OrganizationObj> list, String id) {
        for(OrganizationObj o : list){
            if(o.getId().equals(id)){
                return o;
            }
        }
        return null;
    }

    // Returns an array of events
    public static ArrayList<EventObj> getEvents() {
        return eventList;
    }

    // Returns an array of organizations
    public static ArrayList<OrganizationObj> getOrgs() {
        return orgList;
    }

    public static void setJsonData(InputStream is) {
        try {
            // Read the Input Stream file
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Make it into a string
            String jsonString = new String(buffer);

            // Set our jsonData field to our json data
            Trackd.jsonData = new JSONObject(jsonString);

            // Get the json data
            JSONArray jsonArrayEvents = jsonData.getJSONArray(JSON_EVENTS);
            JSONArray jsonArrayOrgs = jsonData.getJSONArray(JSON_ORG);

            // Clear the current list
            // TODO: Might want to change these to tree sets so they can organize chronologically
            eventList = new ArrayList<>();
            orgList = new ArrayList<>();

            // Add the data into an array list.
            for(int i = 0; i < jsonArrayEvents.length() -1; i++){
                eventList.add(new EventObj(jsonArrayEvents.getJSONObject(i), i));
            }

            for(int j = 0; j < jsonArrayOrgs.length() -1; j++){
                orgList.add(new OrganizationObj(jsonArrayOrgs.getJSONObject(j), j));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScheduleStarted() {
        Trackd.scheduleStarted = true;
    }

    public static boolean isScheduleStarted(){
        return scheduleStarted;
    }

    public static boolean isUpdating() { return updating; }

    public static void setUpdating(boolean updating) { Trackd.updating = updating; }

    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.System.AIRPLANE_MODE_ON, 0) != 0;

    }

    public static boolean isExternalStorageAvailable(){
        boolean mExternalStorageAvailable = false;
        String state = getExternalStorageState();

        if (MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageAvailable = true;
        } else if (MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageAvailable = true;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageAvailable = false;
        }
        return mExternalStorageAvailable;
    }

    public static boolean isExternalStorageWritable(){
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // We can read and write the media
            mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // We can only read the media
            mExternalStorageWriteable = false;
        } else {
            // Something else is wrong. It may be one of many other states, but all we need
            //  to know is we can neither read nor write
            mExternalStorageWriteable = false;
        }
        return mExternalStorageWriteable;
    }
}
