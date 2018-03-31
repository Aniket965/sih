package com.example.android.electracksih;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class history_device extends Fragment {
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerView2;
    RecyclerView.Adapter mAdapter;
    RecyclerView.Adapter mAdapter2;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.LayoutManager mLayoutManager2;
    public  history_device(){};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_history_device, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.device_recycler_view_frag);
        // in content do not change the layout size of the RecyclerView
        //  mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(this.getActivity());
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
}

