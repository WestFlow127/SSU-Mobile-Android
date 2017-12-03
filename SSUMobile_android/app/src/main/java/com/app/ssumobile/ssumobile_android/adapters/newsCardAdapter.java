package com.app.ssumobile.ssumobile_android.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.ssumobile.ssumobile_android.R;
import com.app.ssumobile.ssumobile_android.activity.newsSingleStoryActivity;
import com.app.ssumobile.ssumobile_android.models.newsStoryModel;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;

/**
 * Created by ben on 11/12/15.
 */
public class newsCardAdapter  extends RecyclerView.Adapter<newsCardAdapter.ViewHolder> implements View.OnClickListener{
    private ArrayList<newsStoryModel> mDataset;

    @Override
    public newsCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        final View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.newscardview, parent, false);
        v.setOnClickListener(this);

        // set the view's size, margins, paddings and layout parameters here
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.Title.setText(mDataset.get(position).title);

        // check for image
        if (!mDataset.get(position).image_url.equals("")){
            // load image

            ImageView imageView = holder.storyImage;
            Ion.with(imageView)
                    .placeholder(R.drawable.ssu_paw)
                    .error(R.drawable.ssu_paw)
                    .load(mDataset.get(position).image_url);
        }


        // save which event so it can be used later
        holder.Title.setTag(mDataset.get(position));
        //holder.story = mDataset.get(position);
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView Title;
        public ImageView storyImage;

        public ViewHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.newstitle);
            storyImage = (ImageView) itemView.findViewById(R.id.newsstorylogo);
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View v) {

        Activity current = (Activity) v.getContext();
        Intent myIntent = new Intent(current, newsSingleStoryActivity.class);

        newsStoryModel story = (newsStoryModel) v.findViewById(R.id.newstitle).getTag();
        myIntent.putExtra("category", story.category);
        myIntent.putExtra("content", story.content);
        myIntent.putExtra("id", story.id);
        myIntent.putExtra("published", story.published);
        myIntent.putExtra("image_url", story.image_url);
        myIntent.putExtra("link", story.link);
        myIntent.putExtra("title", story.title);
        myIntent.putExtra("summary", story.summary);

        current.startActivity(myIntent);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public newsCardAdapter(ArrayList<newsStoryModel> myDataset) {
        mDataset = myDataset;
    }
}
