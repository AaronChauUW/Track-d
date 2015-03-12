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
import android.widget.ImageView;
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
 * Use the {@link Organization#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Organization extends Fragment {
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
     * @return A new instance of fragment Organization.
     */
    // TODO: Rename and change types and number of parameters
    public static Organization newInstance() {
        Organization fragment = new Organization();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Organization() {
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_organization, container, false);

        OrganizationObj o =(OrganizationObj) getArguments().getSerializable("org");
        System.out.println(o.getId());
        ImageView image = (ImageView) v.findViewById(R.id.image);
        //TODO: change image
        TextView n = (TextView) v.findViewById(R.id.orgName);
        n.setText(o.getName());
        TextView email = (TextView) v.findViewById(R.id.orgEmail);
        email.setText(o.getEmail());
        TextView web = (TextView)  v.findViewById(R.id.orgWeb);
        web.setText(o.getWebsite());
        TextView longDescr = (TextView)  v.findViewById(R.id.orgDescr);
        longDescr.setText(o.getLongDescr());
        ListView list = (ListView) v.findViewById(R.id.upcomingEvents);
        ArrayList<EventObj> upcoming = Trackd.upComingEvents(o.getName().toString());
        list.setAdapter(
                new CustomEventAdapter(
                        getActivity(),
                        upcoming,
                        v.getResources()
                )
        );
        list.setOnItemClickListener(clickListener(v.getContext()));
        return v;
    }

    private AdapterView.OnItemClickListener clickListener(final Context context){
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListView item = (ListView)
                // TextView t = (TextView) (lv.getItemAtPosition(position));
                // TextView tv = (TextView) view.findViewById(R.item_id);
                // String id = tv.getText().toString();
                TextView tv = (TextView) view.findViewById(R.id.item_id);
                String eId = tv.getText().toString();
                EventObj e = Trackd.findEventById(eId);
                // Do stuff with the event object
                String msg = e.getName() ;
                //+ "\n" + o.getShortDescr()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("eo",e);
                //set Fragmentclass Arguments
                Event eFragment = new Event();
                eFragment.setArguments(bundle);
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //  ExampleFragment fragment = new ExampleFragment();
                fragmentTransaction.replace(R.id.container, eFragment);
                fragmentTransaction.commit();

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
