package edu.gatech.jjmae.u_dirty_rat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RatSightingsList extends AppCompatActivity {

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
                Intent myIntent = new Intent(view.getContext(), CreateNewRatSighting.class);
                startActivityForResult(myIntent, 0);
            }
        });

        View recyclerView = findViewById(R.id.dataitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    /**
     * sets up the recycler view
     * @param recyclerView returns the recyclerview that's set up
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SampleItemRecyclerViewAdapter(SampleModel.INSTANCE.getItems()));
    }

    /**
     * Class for the Recycler view adapter that displays rat data
     */
    public class SampleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SampleItemRecyclerViewAdapter.ViewHolder> {

        private final List<RatSightingDataItem> mValues;

        public SampleItemRecyclerViewAdapter(List<RatSightingDataItem> items) {
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
            holder.mItem = mValues.get(position);
            String date = mValues.get(position).get_Date().toString();
            // the date string sometimes shows up as GMT which adds extra characters so taking that
            // into account here
            try {
                holder.mDateView.setText(date.substring(0, 10) + " " + date.substring(30, 34));
            } catch (IndexOutOfBoundsException e) {
                holder.mDateView.setText(date.substring(0, 10) + " " + date.substring(24, 28));
            }

            holder.mBoroughView.setText(mValues.get(position).get_Borough());

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, RatSighting_ViewDetail.class);
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
