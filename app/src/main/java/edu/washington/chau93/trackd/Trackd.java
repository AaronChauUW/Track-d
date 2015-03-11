package edu.washington.chau93.trackd;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

    // Instantiate a new Trackd singleton if it's not already made.
    public static void initInstance() {
        if(instance == null){
            instance = new Trackd();
        }
    }

    // Trackd singleton constructor.
    private Trackd(){
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
        return (EventObj) findByName(getEvents(), id);
    }

    // Finds a specific organization by name.
    public static OrganizationObj findOrgByName(String id) {
        return (OrganizationObj) findByName(getOrgs(), id);

    }

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

    // Returns an array of events
    public static ArrayList<EventObj> getEvents() {
        return eventList;
    }

    // Returns an array of organizations
    public static ArrayList<OrganizationObj> getOrgs() {
        return orgList;
    }

    public static void setJsonData(JSONObject jsonData) {
        Trackd.jsonData = jsonData;
        try {
            // Get the json data
            Log.d(TAG, "jsonData is " + jsonData);
            JSONArray jsonArrayEvents = jsonData.getJSONArray(JSON_EVENTS);
            JSONArray jsonArrayOrgs = jsonData.getJSONArray(JSON_ORG);

            // Add the data into an array list.
            for(int i = 0; i < jsonArrayEvents.length() -1; i++){
                eventList.add(new EventObj(jsonArrayEvents.getJSONObject(i), i));
            }

            for(int j = 0; j < jsonArrayOrgs.length() -1; j++){
                orgList.add(new OrganizationObj(jsonArrayOrgs.getJSONObject(j), j));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
