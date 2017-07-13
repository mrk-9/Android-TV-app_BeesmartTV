 package com.example.ant.beesmarttv;

import com.example.ant.beesmarttv.model.Channel;
import java.util.ArrayList;
import java.util.List;

public final class MovieList {

    public static ArrayList<Channel> list;

    public static ArrayList<Channel> setupMovies() {
        list = new ArrayList<Channel>();
        String title[] = {
                "ABC TV",
                "CNN TV",
                "CNBC TV",
                "PBS TV"
        };

        String videoUrl[] = {
                "http://abclive.abcnews.com/i/abc_live4@136330/index_1200_av-b.m3u8",
                "http://wpc.c1a9.edgecastcdn.net/hls-live/20C1A9/cnn/ls_satlink/b_828.m3u8",
                "http://wpc.c1a9.edgecastcdn.net/hls-live/20C1A9/cnbc_eu/ls_satlink/b_,264,528,828,.m3u8",
                "http://api.new.livestream.com/accounts/13897710/events/4153577/videos/95111168.m3u8"
        };
        int bgImageUrl[] = {
                R.drawable.abc_tv_icon, R.drawable.cbs_tv_icon, R.drawable.cnbc_tv_icon, R.drawable.pbs_tv_icon
        };

        list.add(buildMovieInfo(title[0], videoUrl[0],  bgImageUrl[0]));
        list.add(buildMovieInfo(title[1], videoUrl[1],  bgImageUrl[1]));
        list.add(buildMovieInfo(title[2], videoUrl[2],  bgImageUrl[2]));
        list.add(buildMovieInfo(title[3], videoUrl[3],  bgImageUrl[3]));

        return list;
    }

    private static Channel buildMovieInfo(String channle_name, String channel_link, int channel_icon) {
        Channel channel = new Channel();

        channel.setChannel_icon(channel_icon);
        channel.setChannel_link(channel_link);
        channel.setChannel_name(channle_name);
        return channel;
    }
}
