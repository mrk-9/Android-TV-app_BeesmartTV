package com.example.ant.beesmarttv.common;

import com.example.ant.beesmarttv.model.ChannelModel;
import com.example.ant.beesmarttv.model.User;

import org.apache.http.client.CookieStore;
import org.apache.http.protocol.HttpContext;

import java.util.ArrayList;

/**
 * Created by ant on 9/23/15.
 */
public class Common {

    public static ChannelModel selectedCannel;
    public static int selectedIndex;
    public static User savedUser;
    public static ArrayList<ChannelModel> savedChannelList;
    public static String savedTVLink;
    public static CookieStore localCookies;

    public static String username;
    public static String userID;
    public static String password_hash;

    public static ArrayList<ChannelModel> getLineupedList() {

        ArrayList<ChannelModel> channels = new ArrayList<>();

        String channel_name[]  = {
                "CBS", "ABC", "ABC 22", "NBC", "Fox NY", "Fox 45", "CW", "WSVN 7 News Miami", "Weather National", "WGN9", "RT News", "AlJezera", "Bloomberg", "CNBC", "MSNBC", "CNN", "CNN International", "Fox News",
                "AMC", "Spike", "USA", "Lifetime", "History", "Discovery Investigation", "Travel Channel", "Food Network", "History", "Science", "HGTV", "Own", "ID-Investigation Discovery", "TLC", "Bravo", "Comedy Central", "HBO Comedy",
                "Disney", "Nickelodeon", "NFL Network", "ESPN USA", "ESPN UK", "ESPN 2", "NBC Sports Network", "WWE Network", "Golf", "Euro Sport 1", "RT Documentary"
        };

        for (int i = 0; i < channel_name.length ; i ++) {
            ChannelModel channelModel = new ChannelModel();
            channelModel.channel_name = channel_name[i];
            channels.add(channelModel);
        }

        return channels;
    }

    public static ArrayList<ChannelModel> getSortedArray(ArrayList<ChannelModel> mArray, ArrayList<ChannelModel> aArray)  {

        ArrayList<ChannelModel> temp = new ArrayList<>();

        temp = filteredArray(aArray);

        for (int i = 0; i < mArray.size(); i ++)    {
            ChannelModel channelModel = mArray.get(i);
            temp.add(channelModel);
        }

        temp = resortArray(temp);

        return temp;
    }

    private static ArrayList<ChannelModel> filteredArray(ArrayList<ChannelModel> mArray)    {

        ArrayList<ChannelModel> temp = new ArrayList<>();

        for (int i = 0; i < mArray.size(); i ++)    {
            ChannelModel channelModel = mArray.get(i);

            if ((channelModel.channel_id != null && channelModel.channel_id.length() > 0) || channelModel.channel_name.contains("WSVN")) {

                temp.add(channelModel);
            }
        }

        return temp;
    }

    private static ArrayList<ChannelModel> resortArray(ArrayList<ChannelModel> mArray)  {

        ArrayList<ChannelModel> temp = new ArrayList<>();

        for (int i = 0; i < mArray.size(); i ++)    {

            ChannelModel model = mArray.get(i);

            if (model.channel_name.contains("HBO Comedy"))  {
                model.channel_name = "HBO Comedy";
            }

            if (model.channel_name.contains("History Channel HD"))  {
                model.channel_name = "History Channel";
            }

            if (model.channel_name.contains("Science (HD)"))    {
                model.channel_name = "Science Channel";
            }

            if (model.channel_name.contains("(HD)"))    {
                model.channel_name = model.channel_name.substring(0, model.channel_name.length()-5);
            }

            if (model.channel_name.contains("WWE Network WWE Network Live"))    {
                model.channel_name = "WWE Network";
            }

            if (model.channel_name.contains("Fox Network")) {
                model.channel_name = "FOX Network";
            }

            if (model.channel_name.contains("[HD]"))    {
                model.channel_name = model.channel_name.substring(0, model.channel_name.length()-5);
            }

            if (model.channel_name.contains("2015")) {

                String lastSt = model.channel_name.substring(model.channel_name.length() - 2, model.channel_name.length() - 1);
                String numSt = model.channel_name.substring(model.channel_name.length() - 3, model.channel_name.length() - 2);

                if (lastSt.equals(")") && Integer.parseInt(numSt) >= 0 && Integer.parseInt(numSt) <= 9) {
                    model.channel_name = model.channel_name.substring(0, model.channel_name.length()-7);
                }
            }

            temp.add(model);
        }

        return temp;
    }
}
