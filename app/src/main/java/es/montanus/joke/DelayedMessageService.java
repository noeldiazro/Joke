package es.montanus.joke;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import org.jetbrains.annotations.Nullable;

public class DelayedMessageService extends IntentService {

    private static final String SERVICE_NAME = "DelayedMessageService";
    public static final String EXTRA_MESSAGE = "message";

    public DelayedMessageService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        tryWaiting(10000);

        if (intent != null) issue(getNotification(intent));
    }

    private void tryWaiting(long millis) {
        synchronized (this) {
            try {
                wait(millis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void issue(Notification notification) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(5453, notification);
    }

    private Notification getNotification(@NonNull Intent intent) {
        return new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentTitle(getString(R.string.question))
                .setContentText(intent.getStringExtra(EXTRA_MESSAGE))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{0, 1000})
                .setAutoCancel(true)
                .setContentIntent(createAction())
                .build();
    }

    private PendingIntent createAction() {
        final Intent actionIntent = new Intent(this, MainActivity.class);
        return PendingIntent.getActivity(
                this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
