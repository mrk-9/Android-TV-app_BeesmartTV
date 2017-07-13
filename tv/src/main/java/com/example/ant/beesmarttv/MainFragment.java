/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.ant.beesmarttv;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ant.beesmarttv.Adapter.ChannelListAdapter;
import com.example.ant.beesmarttv.Adapter.ProgramListAdapter;
import com.example.ant.beesmarttv.Adapter.RecyclerItemClickListener;
import com.example.ant.beesmarttv.common.Common;
import com.example.ant.beesmarttv.model.BaseFragment;
import com.example.ant.beesmarttv.model.Channel;
import com.example.ant.beesmarttv.model.Program;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";
    private TextView date_title, time_title, selected_channel_name, selected_program_guide, date_status;
    private ImageView selected_channel_icon;
    private RecyclerView channelList, programList;
    private ArrayList<Program> program_data_list;
    private int selectedItem;
    private TextView logo_title;
    private TextView prevTime, currTime, nextTime;

    int hours[] = {
            1, 2, 2, 1, 1, 2, 3, 1, 3, 2, 1, 1, 4, 2, 2, 2
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        date_title = (TextView)view.findViewById(R.id.date_title);
        time_title = (TextView)view.findViewById(R.id.time_title);
        selected_channel_name = (TextView)view.findViewById(R.id.selected_channel_name);
        selected_program_guide = (TextView)view.findViewById(R.id.channel_program_title);
        date_status = (TextView)view.findViewById(R.id.date_status);
        selected_channel_icon = (ImageView)view.findViewById(R.id.selected_channel_icon);
        channelList = (RecyclerView)view.findViewById(R.id.channel_listview);
        programList = (RecyclerView)view.findViewById(R.id.program_listview);
        logo_title = (TextView)view.findViewById(R.id.logo_title);
        prevTime = (TextView)view.findViewById(R.id.txt_prev_time);
        currTime = (TextView)view.findViewById(R.id.txt_curr_time);
        nextTime = (TextView)view.findViewById(R.id.txt_next_time);

        Context context = mActivity.getApplicationContext();

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/motschcc.ttf");
        logo_title.setTypeface(custom_font);

        channelList.addOnItemTouchListener(new RecyclerItemClickListener(mActivity.getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.d("Event", "channel selected");
                selectedItem = position;
                setStatusForChannel();
            }
        }));

        programList.addOnItemTouchListener(new RecyclerItemClickListener(mActivity.getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                Log.d("Event", "program selected");
                Common.selectedCannel = MovieList.setupMovies().get(selectedItem);
                Common.selectedIndex = selectedItem;
                mActivity.pushFragment(new TVPlayer());
            }
        }));

        if (Common.selectedCannel != null)  {
            selectedItem = Common.selectedIndex;
        } else {
            selectedItem = 0;
        }

        setTimeEnvironment();
        setListView();
        setStatusForChannel();

        return view;
    }

    public void setTimeEnvironment()    {

        date_title.setText(convetDateToString("EEEE, dd MMMM yyyy", 0));

        date_status.setText(convetDateToString("EEEE, dd MMMM", 0));

        time_title.setText(convetDateToString("KK:mm a", 0));

        prevTime.setText(convetDateToString("KK:00 a", 0));

        currTime.setText(convetDateToString("KK:00 a", 1));

        nextTime.setText(convetDateToString("KK:00 a", 2));
    }

    public String convetDateToString(String format, int diffirence) {

        Date date = new Date();
        date.setHours(date.getHours() + diffirence);

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        return dateFormat.format(date);
    }

    public void setStatusForChannel() {

        ArrayList<Channel> data_List = MovieList.setupMovies();
        ChannelListAdapter adapter = new ChannelListAdapter(mActivity, data_List);
        Channel channel = data_List.get(selectedItem);

        selected_channel_icon.setImageResource(channel.channel_icon);
        selected_channel_name.setText(channel.channel_name);
    }

    public void setListView()   {

        channelList.setHasFixedSize(false);
        channelList.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext()));
        ArrayList<Channel> data_List = MovieList.setupMovies();
        ChannelListAdapter adapter = new ChannelListAdapter(mActivity, data_List);
        channelList.setAdapter(adapter);

        GridLayoutManager manager = new GridLayoutManager(mActivity.getApplicationContext(), 6);

        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {

                return hours[position];
            }
        });

        programList.setLayoutManager(manager);
        getProgramList();
        ProgramListAdapter adapter_program = new ProgramListAdapter(mActivity, program_data_list);

        programList.setAdapter(adapter_program);
    }

    public void getProgramList()    {
        program_data_list = new ArrayList<Program>();

        for (int i = 0; i < 16; i ++)   {
            Program program = new Program();
            program_data_list.add(program);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}