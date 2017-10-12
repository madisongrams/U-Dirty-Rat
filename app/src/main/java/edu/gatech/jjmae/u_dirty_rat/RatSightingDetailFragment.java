package edu.gatech.jjmae.u_dirty_rat;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RatSightingDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private RatSightingDataItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RatSightingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            int item_id = getArguments().getInt(ARG_ITEM_ID);
            Log.d("MYAPP", "Start details for: " + item_id);
            mItem = SampleModel.INSTANCE.findItemById(item_id);
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.get_Borough());
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.rat_report_detail, container, false);
        Log.d("MYAPP", "Getting ready to set data");
        if (mItem != null) {
            Log.d("MYAPP", "Getting ready to set id");
//            ((TextView) rootView.findViewById(R.id.title_id)).setText("" + mItem.get_ID());
            Log.d("MYAPP", "Getting ready to set name");
            ((TextView) rootView.findViewById(R.id.date)).setText(mItem.get_Date().toString());
            ((TextView) rootView.findViewById(R.id.location)).setText(mItem.get_Location());
            ((TextView) rootView.findViewById(R.id.zip)).setText("" + mItem.get_ZipCode());
            ((TextView) rootView.findViewById(R.id.address)).setText(mItem.get_Address());
            ((TextView) rootView.findViewById(R.id.city)).setText(mItem.get_City());
            String latlong = mItem.get_Latitude() + ", " + mItem.get_Longitude();
            ((TextView) rootView.findViewById(R.id.latlong)).setText(latlong);
//            ((TextView) rootView.findViewById(R.id.borough)).setText(mItem.get_Borough());
//            ((TextView) rootView.findViewById(R.id.latitude)).setText("" + mItem.get_Latitude());
//            ((TextView) rootView.findViewById(R.id.longitude)).setText("" + mItem.get_Longitude());
        }

        return rootView;
    }
}
