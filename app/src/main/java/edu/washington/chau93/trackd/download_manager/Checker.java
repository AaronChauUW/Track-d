package edu.washington.chau93.trackd.download_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * Created by Aaron Chau on 3/10/2015.
 */
public class Checker extends BroadcastReceiver {
    private final String TAG = "Checker";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!Trackd.isUpdating()) {
            Log.d(TAG, "Checking for updates.");
            Trackd.setUpdating(true);
            String url = context.getString(R.string.url);
            Intent downloader = new Intent(context, Downloader.class);
            downloader.putExtra("url", url);

            context.startService(downloader);
        } else {
            Log.d(TAG, "Update is already in progress.");
        }
    }
}
