package com.example.calculator;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.ContentValues.TAG;



public class GoogleService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 10000;
    private static LocationService instance = null;
    DatabaseReference dbref;
    childRegisterDB regdb;  //from class RegisterDB
    String email;
    public static String str_receiver = "com.example.calculator.MainActivity";
    Intent intent;


    public GoogleService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

   mTimer = new Timer();
  mTimer.schedule(new TimerTaskToGetLocation(), 5, notify_interval);
  intent = new Intent(str_receiver);
      fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @TargetApi(23)
    private void fn_getlocation() {
        Log.e("ERROR", "HELLO  DSFDSFDASFSD");
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
              Log.e("ELSE", "network enable");


                if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    // Log.e("ELSE", "permission "+PackageManager.PERMISSION_GRANTED);
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    /// here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }

                if(isNetworkEnable){
                    try {
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 50000, 0, this);
                    }catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                SharedPreferences sp = getSharedPreferences("key", Context.MODE_PRIVATE);
                email = sp.getString("ParentMail", "");

                FirebaseApp.initializeApp(this);
                DatabaseReference volunteerRef = FirebaseDatabase.getInstance().getReference("childRegisterDB");



                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.e("latitude network", location.getLatitude() + "");
                        Log.e("longitude network", location.getLongitude() + "");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        String devID = "";
                        if (isNetworkEnable) {
                            volunteerRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                                        //childRegisterDB user = childDataSnapshot.getValue(childRegisterDB.class);
                                        String emailId = childDataSnapshot.child("email").getValue(String.class);
                                        String devId = childDataSnapshot.child("childId").getValue(String.class);
                                        if (email != null && email.equals(emailId)) {
                                            Log.w(TAG, "Dev id ="+devId);
                                            DatabaseReference volunteerRef = FirebaseDatabase.getInstance().getReference("childRegisterDB").child(devId);
                                            if (volunteerRef != null ) {
                                                Log.w(TAG, "Latitude value  id ="+ latitude);
                                                Log.w(TAG, "Longitude id ="+longitude);
                                                //volunteerRef.setValue(location.getLatitude());
                                                Map<String, Object> taskMap = new HashMap<String, Object>();
                                                taskMap.put("latitude", latitude);
                                                taskMap.put("longitude", longitude);
                                                volunteerRef.updateChildren(taskMap);
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {

                                }
                            });

                            DatabaseReference childDetailReg = FirebaseDatabase.getInstance().getReference("ChildDetailsRegDB");

                            childDetailReg.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                                        String emailId = childDataSnapshot.child("uname").getValue(String.class);
                                        String devId = childDataSnapshot.child("devID").getValue(String.class);



                                        if (email != null && email.equals(emailId)) {

                                            DatabaseReference childDB = FirebaseDatabase.getInstance().getReference("ChildDetailsRegDB").child(devId);
                                            if (childDB != null) {


                                                Log.w(TAG, "Latitude value  id ="+ latitude);
                                                Log.w(TAG, "Longitude id ="+longitude);
                                                Map<String, Object> taskMap = new HashMap<String, Object>();
                                                taskMap.put("latitude", latitude);
                                                taskMap.put("longitude", longitude);
                                                childDB.updateChildren(taskMap);
                                            }

                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError error) {


                                }
                            });
                        }



                    }
                }
                else
                {
                    Log.e("latitude", "locationManager is null");
                }

            }


            if (isGPSEnable) {
                Log.e("ELSE", "gps enable");
                location = null;

                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }

                if(isGPSEnable)
                {
                    try {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();


                    }
                }
            }

        }

    }

    private class TimerTaskToGetLocation extends TimerTask{
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

   private void fn_update(Location location){

        intent.putExtra("latitude",location.getLatitude()+"");
        intent.putExtra("longitude",location.getLongitude()+"");
        sendBroadcast(intent);
    }


}