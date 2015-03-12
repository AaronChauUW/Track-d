package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import edu.washington.chau93.trackd.EventObj;
import edu.washington.chau93.trackd.OnFragmentInteractionListener;
import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Explore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Explore extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    // TODO: Rename and change types of parameters
    private static ArrayList<String> explored;
    private static ArrayList<EventObj> events;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Explore.
     */
    // TODO: Rename and change types and number of parameters
    public static Explore newInstance() {
        Explore fragment = new Explore();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        explored = new ArrayList<String>();
        return fragment;
    }

    public Explore() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       if(getArguments().getStringArrayList("explored") != null){
           explored = getArguments().getStringArrayList("explored");
       } else {
           explored = new ArrayList<>();
       }

        events = Trackd.getEvents();
        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        if (explored.size() < events.size() ){
            Random r = new Random();
            int num = r.nextInt(events.size());
            while(explored.contains(events.get(num).getId())){
                num = r.nextInt(events.size()) ;
            }
            final EventObj o = events.get(num);
            String id = o.getId();
            explored.add(id);

            ImageView img = (ImageView) v.findViewById(R.id.image);
            img.setImageResource(getResources().getIdentifier(o.getPhoto(), "drawable", v.getContext().getPackageName()));
            TextView eventName = (TextView) v.findViewById(R.id.event_name);
            eventName.setText(o.getName());

            Log.i("Event", "orgName != null : " + o.getName());
            TextView details = (TextView) v.findViewById(R.id.eventDescr);
            details.setText(o.getDetails());
            String dateTime = Trackd.convertDate(o.getStartDate(), o.getEndDate()) + " / " +  Trackd.convertTime(o.getStartTime(), o.getEndTime());
            TextView dateTime1 = (TextView) v.findViewById(R.id.dateTime);
            dateTime1.setText(dateTime);
            TextView loc = (TextView) v.findViewById(R.id.meetLocation);
            loc.setText(o.getWhere());
            TextView hosts = (TextView) v.findViewById(R.id.hosts);
            hosts.setText("Hosted by: " + o.getHost());
            TextView descr = (TextView) v.findViewById(R.id.eventDescr);
            descr.setText(o.getShortDescr());

            Button n = (Button) v.findViewById(R.id.next);
            n.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("explored", explored);
                    //set Fragmentclass Arguments
                    Explore eFragment = new Explore();
                    eFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    //  ExampleFragment fragment = new ExampleFragment();
                    fragmentTransaction.replace(R.id.container, eFragment);
                    fragmentTransaction.commit();
                }

            });
            Button addEvent = (Button) v.findViewById(R.id.addToCalendar);
            addEvent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent calIntent = new Intent(Intent.ACTION_INSERT);
                    calIntent.setType("vnd.android.cursor.item/event");
                    calIntent.putExtra(CalendarContract.Events.TITLE, o.getName());
                    calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, o.getWhere());
                    calIntent.putExtra(CalendarContract.Events.DESCRIPTION, o.getShortDescr());

                    Calendar calDate1 = new GregorianCalendar();
                    Calendar calDate2 = new GregorianCalendar();
                    Log.i("Event", "Starttime: " + o.getStartTime());
                    try {
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                        Date startDate = df.parse(o.getStartDate() + ":" + o.getStartTime());
                        Date endDate = df.parse(o.getStartDate() + ":" + o.getEndTime());
                        calDate1.setTime(startDate);
                        calDate2.setTime(endDate);
                        Log.i("Event", "date: " + startDate.toString());
                    } catch(Exception e) {

                    }

                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate1.getTimeInMillis());
                    calIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate2.getTimeInMillis());

                    startActivity(calIntent);
                }
            });
            return v;
        } else {
            Button n = (Button) v.findViewById(R.id.next);
            Button c = (Button) v.findViewById(R.id.addToCalendar);
            ImageView img = (ImageView) v.findViewById(R.id.image);
            TextView eventName = (TextView) v.findViewById(R.id.event_name);
            TextView details = (TextView) v.findViewById(R.id.eventDescr);
            TextView dateTime1 = (TextView) v.findViewById(R.id.dateTime);
            TextView loc = (TextView) v.findViewById(R.id.meetLocation);
            TextView hosts = (TextView) v.findViewById(R.id.hosts);
            TextView descr = (TextView) v.findViewById(R.id.eventDescr);
            n.setVisibility(v.GONE);
            c.setVisibility(v.GONE);
            img.setVisibility(v.GONE);
            eventName.setText(R.string.explore);
            details.setVisibility(v.GONE);
            dateTime1.setVisibility(v.GONE);
            loc.setVisibility(v.GONE);
            hosts.setVisibility(v.GONE);
            descr.setVisibility(v.GONE);
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



}
