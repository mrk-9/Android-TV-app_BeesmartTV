package com.example.ant.beesmarttv;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

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
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ant on 9/23/15.
 */

public class TVPlayer extends BaseFragment  {

    private VideoView mVideoView;
    private ProgressBar load;
    private RelativeLayout loadingView;
    private TextView playedChannelTitle;
    private ChannelModel currentChannel;
    private TextView errorText;
    private TextView channel_input;
    private boolean isPlayed;
    private RelativeLayout status_bar;

    private Timer logoTimer;
    private TimerTask timerTask;

    private Handler handler = new Handler();

    private int timerValue = 0;
    private String pageNumberSt = "";
    private boolean isError = false;
    private boolean isFinished_view = false;
    private TextView channeNumber_txt;

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
            if(!isError && !isFinished_view)
                error();
            return true;
        }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {

        View view = inflater.inflate(R.layout.fragment_tvplayer, container, false);

        playedChannelTitle = (TextView)view.findViewById(R.id.playedChannelName);
        mVideoView = (VideoView)view.findViewById(R.id.videoView);
        load = (ProgressBar)view.findViewById(R.id.progressBar);
        loadingView = (RelativeLayout)view.findViewById(R.id.loadingView);
        errorText = (TextView)view.findViewById(R.id.error_txt_thumb);
        channeNumber_txt = (TextView)view.findViewById(R.id.channel_number);
        status_bar = (RelativeLayout)view.findViewById(R.id.status_bar);
        channel_input = (TextView)view.findViewById(R.id.channel_number_right);

        // add handler to video view

        mVideoView.setOnCompletionListener(myVideoViewCompletionListener);
        mVideoView.setOnPreparedListener(MyVideoViewPreparedListener);
        mVideoView.setOnErrorListener(myVideoViewErrorListener);

        ////////////////////////////

        setEnvironment();

        playVideo(Common.savedTVLink);

        isPlayed = false;

        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        return view;
    }

    private void playVideo(String url)  {
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.requestFocus();
        mVideoView.start();
        MediaController localMediaController = new MediaController(mActivity, false);
        localMediaController.setAnchorView(mVideoView);
        mVideoView.setMediaController(localMediaController);
    }

    public void setEnvironment()  {

        currentChannel = Common.selectedCannel;
        playedChannelTitle.setText(currentChannel.channel_name);
        int channelNumber = Common.selectedIndex + 1;
        channeNumber_txt.setText("" + channelNumber);

        loading();
    }

    public void loading()   {

        ChannelModel channelModel = Common.selectedCannel;
        playedChannelTitle.setText(channelModel.channel_name);
        int channelNumber = Common.selectedIndex + 1;
        channeNumber_txt.setText(channelNumber+ "");
        channel_input.setText(channelNumber + "");
        load.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
        status_bar.setVisibility(View.VISIBLE);
    }

    public void loadComplete()  {

        isPlayed = true;
        load.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        status_bar.setVisibility(View.GONE);
        channel_input.setText("");
    }

    public void error()   {
        isError = true;
        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Unable to play this channel.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                back();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onPause()   {

        if (!isError && !isFinished_view)   {
            stop();
        }
        super.onPause();
    }

    public void stop()  {

        mVideoView.setMediaController(new MediaController(mActivity, false));
        mVideoView.requestFocus();

        Intent intent = new Intent(mActivity, GetInfoActivity.class);
        isFinished_view = true;
        startActivity(intent);
        mActivity.finish();
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

                if (event.getAction() == KeyEvent.ACTION_UP) {

                    if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
                        int number = keyCode - 7;
                        if (pageNumberSt == null || pageNumberSt.length() == 0)
                            pageNumberSt = number + "";
                        else
                            pageNumberSt = pageNumberSt + number;

                        channel_input.setText(pageNumberSt);

                        timerValue = 0;

                        if (logoTimer != null)
                            stopTimerTask();
                        startTimer();
                        return false;
                    }

                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK:
                            if (isPlayed)
                                back();
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            if (isPlayed)
                                nextPlay();
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            if (isPlayed)
                                prevPlay();
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            if (isPlayed)
                                back();
                            break;
                        case KeyEvent.KEYCODE_HOME:
                            break;
                        default:
                            break;
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void back()  {

        mVideoView.setMediaController(new MediaController(mActivity, false));
        mVideoView.requestFocus();

        isFinished_view = true;
        mActivity.pushFragment(new MainFragment());
    }

    public void nextPlay()  {

        int selectedIndex = Common.selectedIndex;

        ArrayList<ChannelModel> dataList = Common.savedChannelList;
        if (selectedIndex == dataList.size()-1) {
            selectedIndex = 0;
        } else
        {
            selectedIndex = selectedIndex + 1;
        }

        Common.selectedIndex = selectedIndex;

        if (selectedIndex < Common.savedChannelList.size()) {
            Common.selectedCannel = dataList.get(selectedIndex);
            playVideoByUpDown(Common.selectedCannel.channel_id);
        }
    }

    public void prevPlay()  {
        int selectedIndex = Common.selectedIndex;

        ArrayList<ChannelModel> dataList = Common.savedChannelList;
        if (selectedIndex == 0) {
            selectedIndex = dataList.size()-1;
        } else
        {
            selectedIndex = selectedIndex - 1;
        }

        Common.selectedIndex = selectedIndex;

        if (selectedIndex < Common.savedChannelList.size()) {
            Common.selectedCannel = dataList.get(selectedIndex);
            playVideoByUpDown(Common.selectedCannel.channel_id);
        }
    }

    public void playVideoByUpDown(String id)     {

        if (Common.selectedCannel.channel_id.contains("http"))  {
            Common.savedTVLink = Common.selectedCannel.channel_id;
            initMediaPlayer();
        } else {
            loading();
            new GetChannelLinkTask().execute();
        }
    }

    @Override
    public void onDestroy()  {

        mVideoView.setMediaController(new MediaController(mActivity, false));
        mVideoView.requestFocus();

        super.onDestroy();
    }

    private void initMediaPlayer()  {
        mVideoView.setVideoPath(Common.savedTVLink);
        MediaController localMeidaController = new MediaController(mActivity, false);
        mVideoView.setMediaController(localMeidaController);
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

                    Common.selectedIndex = Integer.parseInt(pageNumberSt) - 1;;
                    Common.selectedCannel = Common.savedChannelList.get(Common.selectedIndex);

                    pageNumberSt = "";
                    stopTimerTask();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            int pageNumber = Common.selectedIndex + 1;
                            channeNumber_txt.setText(pageNumber + "");
                            playVideoByUpDown(Common.selectedCannel.channel_id);
                        }
                    });
                }
            }
        };
    }

    class GetChannelLinkTask extends AsyncTask<Void, Void, Void> {

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
                return null;
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

            initMediaPlayer();
        }
    }
}