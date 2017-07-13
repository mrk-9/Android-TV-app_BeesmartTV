package com.example.ant.beesmarttv.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ant.beesmarttv.R;
import com.example.ant.beesmarttv.model.Channel;

import java.util.ArrayList;

/**
 * Created by ant on 9/23/15.
 */
public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.CustomViewHolder> {

    Activity mActivity;
    ArrayList<Channel> dataList;

    public ChannelListAdapter(Activity activity, ArrayList<Channel> list)   {

        mActivity = activity;
        dataList = list;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_channel_list_cell, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Channel channel = dataList.get(position);
        holder.nameView.setText(channel.channel_name);
        holder.iconView.setImageResource(channel.channel_icon);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder   {
        public ImageView iconView;
        public TextView nameView;

        public CustomViewHolder(View view)  {
            super(view);
            iconView = (ImageView)view.findViewById(R.id.channel_icon);
            nameView = (TextView)view.findViewById(R.id.channel_name);
        }
    }
}