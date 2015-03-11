package edu.washington.chau93.trackd;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Gabby on 3/10/2015.
 */
public class CustomOrgAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = "CustomOrgAdapter";
    private Activity activity;
    private ArrayList<OrganizationObj> data;
    private static LayoutInflater inflater = null;
    public Resources res;
    private OrganizationObj tempOrgObj;

    // Constructor
    public CustomOrgAdapter(Activity a, ArrayList<OrganizationObj> data, Resources res){
        this.activity = a;
        this.data = data;
        this.res = res;

        tempOrgObj = null;

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
        public TextView title, detail, time, id;
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
            holder.detail = (TextView) customView.findViewById(R.id.item_detail);
            holder.time = (TextView) customView.findViewById(R.id.item_extra);
            holder.id = (TextView) customView.findViewById(R.id.item_id);

            // Set holder with Layout Inflater
            customView.setTag(holder);
        } else {
            holder = (ViewHolder) customView.getTag();
        }
        if(data.size() <= 0){
            holder.title.setText("There are no events.");
        } else {
            // Get topics from Array List
            tempOrgObj = (OrganizationObj) data.get(position);

            // Set background color to list item
            customView.setBackgroundColor(setBackgroundColor(tempOrgObj.getIndex()));

            // Set values into widgets
            holder.title.setText(tempOrgObj.getName());
            holder.detail.setText(
                    tempOrgObj.getShortDescr()
            );
            holder.id.setText(tempOrgObj.getId());
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
}
