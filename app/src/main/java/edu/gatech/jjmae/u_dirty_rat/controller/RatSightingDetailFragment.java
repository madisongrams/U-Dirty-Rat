package edu.gatech.jjmae.u_dirty_rat.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

import static com.facebook.share.internal.DeviceShareDialogFragment.TAG;

/**
 * fragment to display detail about rat data
 */
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

    private ShareButton shareButton;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RatSightingDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
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
            ((TextView) rootView.findViewById(R.id.id)).setText("" + mItem.get_ID());
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

            shareButton = (ShareButton) rootView.findViewById(R.id.share_btn);
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://developers.facebook.com")) //TODO: change this url
                    .setQuote("Dirty Rat Spotted in " + mItem.get_City() + "!!")
                    .setImageUrl(Uri.parse("https://thumb1.shutterstock.com/display_pic_with_logo/1256479/187492997/stock-vector-fat-cartoon-rat-187492997.jpg"))
                    .setShareHashtag(new ShareHashtag.Builder()
                            .setHashtag("#UDirtyRat")
                            .build())
                    .build();
            shareButton.setShareContent(content);
            shareButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Log.d("RatDetailFragment", "onClick: share button pressed");
//                    postRat();
                }
            });

        }

        return rootView;
    }

}
