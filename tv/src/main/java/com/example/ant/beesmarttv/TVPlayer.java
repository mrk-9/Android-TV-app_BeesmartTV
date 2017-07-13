package com.example.ant.beesmarttv;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ant.beesmarttv.common.Common;
import com.example.ant.beesmarttv.model.BaseFragment;
import com.example.ant.beesmarttv.model.Channel;

import java.io.IOException;


/**
 * Created by ant on 9/23/15.
 */

public class TVPlayer extends BaseFragment implements SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {

    private SurfaceView mVideoView;
    private SurfaceHolder videoHolder;
    private MediaPlayer mediaPlayer;
    private ProgressBar load;
    private RelativeLayout loadingView;
    private TextView playedChannelTitle;
    private Channel currentChannel;
    private TextView errorText;
    private Thread thread;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)   {

        View view = inflater.inflate(R.layout.fragment_tvplayer, container, false);
        mVideoView = (SurfaceView)view.findViewById(R.id.videoView);
        mVideoView.setClickable(false);
        videoHolder = mVideoView.getHolder();
        videoHolder.addCallback(this);
        load = (ProgressBar)view.findViewById(R.id.progressBar);
        playedChannelTitle = (TextView)view.findViewById(R.id.playedChannelName);
        loadingView = (RelativeLayout)view.findViewById(R.id.loadingView);
        errorText = (TextView)view.findViewById(R.id.error_txt);

        mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setEnvironment();

        return view;
    }

    public void setEnvironment()  {


        currentChannel = Common.selectedCannel;
        playedChannelTitle.setText(currentChannel.channel_name);

        loading();
    }

    public void loading()   {

        load.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.VISIBLE);
    }

    public void loadComplete()  {

        load.setVisibility(View.GONE);
        loadingView.setVisibility(View.GONE);
        mediaPlayer.start();

    }

    public void error()   {

        AlertDialog alertDialog = new AlertDialog.Builder(mActivity).create();
        alertDialog.setTitle("Error");
        alertDialog.setMessage("Unable to play this channel.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                thread.interrupt();
                thread = null;
                mActivity.pushFragment(new MainFragment());
            }
        });

        alertDialog.show();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(videoHolder);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setScreenOnWhilePlaying(true);
            playVideo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playVideo() {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    mediaPlayer.setDataSource(currentChannel.channel_link);
                    mediaPlayer.prepare();
                } catch (IllegalArgumentException e)    {
                    error();
                    e.printStackTrace();
                } catch (IllegalStateException e)   {
                    e.printStackTrace();
                    error();
                } catch (IOException e) {
                    error();
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        loadComplete();
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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    thread.interrupt();
                    thread = null;
                    mActivity.pushFragment(new MainFragment());
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        error();
        return false;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }
}