package com.example.ant.beesmarttv.model;

import android.app.Fragment;
import android.os.Bundle;

import com.example.ant.beesmarttv.MainActivity;

/**
 * Created by ant on 9/23/15.
 */
public class BaseFragment extends Fragment {

    public static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceStatus)    {

        super.onCreate(savedInstanceStatus);
        mActivity = (MainActivity)getActivity();
    }
}
