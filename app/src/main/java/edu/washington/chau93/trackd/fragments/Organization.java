package edu.washington.chau93.trackd.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import edu.washington.chau93.trackd.OnFragmentInteractionListener;
import edu.washington.chau93.trackd.OrganizationObj;
import edu.washington.chau93.trackd.R;

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
        //TODO: add events to list view, make method to get all upcoming events for organization

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
