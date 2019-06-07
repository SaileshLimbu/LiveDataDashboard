package np.com.smartpolicestation.services;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import np.com.smartpolicestation.R;
import np.com.smartpolicestation.Shared;
import np.com.smartpolicestation.Utils;
import np.com.smartpolicestation.activities.DashboardActivity;
import np.com.smartpolicestation.listeners.MyChildEventListener;
import np.com.smartpolicestation.models.EQ;
import np.com.smartpolicestation.models.RFID;

public class LiveDataService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Shared shared = new Shared(this);
        FirebaseDatabase.getInstance().getReference("eq").addChildEventListener(new MyChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (shared.getBackground()) {
                    EQ eq = dataSnapshot.getValue(EQ.class);
                    sendNotification(R.drawable.eq, "Earthquake", "Earthquake : " + eq.getMagnitude() + " Richter Scale", Utils.getTimeAgo(Long.parseLong(eq.getTimestamp())));
                }
            }
        });
        FirebaseDatabase.getInstance().getReference("rfid").addChildEventListener(new MyChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                RFID rfid = dataSnapshot.getValue(RFID.class);
                String content = "";
                if(rfid.getIsAuth().equals("true")){
                    content = "Access Granted";
                }else{
                    content = "Access Denied";
                }
                sendNotification(R.drawable.eq, "RFID", content, Utils.getTimeAgo(Long.parseLong(rfid.getTimestamp())));
            }
        });
        return Service.START_STICKY;
    }

    public void sendNotification(int icon, String title, String content, String date) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(icon)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(date)
                .setAutoCancel(true)
                .setVibrate(new long[] { 1000, 1000, 1000 });

        if(!isForeground(this)) {
            notificationBuilder.setContentIntent(pendingIntent);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());
    }

    public static boolean isForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfo = manager.getRunningTasks(1);
        ComponentName componentInfo = runningTaskInfo.get(0).topActivity;
        return componentInfo.getPackageName().equals(context.getPackageName());
    }
}
