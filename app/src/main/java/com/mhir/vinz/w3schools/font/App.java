package com.mhir.vinz.w3schools.font;

import android.app.Application;
import com.mhir.vinz.w3schools.R;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/DroidSans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
