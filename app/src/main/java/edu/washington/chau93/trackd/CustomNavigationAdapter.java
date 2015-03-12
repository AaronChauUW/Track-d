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

import java.util.ArrayList;

/**
 * Created by iguest on 3/11/15.
 */
public class CustomNavigationAdapter extends BaseAdapter implements View.OnClickListener {
    private Activity activity;
    private ArrayList<String> data;
    private LayoutInflater inflater;
    private String tempString;
    private Resources res;

    public CustomNavigationAdapter(Activity a, ArrayList<String> data, Resources res) {
        this.activity = a;
        this.data = data;
        this.res = res;
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
        public TextView navItem;
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
            customView = inflater.inflate(R.layout.custom_navigation_layout, null);

            // Use View Holder to hold the widgets
            holder = new ViewHolder();
            holder.navItem = (TextView) customView.findViewById(R.id.custom_navigation_item);


            // Set holder with Layout Inflater
            customView.setTag(holder);
        } else {
            holder = (ViewHolder) customView.getTag();
        }
        if(data.size() <= 0){
            holder.navItem.setText("There is nothing to navigate.");
        } else {
            // Get topics from Array List
            tempString = data.get(position);

            // Set background color to list item
            holder.navItem.setTextColor(getTextColor(tempString));

            // Set values into widgets
            holder.navItem.setText(tempString);
        }

        return customView;
    }

    @Override
    public void onClick(View v) {

    }

    private int getTextColor(String tempString){
        switch (tempString){
            case "Explore!":
                return res.getColor(R.color.yellow);
            case "Events":
                return res.getColor(R.color.purple);
            case "Organizations":
                return res.getColor(R.color.orange);
            case "About Us":
                return res.getColor(R.color.plum);
        }
        return res.getColor(R.color.purple);
    }
}
