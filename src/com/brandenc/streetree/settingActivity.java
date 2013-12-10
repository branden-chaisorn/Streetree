package com.brandenc.streetree;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


//Main Activity. Will use fragments on activites from here on out
public class settingActivity extends Activity
    {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_list);

        // TODO: If exposing deep links into your app, handle intents here.
    }

        /**
         * Button to start the race map activity from the main activity
         */
        public void startMapActivity(View view) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            Intent raceMapIntent = new Intent(this, raceActivity.class);
            startActivity(raceMapIntent);
        }



}
