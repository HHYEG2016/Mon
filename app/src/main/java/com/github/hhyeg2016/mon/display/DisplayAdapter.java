package com.github.hhyeg2016.mon.display;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.hhyeg2016.mon.R;
import com.github.hhyeg2016.mon.data.Data;

import java.util.ArrayList;

public class DisplayAdapter extends RecyclerView.Adapter<DisplayAdapter.ViewHolder> {
    private ArrayList<Data> mDataset;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public RelativeLayout relativeLayout;
        public ViewHolder(RelativeLayout v) {
            super(v);
            relativeLayout = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DisplayAdapter(ArrayList<Data> myDataset, Context context) {
        mDataset = myDataset;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DisplayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder((RelativeLayout) v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ImageView imageView = (ImageView) holder.relativeLayout.getChildAt(0);
        TextView title = (TextView) holder.relativeLayout.getChildAt(1);
        TextView info1 = (TextView) holder.relativeLayout.getChildAt(2);
        TextView time = (TextView) holder.relativeLayout.getChildAt(3);
        TextView info2 = (TextView) holder.relativeLayout.getChildAt(4);

        imageView.setImageDrawable(context.getDrawable(R.drawable.ic_action_name));
        title.setText("Hi");
        info1.setText("Hi");
        time.setText("Hi");
        info2.setText("Hi");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}