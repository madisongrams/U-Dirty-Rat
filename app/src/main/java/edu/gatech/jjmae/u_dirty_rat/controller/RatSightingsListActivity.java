package edu.gatech.jjmae.u_dirty_rat.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.firebase.database.*;
import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;

public class RatSightingsListActivity extends AppCompatActivity {
    private Handler handler;
    private SampleItemRecyclerViewAdapter ratAdapter;
    private static final String TAG = "RatSightingsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_sighting_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), NewRatSightingActivity.class);
                startActivityForResult(myIntent, 0);
            }
        });

        View recyclerView = findViewById(R.id.dataitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                SampleModel.INSTANCE.addItem((RatSightingDataItem) msg.obj, false);
                ratAdapter.notifyDataSetChanged();
            }
        };
        readFromDatabase();
    }

    private void readFromDatabase() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ValueEventListener listener = (new ValueEventListener() {
            private Handler handler;

            ValueEventListener init(Handler ahandler) {
                handler = ahandler;
                return this;
            }

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleRat: dataSnapshot.getChildren()) {
                    long id = 0;
                    int zip = 0;
                    try {
                        id = (Long) singleRat.child("Unique_Key").getValue();
                    } catch (Exception e) {
                    }
                    try {
                        zip = (Integer) singleRat.child("Incident_Zip").getValue();
                    } catch (Exception e) {
                    }

                    double latitude =  0.0;
                    double longitude = 0.0;

                    try {
                        latitude = (Double) singleRat.child("Latitude").getValue();
                        longitude = (Double) singleRat.child("Longitude").getValue();
                    } catch (ClassCastException e) {}

                    String incidentAddress = "";
                    try {
                        incidentAddress = (String) singleRat.child("Incident_Address").getValue();
                    } catch (ClassCastException e) {}

                    Date entryDate = new Date(1969, 12, 31);
                    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                    try {
                        entryDate = df.parse((String) singleRat.child("Created_Date").getValue());
                    } catch (Exception e) {}
                    Message msg = handler.obtainMessage();
                    msg.obj = new RatSightingDataItem(id, entryDate,
                            (String) singleRat.child("Location_Type").getValue(), zip,
                            incidentAddress, (String) singleRat.child("City").getValue(),
                            (String) singleRat.child("Borough").getValue(),
                            latitude, longitude);
                    handler.sendMessage(msg);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }).init(handler);
        ref.limitToFirst(100).addValueEventListener(listener);
    }

    /**
     * sets up the recycler view
     * @param recyclerView returns the recyclerview that's set up
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        ratAdapter = new SampleItemRecyclerViewAdapter(SampleModel.INSTANCE);
        recyclerView.setAdapter(ratAdapter);
    }

    /**
     * Class for the Recycler view adapter that displays rat data
     */
    public class SampleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SampleItemRecyclerViewAdapter.ViewHolder> {

        private final SampleModel mValues;

        public SampleItemRecyclerViewAdapter(SampleModel items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rat_item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            List<RatSightingDataItem> rats = this.mValues.getItems();
            holder.mItem = rats.get(position);
            String date = rats.get(position).get_Date().toString();
            // the date string sometimes shows up as GMT which adds extra characters so taking that
            // into account here
            holder.mDateView.setText(date);

            holder.mBoroughView.setText(rats.get(position).get_Borough());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RatSightingViewDetailActivity.class);
                        Log.d("MYAPP", "Switch to detailed view for item: " + holder.mItem.get_ID());
                        intent.putExtra(RatSightingDetailFragment.ARG_ITEM_ID, holder.mItem.get_ID());

                        context.startActivity(intent);
                    }
            });
        }

        @Override
        /**
         * @return number of rat data items
         */
        public int getItemCount() {
            return mValues.size();
        }

        /**
         * View Holder class to display the recycler view
         */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDateView;
        public final TextView mBoroughView;
        public RatSightingDataItem mItem;

            /**
             * constructor that takes in a view for the view holder
             * @param view view used for viewholder
             */

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDateView = (TextView) view.findViewById(R.id.date);
            mBoroughView = (TextView) view.findViewById(R.id.borough);
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mDateView.getText() + "'" +  mBoroughView.getText()
                    + " '";
        }
    }
    }


}
