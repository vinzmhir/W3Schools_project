package com.mhir.vinz.w3schools.Helper;

import android.support.v4.app.FragmentManager;

import com.mhir.vinz.w3schools.Interface.NavigationManager;
import com.mhir.vinz.w3schools.MainActivity;

public class FragmentNavigationManager implements NavigationManager {
    private static FragmentNavigationManager mInstance;

    private FragmentManager mFragmentManager;
    private MainActivity mainActivity;

    public static FragmentNavigationManager getmInstance(MainActivity mainActivity) {
        if(mInstance == null)
            mInstance = new FragmentNavigationManager();
            mInstance.configure(mainActivity);
            return mInstance;
    }

    private void configure(MainActivity mainActivity) {
        mainActivity = mainActivity;
        mFragmentManager = mainActivity.getSupportFragmentManager();
    }

    @Override
    public void showFragment(String title) {

    }
}
