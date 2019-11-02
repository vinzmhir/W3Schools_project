package com.mhir.vinz.w3schools;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

public class home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar tb = (Toolbar) findViewById(R.id.toolbar_home);
    }

}
