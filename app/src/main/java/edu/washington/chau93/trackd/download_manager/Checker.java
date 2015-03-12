package edu.washington.chau93.trackd.download_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
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
        ConnectivityManager conMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Trackd.isAirplaneModeOn(context) ||
                conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED ||
                conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED){
            // User is not online.
            // TODO: Make it so event list will show nothing?
        } else if (conMgr.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTING) {
            // If the user is online then
            if(!Trackd.isUpdating()) {
                // Try to get some data
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
}
