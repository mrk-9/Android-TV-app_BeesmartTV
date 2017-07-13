package com.example.ant.beesmarttv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ant on 10/15/15.
 */
public class SplashActivity extends Activity    {

    @Override
    public void onCreate(Bundle savedInstance)  {

        super.onCreate(savedInstance);

        setContentView(R.layout.activity_splash);

        Thread logoTimer = new Thread() {

            public void run()   {

                try {

                    int logoTimer = 0;
                    while (logoTimer < 2000)    {
                        sleep(100);
                        logoTimer = logoTimer + 100;
                    };

                    Intent intent = new Intent(SplashActivity.this, GetInfoActivity.class);
                    startActivity(intent);

                } catch (InterruptedException e)    {

                    e.printStackTrace();
                }

                finally {
                    finish();
                }
            }
        };

        logoTimer.start();
    }
}
