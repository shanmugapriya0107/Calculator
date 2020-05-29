package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class calci  extends Activity {
    boolean mBounded;
    LocationUpdaterService mServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_calci);
        //get the current intent
        Intent intent = getIntent();
        String latitude = intent.getStringExtra("latitude");

        Log.e("latitude in calci",latitude);
      // Intent intent = new Intent(calci.this, LocationUpdaterService.class);
      //  startService(intent);
    }


}
