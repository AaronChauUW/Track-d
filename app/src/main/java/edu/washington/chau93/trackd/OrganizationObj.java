package edu.washington.chau93.trackd;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aaron Chau on 3/8/2015.
 */
public class OrganizationObj {
    /*
        {"name" : "Asian Coalition for Equalty",
	     "category" : "communty",
	     "foundDate" : "1969",
		 "shortDescr" : "Asian Coalition for Equality is a organization based out of the University of Washington whose mission is to engage self-identified Asian and Asian American individuals in conversations around inequality and civic engagement. Our organization was created in 1969 as a response to the discrimination of Asian individuals into the University of Washington.  Later on, Asian Coalition for Equality continued to act as an advocacy group for the Asian community in the Seattle area, taking part in the Oriental Student Sit-in, the Kingdom Protest, and preservation of the International District.  Currently we focus our efforts on the invisibility of Asian and Asian American communities such as; education gaps, stereotypes, and what it means to identify as Asian.",
		 "longDescr" : "Mission: Asian Coalition for Equality works to engage self-identified Asian and Asian American individuals, through conversations and events centering on our daily truths; we work to increase the visibility of our community
		 				Want more info about ACE check out our history here: http://depts.washington.edu/civilr/aa_ace.htm",
		 "email" : "acequal@uw.edu",
		 "website" : "https://students.washington.edu/acequal/"
		}
     */

    private String name, category, foundDate, shortDescr, longDescr, email, website, id;
    private int index;
    public OrganizationObj(JSONObject org, int index){
        this.index = index;
        try {
            name = org.getString("name");
            category = org.getString("category");
            foundDate = org.getString("foundDate");
            shortDescr = org.getString("shortDescr");
            longDescr = org.getString("longDescr");
            email = org.getString("email");
            website = org.getString("website");
            id = org.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getFoundDate() {
        return foundDate;
    }

    public String getShortDescr() {
        return shortDescr;
    }

    public String getLongDescr() {
        return longDescr;
    }

    public String getEmail() {
        return email;
    }

    public String getWebsite() {
        return website;
    }

    public String getId() { return id; }

    public int getIndex() { return index; }
}
