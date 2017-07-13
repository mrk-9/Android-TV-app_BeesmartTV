package com.example.ant.beesmarttv.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ant on 10/3/15.
 */
public class User {

    public String email, hash_pw, userid, username, fullName, homePage, facebookPage, twitterPage, aboutMe, click2Sell, accessed_by, account_type;


    public static User initUser(JSONObject userData)    {

        User user = new User();
        try {
            user.email = userData.getString("email");
            user.hash_pw = userData.getString("hash_pw");
            user.userid = userData.getString("id");
            user.username = userData.getString("username");
            user.fullName = userData.getString("name");
            user.homePage = userData.getString("homePage");
            user.facebookPage = userData.getString("facebookkPage");
            user.twitterPage = userData.getString("twitterPage");
            user.aboutMe = userData.getString("aboutMe");
            user.click2Sell = userData.getString("click2Sell");
            user.accessed_by = userData.getString("accessed_by");
            user.account_type = userData.getString("account_type");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }
}