package com.example.android.electracksih;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shruti on 26-03-2018.
 */

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {
    private String[] mDatasetHeader = {"Temperature","Humidity"};
    private String[] mDatasetValue = {"21","41"};
    private String[] mDatasetDesc = {"Cool","Humid"};
    private String[] mDatasetImage = {"",""};
            // Provide a reference to the views for each data item

    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView value;
        public TextView desc;
        public ImageView desc_image;
        public View layout;
        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.header);
            value = (TextView) v.findViewById(R.id.value);
            desc = (TextView) v.findViewById(R.id.description);
            desc_image = (ImageView) v.findViewById(R.id.desc_image);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public MyAdapter(String[] myDataset) {
//        mDataset = myDataset;
//    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.content_humid_temp_card, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        URL url;

        holder.txtHeader.setText(mDatasetHeader[position]);
        holder.value.setText(mDatasetValue[position]);
        holder.desc.setText(mDatasetDesc[position]);

        try {
            url =new URL(mDatasetImage[position]);
            Picasso.get()
                    .load(mDatasetImage[position])
                    .placeholder(R.drawable.ic_launcher_background) // optional
                    .error(R.drawable.progress)         // optional
                    .into(holder.desc_image);


        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetHeader.length;
    }
}