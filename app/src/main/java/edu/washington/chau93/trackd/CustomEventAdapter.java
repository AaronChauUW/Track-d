package edu.washington.chau93.trackd;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aaron Chau on 3/10/2015.
 */
public class CustomEventAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = "CustomEventAdapter";
    private Activity activity;
    private ArrayList<EventObj> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    private EventObj tempEventObj;

    // Constructor
    public CustomEventAdapter(Activity a, ArrayList<EventObj> data, Resources res){
        this.activity = a;
        this.data = data;
        this.res = res;

        tempEventObj = null;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if(data.size()<=0)
            return 1;
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    public static class ViewHolder {
        public TextView title, locationDate, time, id;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View customView = convertView;
        ViewHolder holder;

        if (convertView == null){
            // Inflate our custom list item view
            customView = inflater.inflate(R.layout.custom_list_item, null);

            // Use View Holder to hold the widgets
            holder = new ViewHolder();
            holder.title = (TextView) customView.findViewById(R.id.item_Name);
            holder.locationDate = (TextView) customView.findViewById(R.id.item_detail);
            holder.time         = (TextView) customView.findViewById(R.id.item_extra);
            holder.id = (TextView) customView.findViewById(R.id.item_id);


            // Set holder with Layout Inflater
            customView.setTag(holder);
        } else {
            holder = (ViewHolder) customView.getTag();
        }
        if(data.size() <= 0){
            holder.title.setText(R.string.ev);
            holder.title.setTextColor(Color.DKGRAY);
            holder.time.setVisibility(View.GONE);
            holder.locationDate.setVisibility(View.GONE);
        } else {
            // Get topics from Array List
            tempEventObj = (EventObj) data.get(position);

            // Set background color to list item
            customView.setBackgroundColor(setBackgroundColor(tempEventObj.getIndex()));

            // Set values into widgets
            holder.title.setText(tempEventObj.getName());
            holder.locationDate.setText(
                    tempEventObj.getWhere() + " / " +
                            Trackd.convertDate(tempEventObj.getStartDate(), tempEventObj.getEndDate())
            );

            holder.time.setVisibility(View.VISIBLE);
            holder.time.setText(convertTime(tempEventObj.getStartTime(), tempEventObj.getEndTime()));
            holder.id.setText(tempEventObj.getId());
        }

        return customView;
    }

    @Override
    public void onClick(View v) {

    }

    private int setBackgroundColor(int index){
        int color = 0;
        /*
            <color name="white">#FFFFFF</color>
            <color name="purple">#5C2965</color>
            <color name="plum">#9B375E</color>
            <color name="red">#D64857</color>
            <color name="orange">#E9793D</color>
            <color name="yellow">#EBC22C</color>
         */
        switch (index % 3) {
            case 0:
                color = res.getColor(R.color.purple);
                break;
            case 1:
                color = res.getColor(R.color.plum);
                break;
            case 2:
                color = res.getColor(R.color.orange);
                break;
            default:
                color = res.getColor(R.color.red);
                break;
        }
        return color;
    }

    private String convertTime(String start, String end){
        Date startTime = null;
        Date endTime = null;
        try {
            startTime = new SimpleDateFormat("hh:mm:ss").parse(start);
            endTime = new SimpleDateFormat("hh:mm:ss").parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mma");
        return sdf.format(startTime) + "-" + sdf.format(endTime);
    }

    private String convertDate(String start, String end){
        Date startDate = null;
        Date endDate = null;

        try {
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d");
        if(sdf.format(startDate).equalsIgnoreCase(sdf.format(endDate))){
            return sdf.format(startDate);
        }
        return sdf.format(startDate) + "-" + sdf.format(endDate);
    }
}
