package com.example.ant.beesmarttv;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.ant.beesmarttv.common.Common;
import com.example.ant.beesmarttv.common.Content;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ant on 12/8/15.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    EditText password_tx, username_ux;
    RelativeLayout loginButton;
    String username, password;

    @Override
    public void onCreate(Bundle savedInstance) {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_login);

        // set UI

        password_tx = (EditText)findViewById(R.id.password_in);
        username_ux = (EditText)findViewById(R.id.username_in);
        loginButton = (RelativeLayout)findViewById(R.id.loginButton);

        /////////////////////////////////////////////////

        loginButton.setOnClickListener(this);

        Common.savedChannelList = new ArrayList<>();

//        if (loadPreferences()) {
//            new STLoginTask().execute();
//        } else {
//            new GetAccount().execute();
//        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginButton)  {

            username = username_ux.getText().toString();
            password = password_tx.getText().toString();
            new LoginTask().execute();
        }
    }

    private class LoginTask extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        String jsonData;

        @Override
        protected void onPreExecute()   {

            super.onPreExecute();
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage("Logging in now...");
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
        }
    }
}