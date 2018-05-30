package es.montanus.joke;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

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
        if (intent != null) {
            Log.v(SERVICE_NAME, intent.getStringExtra(EXTRA_MESSAGE));
        }
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
}
