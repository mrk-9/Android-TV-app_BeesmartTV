package com.example.ant.beesmarttv.Adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ant.beesmarttv.R;
import com.example.ant.beesmarttv.model.Channel;
import com.example.ant.beesmarttv.model.Program;

import java.util.ArrayList;

/**
 * Created by ant on 9/23/15.
 */
public class ProgramListAdapter extends RecyclerView.Adapter<ProgramListAdapter.CustomProgramViewHolder> {

    Activity mActivity;
    ArrayList<Program> dataList;

    public ProgramListAdapter(Activity activity, ArrayList<Program> list)   {

        mActivity = activity;
        dataList = list;
    }

    @Override
    public CustomProgramViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_program_list, parent, false);
        CustomProgramViewHolder viewHolder = new CustomProgramViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomProgramViewHolder holder, int position) {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {

        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class CustomProgramViewHolder extends RecyclerView.ViewHolder   {

        public CustomProgramViewHolder(View view)  {
            super(view);
        }
    }
}
