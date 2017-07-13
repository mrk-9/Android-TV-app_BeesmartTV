package com.example.ant.beesmarttv.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ant on 10/5/15.
 */
public class ChannelModel {
   public String channel_name, channel_id, cat_id;

    public static ChannelModel InstanceWithData(JSONObject channelData)  {

        ChannelModel channel = new ChannelModel();

        try {
            channel.channel_name = channelData.getString("title");
            channel.channel_id = channelData.getString("channel_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return channel;
    }
}