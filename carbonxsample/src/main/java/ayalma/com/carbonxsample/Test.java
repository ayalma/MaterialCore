package ayalma.com.carbonxsample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.nio.charset.Charset;

import mat.widget.Button;
import mat.widget.EditText;
import mat.widget.TextView;
import mat.widget.Toolbar;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText input = findViewById(R.id.inputField);
        Button btn = findViewById(R.id.ok);
        TextView text = findViewById(R.id.content);

        btn.setOnClickListener(v -> {
            Notification.Builder notify = new Notification.Builder(this).setContentText("hello!")
                    .setStyle(new Notification.BigTextStyle().bigText("sup!"))
                    .setContentTitle("sup").addAction(R.drawable.ic_launcher_foreground,"hello", PendingIntent.getActivity(this, 0, new Intent(this, Test.class), 0))
                    .setPriority(Notification.PRIORITY_HIGH);

            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                notify.setSmallIcon(R.mipmap.ic_launcher);
            }else{
                notify.setSmallIcon(R.drawable.ic_launcher_foreground);
            }

            NotificationManager manager =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "132";
                String description = "132";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel("132", name, importance);
                channel.setDescription(description);

                manager.createNotificationChannel(channel);
            }

            manager.notify(132, notify.build());

            Toast.makeText(this, "finished", Toast.LENGTH_LONG);
           // setLatestAction("finished");
        });

    }


}
