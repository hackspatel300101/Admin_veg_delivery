package com.example.admin_veg_delivery;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationHelper {
    public static void DisplayNotification(Context context,String title,String body) {
        // Create notification use the notification builder class
        // set the notification title and as well as icon
        Intent notificationIntent = new Intent(context, OderActivity.class);

        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = {500,500,500,500,500,500,500,500,500};
        NotificationCompat.Builder mybulider = new NotificationCompat.Builder(context, Dashboad.channel_id)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.InboxStyle())
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH);


        NotificationManagerCompat notiMgr = NotificationManagerCompat.from(context);
        // two parameter notification id it use later delete or update notification
        //
        notiMgr.notify(1, mybulider.build());

    }

}
