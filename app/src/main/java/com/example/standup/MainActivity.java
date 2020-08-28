package com.example.standup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    ToggleButton alarmToggle = findViewById(R.id.alarmToggle);

}alarmToggle.setOnCheckedChangeListener(
        new CompoundButton.OnCheckedChangeListener() {
@Override
public void onCheckedChanged(CompoundButton compoundButton,
        boolean isChecked) {
        }
        });
        String toastMessage;
        if(isChecked){
        //Set the toast message for the "on" case.
        toastMessage = "Stand Up Alarm On!";
        } else {
        //Set the toast message for the "off" case.
        toastMessage = "Stand Up Alarm Off!";
        }

        //Show a toast to say the alarm is turned on or off.
        Toast.makeText(MainActivity.this, toastMessage,Toast.LENGTH_SHORT)
        .show();

private NotificationManager mNotificationManager;

        mNotificationManager = (NotificationManager)
        getSystemService(NOTIFICATION_SERVICE);

private static final int NOTIFICATION_ID = 0;
private static final String PRIMARY_CHANNEL_ID =
        "primary_notification_channel";

/**
 * Creates a Notification channel, for OREO and higher.
 */
public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
        android.os.Build.VERSION_CODES.O) {

        // Create the NotificationChannel with all the parameters.
        NotificationChannel notificationChannel = new NotificationChannel
        (PRIMARY_CHANNEL_ID,
        "Stand up notification",
        NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription
        ("Notifies every 15 minutes to stand up and walk");
        mNotificationManager.createNotificationChannel(notificationChannel);
        }
        }private void deliverNotification(Context context) {}
        Intent contentIntent = new Intent(context, MainActivity.class);
        PendingIntent contentPendingIntent = PendingIntent.getActivity
        (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_stand_up)
        .setContentTitle("Stand Up Alert")
        .setContentText("You should stand up and walk around now!")
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManager.notify(NOTIFICATION_ID, builder.build());

        if(isChecked){
        deliverNotification(MainActivity.this);
        //Set the toast message for the "on" case
        toastMessage = "Stand Up Alarm On!";
        } else {
        //Cancel notification if the alarm is turned off
        mNotificationManager.cancelAll();

        //Set the toast message for the "off" case
        toastMessage = "Stand Up Alarm Off!";
        }

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);

        PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
        (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long triggerTime = SystemClock.elapsedRealtime()
        + repeatInterval;

//If the Toggle is turned on, set the repeating alarm with a 15 minute interval
        if (alarmManager != null) {
        alarmManager.setInexactRepeating
        (AlarmManager.ELAPSED_REALTIME_WAKEUP,
        triggerTime, repeatInterval, notifyPendingIntent);
        }
        if (alarmManager != null) {
        alarmManager.cancel(notifyPendingIntent);
        }
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
        PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);