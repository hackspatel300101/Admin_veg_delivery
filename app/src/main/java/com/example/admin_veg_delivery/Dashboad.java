package com.example.admin_veg_delivery;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class Dashboad extends AppCompatActivity {
    CardView price_list,add_item,oders;
    private long backPressedTime = 0;
    public  static final String channel_id = "Channel_id";
    private static final String channel_name = "channel_name";
    private static final String channel_desc = "Channel Desc";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboad);
        price_list=findViewById(R.idprice_list);
        add_item=findViewById(R.id.add_item);
        oders=findViewById(R.id.oder);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FirebaseMessaging.getInstance().subscribeToTopic("Oders");
        Uri sound= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {                   // here thrid parameter is notification channel priority
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channel_desc);
            channel.setShowBadge(true);
            channel.setVibrationPattern(pattern);
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build();
            channel.setSound(sound,audioAttributes);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {


                if (task.isSuccessful()) {
                    String token = task.getResult().getToken();
                    Log.i("Tokenajay",token);

                } else
                {

                }
            }
        });

        price_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboad.this,Item_listActivity.class);
                startActivity(intent);
                finish();

            }
        });
        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Dashboad.this,Add_itemActivity.class);
                startActivity(intent1);
                finish();


            }
        });
        oders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Dashboad.this,OderActivity.class);
                startActivity(intent2);
                finish();


            }
        });

    }
    @Override
    public void onBackPressed() {        // to prevent irritating accidental logouts
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Press Back Again To Exit",
                    Toast.LENGTH_SHORT).show();
        } else {    // this guy is serious
            // clean up
            super.onBackPressed();       // bye
        }
    }

}
