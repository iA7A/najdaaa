package com.sa.itcompanyps.najda;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class ImmSOSActivity extends AppCompatActivity {


    private TextView textView;
    private LocationManager locationManager;
    private LocationListener listener;
    private TextView t;
    String phoneNo = "911";
    String smss = "Help Me! I need IMMEDIATE HELP HERE";
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    final Handler handler = new Handler();
    private Button b;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imm_sos);

        t = (TextView) findViewById(R.id.textView);
        b = (Button) findViewById(R.id.button77);




        final Button customButton = findViewById(R.id.custombutton);
        Switch switchEnableButton = findViewById(R.id.switchenablebutton);





        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customButton.setEnabled(true);
                } else {
                    customButton.setEnabled(false);
                }
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                String uri = "http://maps.google.com/maps?saddr=" + location.getLatitude()+","+location.getLongitude();
                t.setText("\n " + location.getLatitude() + " " + location.getLongitude());



            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {


                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);


            }
        };

        configure_button();

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }



    void configure_button(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }

        }





        final Button customButton = findViewById(R.id.custombutton);
        t = (TextView) findViewById(R.id.textView);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                locationManager.requestLocationUpdates("gps", 100000, 0, listener);


                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        String message = "Help Me! I need an IMMEDIATE HELP HERE" + t.getText().toString();

                        if (ContextCompat.checkSelfPermission(ImmSOSActivity.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(ImmSOSActivity.this, new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        } else {
                            SmsManager sms = SmsManager.getDefault();


                            sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
                            Toast.makeText(ImmSOSActivity.this, "Your message has been sent! Stay Safe!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 100);


            }


            });
    }
}