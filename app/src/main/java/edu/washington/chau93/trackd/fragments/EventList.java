package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import edu.washington.chau93.trackd.EventObj;
import edu.washington.chau93.trackd.OnFragmentInteractionListener;
import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventList extends Fragment {
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
     * @return A new instance of fragment EventList.
     */
    // TODO: Rename and change types and number of parameters
    public static EventList newInstance() {
        EventList fragment = new EventList();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate our event list view
        View rootView = inflater.inflate(R.layout.fragment_event_list, container, false);
        // Get our ListView from the layout
        ListView lv = (ListView) rootView.findViewById(R.id.eventList);

        // TODO: Make this more complex. Need to put more data and make a custom list item.
        // Get the Arraylist of event objects
        ArrayList<EventObj> events = null;
        // Get the events
        events = Trackd.getEvents();
        // Going to add the event names into this array list
        ArrayList<String> stringEvents = new ArrayList<>();
        for(EventObj eo : events){
            stringEvents.add(eo.getName());
        }

        // Set the list view up with an adapter with our list of event names
        lv.setAdapter(
                new ArrayAdapter<String>(
                        rootView.getContext(),
                        android.R.layout.simple_list_item_1,
                        stringEvents
                )
        );

        lv.setOnItemClickListener(clickListener(rootView.getContext()));
        return rootView;
    }

    private AdapterView.OnItemClickListener clickListener(final Context context){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                EventObj eo = Trackd.findEventByName(tv.getText().toString());
                // Do stuff with the event object
                String msg = eo.getName() + "\n" + eo.getDetails();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        };
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
