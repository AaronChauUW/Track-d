package edu.washington.chau93.trackd;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Aaron Chau on 3/8/2015.
 */
public class EventObj implements Serializable {

    /*
        "name" : "Asians Collaborating Together Conference (ACE)",
		 "details" : "One of the biggest needs that our group has identified, is the need for leadership development within our community.  It is with excitement that we are announcing our second annual leadership focused conference called Asians Collaborating Together (ACT) Conference.  The event will focus on the current issues surrounding the Asian community and how we can develop leadership skills to overcome them. This year, our overarching themes will be Identity and Unity.",
		 "where" : "Ethnic Cultural Center",
		 "startDate" : "2015-04-04",
		 "startTime" : "10:00:00",
		 "endDate" : "2015-04-04",
		 "endTime" : "16:00:00",
		 "host" : "Asian Coalition for Equality"
     */

    private String name, details, where, startDate, startTime, endDate, endTime, host, id, photo, shortdescr;
    private int index;

    public EventObj(JSONObject event, int index){
        this.index = index;
        try {
            name = event.getString("name");
            details = event.getString("details");
            where = event.getString("where");
            startDate = event.getString("startDate");
            startTime = event.getString("startTime");
            endDate = event.getString("endDate");
            endTime = event.getString("endTime");
            host = event.getString("host");
            id = event.getString("id");
            photo = event.getString("pic");
            shortdescr = event.getString("shortDescr");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getWhere() {
        return where;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getHost() {
        return host;
    }

    public int getIndex() { return index; }

    public String getId() { return id; }

    public String getPhoto() { return photo; }

    public String getShortDescr() { return shortdescr; }
}
