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

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.ant.beesmarttv.Adapter.ChannelDataAdapter;
import com.example.ant.beesmarttv.common.Common;
import com.example.ant.beesmarttv.common.Content;
import com.example.ant.beesmarttv.model.BaseFragment;
import com.example.ant.beesmarttv.model.ChannelModel;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends BaseFragment  {
    private static final String TAG = "MainFragment";
    private TextView date_title, time_title, selected_channel_name;
    private ImageView selected_channel_icon;
    private RecyclerView channelList;
    private ArrayList<ChannelModel> channelDataList;
    private int selectedItem;
    private TextView logo_title;
    private ChannelDataAdapter channelListAdapter;
    private String pageNumberSt;

    private VideoView mVideoView;
    private ProgressBar load;
    private RelativeLayout loadingView;
    private TextView playedChannelTitle;
    private ChannelModel currentChannel;
    private TextView errorText;
    private RelativeLayout thumbView;
    private ImageView thumImage;

    private int timerValue = 0;

    private Timer logoTimer;
    private TimerTask timerTask;
    private boolean isError = false;
    private boolean isFinished_view = false;

    Handler handler = new Handler();

    MediaPlayer.OnCompletionListener myVideoViewCompletionListener
            = new MediaPlayer.OnCompletionListener(){

        @Override
        public void onCompletion(MediaPlayer arg0) {
        }};

    MediaPlayer.OnPreparedListener MyVideoViewPreparedListener
            = new MediaPlayer.OnPreparedListener(){

        @Override
        public void onPrepared(MediaPlayer arg0) {
            loadComplete();
        }};

    MediaPlayer.OnErrorListener myVideoViewErrorListener
            = new MediaPlayer.OnErrorListener(){

        @Override
        public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
            if (!isError && !isFinished_view)
                error();
            return true;
        }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        date_title = (TextView)view.findViewById(R.id.date_title);
        time_title = (TextView)view.findViewById(R.id.time_title);
        selected_channel_name = (TextView)view.findViewById(R.id.selected_channel_name);
        selected_channel_icon = (ImageView)view.findViewById(R.id.selected_channel_icon);
        channelList = (RecyclerView)view.findViewById(R.id.channel_listview);
        logo_title = (TextView)view.findViewById(R.id.logo_title);
        mVideoView = (VideoView)view.findViewById(R.id.videoView_thumb);
        load = (ProgressBar)view.findViewById(R.id.progressBar_thumb);
        loadingView = (RelativeLayout)view.findViewById(R.id.loadingView_thumb);
        errorText = (TextView)view.findViewById(R.id.error_txt_thumb);
        playedChannelTitle = (TextView)view.findViewById(R.id.playedChannelName_thumb);
        thumbView = (RelativeLayout)view.findViewById(R.id.thumbnail_view);
        thumImage = (ImageView)view.findViewById(R.id.logoImage);

        // Temporary Data

        selected_channel_icon.setVisibility(View.INVISIBLE);

        ////////////

        Context context = mActivity.getApplicationContext();

        Typeface custom_font = Typeface.createFromAsset(context.getAssets(), "fonts/motschcc.ttf");
        logo_title.setTypeface(custom_font);

        channelDataList = new ArrayList<>();

        channelDataList = Common.savedChannelList;
        selectedItem = Common.selectedIndex;
        sortSavedChannelList();
        setTimeEnvironment();

        startLoading(Common.savedTVLink);

        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return view;
    }

    public void startTimer()    {

        logoTimer = new Timer();
        intializeTimerTask();

        logoTimer.schedule(timerTask, 0, 100);
    }

    public void stopTimerTask()   {

        if (logoTimer != null)  {

            logoTimer.cancel();
            logoTimer = null;
            timerValue = 0;
        }
    }

    public void intializeTimerTask()    {

        timerTask = new TimerTask() {
            @Override
            public void run() {

                Log.d("Timer", "start");

                if (timerValue < 2000)  {
                    timerValue = timerValue + 100;
                }
                else {

                    selectedItem = Integer.parseInt(pageNumberSt) - 1;
                    if(selectedItem >= channelDataList.size())
                        return;
                    Common.selectedCannel = channelDataList.get(selectedItem);
                    Common.selectedIndex = selectedItem;
                    pageNumberSt = "";
                    stopTimerTask();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshChannelList();
                        }
                    });
                }
            }
        };
    }

    @Override
    public void onResume()  {

        super.onResume();

        if (getView() == null)  {
            return;
        }

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (channelDataList.size() <= 0)
                    return false;

                if (event.getAction() == KeyEvent.ACTION_DOWN) {


                    if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                        int number = keyCode - 7;
                        if (pageNumberSt == null || pageNumberSt.length() == 0)
                            pageNumberSt = number + "";
                        else
                            pageNumberSt = pageNumberSt + number;
                        timerValue = 0;
                        if (logoTimer != null)
                            stopTimerTask();
                        startTimer();
                        return false;
                    }

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            onSelectedDown();
                            Log.d("keypressed", "Down");
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            onSelectedUp();
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            Log.d("keypressed", "selected");
                            onSelectChannel();
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            onSelectedMenu();
                        default:
                            break;
                    }
                }
                return false;
            }
        });
    }

    public void onSelectedUp()  {

        if (selectedItem >= 1) {
                selectedItem = selectedItem - 1;
        } else {

            return;
        }

        refreshChannelList();
    }

    public void onSelectedDown()    {

        if (selectedItem < channelDataList.size() - 1)    {
            selectedItem = selectedItem + 1;
        }

        refreshChannelList();
    }

    public void onSelectedMenu()    {
        thumbView.setBackgroundColor(0);
    }

    public void refreshChannelList()    {
        setStatusForChannel();
        channelListAdapter.setSelectedIndex(selectedItem);
        channelList.setAdapter(null);
        channelList.setAdapter(channelListAdapter);

        LinearLayoutManager manager = (LinearLayoutManager)channelList.getLayoutManager();

        manager.scrollToPositionWithOffset(selectedItem, 0);
    }

    public void onSelectChannel()   {

        Common.selectedCannel = channelDataList.get(selectedItem);
        Common.selectedIndex = selectedItem;

        mVideoView.setMediaController(new MediaController(mActivity, false));

        if (Common.selectedCannel.channel_id.contains("http"))  {
            Common.savedTVLink = Common.selectedCannel.channel_id;
            hideVideoView();
            isFinished_view = true;
            mActivity.pushFragment(new TVPlayer());
        } else {
            new GetChannelLinkTask().execute();
        }

    }

    public void setTimeEnvironment()    {

        date_title.setText(convetDateToString("EEEE, dd MMMM yyyy", 0));

        time_title.setText(convetDateToString("KK:mm a", 0));
    }

    public String convetDateToString(String format, int diffirence) {

        Date date = new Date();
        date.setHours(date.getHours() + diffirence);
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public void setStatusForChannel() {

        ChannelModel channel = channelDataList.get(selectedItem);
//        selected_channel_icon.setImageResource(channel.channel_icon);
        selected_channel_name.setText(channel.channel_name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mVideoView.setMediaController(new MediaController(mActivity, false));
        mVideoView.requestFocus();

        thumImage.setVisibility(View.VISIBLE);

        super.onDestroy();
    }

    public void sortSavedChannelList()  {

        channelListAdapter= new ChannelDataAdapter(mActivity, channelDataList);

        if (Common.selectedCannel != null)  {
            channelListAdapter.setSelectedIndex(selectedItem);
        }

        if (channelList.getAdapter() != null)   {
            channelList.setAdapter(null);
        }

        channelListAdapter.setSelectedIndex(selectedItem);

        channelList.setAdapter(channelListAdapter);
        channelList.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext()));

        if (channelList.getAdapter() != null)   {
            LinearLayoutManager manager = (LinearLayoutManager)channelList.getLayoutManager();
            manager.scrollToPositionWithOffset(selectedItem, 0);
        }

        setStatusForChannel();
    }

    class GetChannelLinkTask extends AsyncTask<Void, Void, Void>    {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(mActivity);
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpClient httpClient = new DefaultHttpClient();
            String url = Content.api_url + Content.get_channel_link_api;
            String requestParams = "n="+Common.selectedCannel.channel_id;
            url = url + requestParams;

            HttpGet httpGet = new HttpGet(url);

            try {
                HttpContext localContext=new BasicHttpContext();
                localContext.setAttribute(ClientContext.COOKIE_STORE, Common.localCookies);

                HttpResponse response = httpClient.execute(httpGet, localContext);
                HttpEntity httpEntity = response.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream));
                String result = "";
                String line = "";

                while (null != (line = reader.readLine()))
                {
                    result += line;
                }

                jsonData = result;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (Exception e)  {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)   {

            dialog.dismiss();

            if (jsonData == null || jsonData.length() == 0) {
                return;
            }

            Log.d("link", jsonData);
            jsonData = jsonData.substring(1, jsonData.length()-1);
            Common.savedTVLink = jsonData;

            hideVideoView();
            isFinished_view = true;
            mActivity.pushFragment(new TVPlayer());
        }
    }

    private void hideVideoView()    {
        mVideoView.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        load.setVisibility(View.GONE);
    }

    private void showVideoView()    {
        mVideoView.setVisibility(View.VISIBLE);
        load.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        errorText.setVisibility(View.VISIBLE);
    }

    private void startLoading(String url)  {

        // add handler

        mVideoView.setOnCompletionListener(myVideoViewCompletionListener);
        mVideoView.setOnPreparedListener(MyVideoViewPreparedListener);
        mVideoView.setOnErrorListener(myVideoViewErrorListener);

        ////////////////////////////////

        // seting video view

        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.requestFocus();
        mVideoView.start();
        MediaController localMediaController = new MediaController(mActivity, false);
        localMediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(localMediaController);

        ///////////////////////////////

        // set loading view

        showVideoView();
        setEnvironment();

        ///////////////////////////////
    }

    public void setEnvironment()  {

        currentChannel = Common.selectedCannel;
        playedChannelTitle.setText(currentChannel.channel_name);
    }

    public void loadComplete()  {

        load.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
    }

    public void error()   {

        isError = true;
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Unable to play this channel.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                hideVideoView();
                thumImage.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }
}