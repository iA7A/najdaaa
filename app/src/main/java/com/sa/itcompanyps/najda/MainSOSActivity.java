package com.sa.itcompanyps.najda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.sa.itcompanyps.najda.R;

public class MainSOSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sos);




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


                Toast.makeText(MainSOSActivity.this, "Stay Safe and Confirm the Message!", Toast.LENGTH_SHORT).show();

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


                Toast.makeText(MainSOSActivity.this, "Stay Safe and Confirm the Message! Your emergnecy conctacts will be notified!", Toast.LENGTH_SHORT).show();

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




    }
}
