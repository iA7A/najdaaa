package com.sa.itcompanyps.najda;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Msos extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private LocationManager locationManager;
    private LocationListener listener;
    private TextView t;
    final Handler handler = new Handler();
    String phoneNo = "911";
    String smss = "Help Me! I need IMMEDIATE HELP HERE";
    private final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;

    private final String SENT = "SMS_SENT";
    private final String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        t = (TextView) findViewById(R.id.textView);

        updateNavHeader1();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavHeader();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.msos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();


        } else if (id == R.id.nav_profile) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new ProfileFragment()).commit();


        } else if (id == R.id.nav_settings) {

            getSupportFragmentManager().beginTransaction().replace(R.id.container,new SettingsFragment()).commit();


        } else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Intent loginActivity = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(loginActivity);
            finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void updateNavHeader() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerview = navigationView.getHeaderView(0);
        TextView navUsername = headerview.findViewById(R.id.nav_username);
        TextView navUserMail = headerview.findViewById(R.id.nav_user_mail);
        ImageView navUserPhot = headerview.findViewById(R.id.nav_user_photo);


        navUserMail.setText(currentUser.getEmail());
        navUsername.setText(currentUser.getDisplayName());

        Glide.with(this).load(currentUser.getPhotoUrl()).into(navUserPhot);

    }


    public void updateNavHeader1() {



        final Button customButton = findViewById(R.id.custombutton);
        final Button customButton1 = findViewById(R.id.custombutton1);
        Switch switchEnableButton = findViewById(R.id.switchenablebutton);

        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /* Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:4146302845"));
                smsIntent.putExtra("sms_body", "Help Me! I need IMMEDIATE HELP HERE");
                locationManager.requestLocationUpdates("gps", 10000, 0, listener);
                startActivity(smsIntent);

*/


                Toast.makeText(Msos.this, "Stay Safe and Confirm the Message!", Toast.LENGTH_SHORT).show();

            }

        });



        customButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /* Intent smsIntent = new Intent(Intent.ACTION_SENDTO,
                        Uri.parse("sms:4146302845"));
                smsIntent.putExtra("sms_body", "Help Me! I need IMMEDIATE HELP HERE");
                locationManager.requestLocationUpdates("gps", 10000, 0, listener);
                startActivity(smsIntent);

*/


                Toast.makeText(Msos.this, "Stay Safe and Confirm the Message! Your emergnecy conctacts will be notified!", Toast.LENGTH_SHORT).show();

            }

        });




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

        switchEnableButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    customButton1.setEnabled(true);
                } else {
                    customButton1.setEnabled(false);
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

                        if (ContextCompat.checkSelfPermission(Msos.this, Manifest.permission.SEND_SMS)
                                != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(Msos.this, new String[]{Manifest.permission.SEND_SMS},
                                    MY_PERMISSIONS_REQUEST_SEND_SMS);
                        } else {
                            SmsManager sms = SmsManager.getDefault();


                            sms.sendTextMessage(phoneNo, null, message, sentPI, deliveredPI);
                            Toast.makeText(Msos.this, "Your message has been sent! Stay Safe!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }, 100);


            }


        });
    }
}
