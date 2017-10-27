package edu.gatech.jjmae.u_dirty_rat.controller;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.gatech.jjmae.u_dirty_rat.R;
import edu.gatech.jjmae.u_dirty_rat.model.AbstractUser;
import edu.gatech.jjmae.u_dirty_rat.model.Admin;
import edu.gatech.jjmae.u_dirty_rat.model.RatSightingDataItem;
import edu.gatech.jjmae.u_dirty_rat.model.SampleModel;
import edu.gatech.jjmae.u_dirty_rat.model.User;
import edu.gatech.jjmae.u_dirty_rat.model.UserData;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View recyclerView = findViewById(R.id.dataitem_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void displayBanAlertDialog(final Admin admin, final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to ban " + user.getUsername() +"?")
                .setTitle("Ban User");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                admin.banUser(user);
                File file = new File(getApplicationContext().getFilesDir(), "userData.txt");
                UserData.saveText(file);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void displayUnBanAlertDialog(final Admin admin, final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to unban " + user.getUsername() +"?")
                .setTitle("Unban User");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                admin.unBanUser(user);
                File file = new File(getApplicationContext().getFilesDir(), "userData.txt");
                UserData.saveText(file);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    /**
     * sets up the recycler view
     * @param recyclerView returns the recyclerview that's set up
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        List<String> items = new ArrayList<String>(UserData.getUsers().keySet());
        Collections.sort(items);
        Collections.reverse(items);
        recyclerView.setAdapter(new UserListActivity.SampleItemRecyclerViewAdapter(items));
    }

    /**
     * Class for the Recycler view adapter that displays rat data
     */
    public class SampleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<UserListActivity.SampleItemRecyclerViewAdapter.ViewHolder> {

        private final List<String> mValues;

        public SampleItemRecyclerViewAdapter(List<String> items) {
            mValues = items;
        }

        @Override
        public UserListActivity.SampleItemRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_list_content, parent, false);
            return new UserListActivity.SampleItemRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final UserListActivity.SampleItemRecyclerViewAdapter.ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            String date = holder.mItem;
            // the date string sometimes shows up as GMT which adds extra characters so taking that
            // into account here

            holder.mUsernameView.setText(date);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("UserList", "onClick: User pressed: " + holder.mItem);
                    User user = UserData.getUser(holder.mItem);
                    if (user != null && user.getIsBanned()) {
                        //ask to unban
                        displayUnBanAlertDialog((Admin) UserData.getCurrentUser(), user);

                    } else if (user != null) {
                        // ask to ban
                        displayBanAlertDialog((Admin) UserData.getCurrentUser(), user);
                    }

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
            public final TextView mUsernameView;
            public String mItem;

            /**
             * constructor that takes in a view for the view holder
             * @param view view used for viewholder
             */

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mUsernameView= (TextView) view.findViewById(R.id.username);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mUsernameView.getText() + "'";
            }
        }
    }
}
