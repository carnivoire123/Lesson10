package ru.gb.lesson10;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CreateNameBottomFragment.CreateNameController {

    private static final String CHANNEL_ID = "BULK CHANNEL ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.bottom_sheet).setOnClickListener(view -> showBottomSheet());

        // Notification Channel - начиная с Android O
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            createNotificationChannels();
        }

        findViewById(R.id.show_notification).setOnClickListener(view -> showNotification());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannels() {
        String channelName = "Bulk";
        String channelDescription = "Bulk channel description";
        int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel =  new NotificationChannel(CHANNEL_ID, channelName, channelImportance);
        channel.setDescription(channelDescription);
        NotificationManagerCompat.from(this).createNotificationChannel(channel);
    }

    public static final int PENDING_REQUEST_ID = 416;

    private void showNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);

        // TODO PendingIntent ?
        Intent mainActivityIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                PENDING_REQUEST_ID,
                mainActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );



        builder
                .setContentTitle("Hello")
                .setSmallIcon(android.R.drawable.star_on)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentText("How about a coffee tonight?");

        NotificationManagerCompat.from(this).notify(444, builder.build());
    }

    private void showBottomSheet() {
        new CreateNameBottomFragment().show(getSupportFragmentManager(), null);
    }

    @Override
    public void createName(String name) {
        Toast.makeText(this, "Name is: " + name, Toast.LENGTH_SHORT).show();
    }
}