package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import edu.washington.chau93.trackd.CustomEventAdapter;
import edu.washington.chau93.trackd.EventObj;
import edu.washington.chau93.trackd.OnFragmentInteractionListener;
import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Event#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Event extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Event.
     */
    // TODO: Rename and change types and number of parameters
    public static Event newInstance() {
        Event fragment = new Event();
        Bundle args = new Bundle();

//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Event() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        final EventObj eo = (EventObj) args.getSerializable("eo");

        TextView eventName = (TextView) rootView.findViewById(R.id.event_name);
        eventName.setText(eo.getName());

        TextView details = (TextView) rootView.findViewById(R.id.eventDescr);
        details.setText(eo.getDetails());

        String dateTime = Trackd.convertDate(eo.getStartDate(), eo.getEndDate()) + " / " +  Trackd.convertTime(eo.getStartTime(), eo.getEndTime());
        TextView dateTime1 = (TextView) rootView.findViewById(R.id.dateTime);
        dateTime1.setText(dateTime);

        TextView loc = (TextView) rootView.findViewById(R.id.meetLocation);
        loc.setText(eo.getWhere());

        TextView hosts = (TextView) rootView.findViewById(R.id.hosts);
        hosts.setText("Hosted by: " + eo.getHost());

        TextView descr = (TextView) rootView.findViewById(R.id.eventDescr);
        descr.setText(eo.getDetails());

        ImageView img = (ImageView) rootView.findViewById(R.id.image);
        Resources res = getResources();
        try {
        int resID = res.getIdentifier(eo.getPhoto(), "drawable",
                rootView.getContext().getPackageName());
        Log.i("Event", "resID: " + resID + " photoName: " + eo.getPhoto());
        img.setImageResource(resID);
        } catch(Exception e) {

        }

        Button addEvent = (Button) rootView.findViewById(R.id.addToCalendar);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calIntent = new Intent(Intent.ACTION_INSERT);
                calIntent.setType("vnd.android.cursor.item/event");
                calIntent.putExtra(CalendarContract.Events.TITLE, eo.getName());
                calIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, eo.getWhere());
                calIntent.putExtra(CalendarContract.Events.DESCRIPTION, eo.getShortDescr());

                Calendar calDate1 = new GregorianCalendar();
                Calendar calDate2 = new GregorianCalendar();
                Log.i("Event", "Starttime: " + eo.getStartTime());
                try {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
                    Date startDate = df.parse(eo.getStartDate() + ":" + eo.getStartTime());
                    Date endDate = df.parse(eo.getStartDate() + ":" + eo.getEndTime());
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
        return rootView;
        //*********get all details and apply them to fragement_event********

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
