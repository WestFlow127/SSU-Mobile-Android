package com.app.ssumobile.ssumobile_android.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.models.calendarEventModel;

import java.util.ArrayList;

/**
 * Created by ben on 11/2/15.
 */
public class calendarCardAdapter extends RecyclerView.Adapter<calendarCardAdapter.ViewHolder> implements View.OnClickListener{


    private ArrayList<calendarEventModel> mDataset;


    @Override
    public void onClick(View v) {
        calendarEventModel currentEvent = mDataset.get((Integer) v.findViewById(R.id.title).getTag());


        String location = currentEvent.getLocation();
        if (location.isEmpty()){
            location = "No Location Provided";
        }
        else if(location.contains("|")){
            String[] locations = location.split("\\|");
            if (locations.length <= 2){
                location = locations[0] + (locations.length == 2 ? locations[1] : "");
            }
            else{ // multiple locations
                location = "Locations: \n";
                for (String i : locations){
                    if (locations[1] == i){
                        continue;
                    }
                    location += "   " + i + '\n';
                }
            }


        }

        String text = new StringBuilder("Start Time: " + currentEvent.getStartsOn() + "\n")
                .append("End Time: " + currentEvent.getEndsOn() + "\n")
                .append(location + '\n')
                .toString();

        final AlertDialog alertDialog = new AlertDialog.Builder(v.getContext())
                .setTitle(currentEvent.getTitle())
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked OK, so save the mSelectedItems results somewhere
                        // or return them to the component that opened the dialog
                        dialog.dismiss();
                    }
                })
                .setMessage(text)
                .create();

        alertDialog.show();

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView starts_date;
        public TextView title;

        Integer pos;


        public ViewHolder(View itemView) {
            super(itemView);
            starts_date = (TextView) itemView.findViewById(R.id.StartsOn);
            title = (TextView) itemView.findViewById(R.id.Title);
        }
    }



    // Provide a suitable constructor (depends on the kind of dataset)
    public calendarCardAdapter(ArrayList<calendarEventModel> myDataset) {
        mDataset = myDataset;
    }



    // Create new views (invoked by the layout manager)
    @Override
    public calendarCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        final View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        v.setOnClickListener(this);
        // set the view's size, margins, paddings and layout parameters here
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }



    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.title.setText(mDataset.get(position).getTitle());
        holder.starts_date.setText(mDataset.get(position).getStartsOn());
        //holder.pos = position;

        // save reference to event in title
        holder.title.setTag(position);

    }



    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
