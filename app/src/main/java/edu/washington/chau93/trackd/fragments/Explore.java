package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.app.AlarmManager;
import android.net.Uri;
import android.os.Bundle;
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

import java.util.ArrayList;
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
        if(savedInstanceState != null) {
            explored = savedInstanceState.getStringArrayList("explored");
        } else {
            explored = new ArrayList<>();
        }
        events = Trackd.getEvents();
        Random r = new Random();
        int num = r.nextInt(events.size());
        while(explored.contains(events.get(num).getId())){
            num = r.nextInt(events.size()) ;
        }
        EventObj o = events.get(num);
        String id = o.getId();
        explored.add(id);
        View v = inflater.inflate(R.layout.fragment_explore, container, false);
        ImageView img = (ImageView) v.findViewById(R.id.image);
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
        descr.setText(o.getDetails());

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
