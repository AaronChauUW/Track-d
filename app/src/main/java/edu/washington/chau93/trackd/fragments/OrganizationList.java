package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import edu.washington.chau93.trackd.CustomEventAdapter;
import edu.washington.chau93.trackd.CustomOrgAdapter;
import edu.washington.chau93.trackd.EventObj;
import edu.washington.chau93.trackd.OnFragmentInteractionListener;
import edu.washington.chau93.trackd.OrganizationObj;
import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrganizationList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrganizationList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String ARG_PARAM1 = "param1";
   // private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
  //  private String mParam1;
  //  private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Organizations.
     */
    // TODO: Rename and change types and number of parameters
    public static OrganizationList newInstance() {
        OrganizationList fragment = new OrganizationList();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OrganizationList() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          //  mParam1 = getArguments().getString(ARG_PARAM1);
          //  mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_organizations, container, false);

        // Inflate our event list view
        View rootView = inflater.inflate(R.layout.fragment_organizations, container, false);
        // Get our ListView from the layout
        ListView lv = (ListView) rootView.findViewById(R.id.orgList);

        // TODO: Make this more complex. Need to put more data and make a custom list item.
        // Get the Arraylist of event objects
        //ArrayList<OrganizationObj> orgs = null;
        // Get the events
        //orgs = Trackd.getOrgs();
        // Going to add the event names into this array list
       // ArrayList<String> stringOrgs = new ArrayList<>();
       // for(OrganizationObj o : orgs){
       //     stringOrgs.add(o.getName());
            //stringOrgs.add(o.getShortDescr());
       // }

        // Set the list view up with an adapter with our list of event names
        lv.setAdapter(
                new CustomOrgAdapter(
                        getActivity(),
                        Trackd.getOrgs(),
                        rootView.getResources()
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
                OrganizationObj o = Trackd.findOrgByName(tv.getText().toString());
                // Do stuff with the event object
                String msg = o.getName() + "\n" + o.getLongDescr();
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
