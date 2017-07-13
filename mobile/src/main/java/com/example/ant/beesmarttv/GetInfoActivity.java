package com.example.ant.beesmarttv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ant.beesmarttv.common.Common;
import com.example.ant.beesmarttv.common.Content;
import com.example.ant.beesmarttv.model.Channel;
import com.example.ant.beesmarttv.model.ChannelModel;
import com.example.ant.beesmarttv.model.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ant on 10/15/15.
 */
public class GetInfoActivity extends Activity    {

    private CookieStore localCookies=new BasicCookieStore() ;
    String username ,password_hash, password, userID;

    @Override
    public void onCreate(Bundle savedInstance)  {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_getinfo);

        Common.savedChannelList = new ArrayList<>();

        if (loadPreferences())  {
                new STLoginTask().execute();
        } else {
                new GetAccount().execute();
        }
    }

    public void menuActivity()  {

        Intent intent = new Intent(GetInfoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public class STLoginTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(GetInfoActivity.this);
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpClient httpClient = new DefaultHttpClient();
            String url = Content.api_url + Content.login_api;

            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> postParam = new ArrayList<>();

            postParam.add(new BasicNameValuePair("username", username));
            postParam.add(new BasicNameValuePair("password", password));
            postParam.add(new BasicNameValuePair("accessed_by", "web"));

            localCookies = new BasicCookieStore();
            try {

                httpPost.setEntity(new UrlEncodedFormEntity(postParam));
                httpPost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36");
                httpPost.addHeader("Referer", "https://offsidestreams.com/site/shop/checkout/");
                HttpResponse response = httpClient.execute(httpPost);
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
            } catch (IOException e) {
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

            new GetChannelList().execute();
        }
    }

    public class GetChannelList extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(GetInfoActivity.this);
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jObj;
            HttpClient httpClient = new DefaultHttpClient();
            String url = Content.server_url + Content.get_channelList;
            HttpGet httpGet = new HttpGet(url);

            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                jsonData = EntityUtils.toString(httpEntity);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e)   {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)   {

            super.onPostExecute(result);
            dialog.dismiss();

            if (jsonData == null) {
                return;
            }

            try {

                ArrayList<ChannelModel> channelDataList = new ArrayList<>();

                JSONObject responseObject = new JSONObject(jsonData);
                JSONArray channels = responseObject.getJSONArray("channels");

                for (int i = 0; i < channels.length(); i ++)    {
                    JSONObject channelData = channels.getJSONObject(i);

                    ChannelModel sortedModel = ChannelModel.InstanceWithData(channelData);

                    if (sortedModel.channel_name.equals("Fox Network"))
                        sortedModel.channel_name = "FOX Network";
                    channelDataList.add(sortedModel);
                }

                Common.savedChannelList = channelDataList;
                Common.selectedCannel = channelDataList.get(0);
                Common.selectedIndex = 0;

                new GetChannelLinkTask().execute();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("getChannels", "yes");
            }
        }
    }

    public class GetAccount extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(GetInfoActivity.this);
            dialog.setMessage("Loading");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject jObj;
            HttpClient httpClient = new DefaultHttpClient();
            String url = Content.server_url + Content.get_user_info;
            HttpGet httpGet = new HttpGet(url);

            try {
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                jsonData = EntityUtils.toString(httpEntity);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e)   {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)   {

            super.onPostExecute(result);
            dialog.dismiss();

            if (jsonData == null) {
                return;
            }

            try {
                JSONObject responseObject = new JSONObject(jsonData);

                username = responseObject.getString("username");
                password_hash = responseObject.getString("password_hash");
                password = responseObject.getString("passsword");
                userID = responseObject.getString("userid");

                Common.username = username;
                Common.password_hash = password_hash;
                Common.userID = userID;

                savedPreferences(username, userID, password_hash, password);

                new STLoginTask().execute();

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("getChannels", "yes");
            }
        }
    }

    private void savedPreferences(String username_load, String userID_load, String password_hash_load, String password_load) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", username_load);
        editor.putString("password", password_load);
        editor.putString("password_hash", password_hash_load);
        editor.putString("userid", userID_load);

        editor.commit();
    }

    private boolean loadPreferences()  {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        username = sharedPreferences.getString("username", "");
        password = sharedPreferences.getString("password", "");
        password_hash = sharedPreferences.getString("password_hash", "");
        userID = sharedPreferences.getString("userid", "");

        if (username == null || username.length() == 0) {
            return false;
        } else  {
            Common.username = username;
            Common.password_hash = password_hash;
            Common.userID = userID;

            return true;
        }
    }

    class GetChannelLinkTask extends AsyncTask<Void, Void, Void>    {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(GetInfoActivity.this);
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

                localCookies.addCookie(new BasicClientCookie("usernameSeeon", username));
                localCookies.addCookie(new BasicClientCookie("passwordSeeon", password_hash));
                localCookies.addCookie(new BasicClientCookie("cometchatuserid", userID));

                HttpContext localContext=new BasicHttpContext();
                localContext.setAttribute(ClientContext.COOKIE_STORE, localCookies);

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

            menuActivity();

        }
    }
}
