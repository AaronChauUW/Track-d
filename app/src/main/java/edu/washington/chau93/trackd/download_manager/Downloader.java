package edu.washington.chau93.trackd.download_manager;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.net.URI;

import static android.app.DownloadManager.*;

/**
 * Created by Aaron Chau on 3/10/2015.
 */
public class Downloader extends Service {
    private final String TAG = "Service";
    private Receiver dlReceiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Starting the download.");
        // Get the url
        String url = intent.getStringExtra("url");

        // Get the download manager
        DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        // Make a reqest
        Request request = new Request(Uri.parse(url));

        String fileName = "trackd.json";
        File jsonFile = new File(getExternalFilesDir(null), fileName);
        if(jsonFile.exists()){
            // If our file already exist. Lets name the download updating_trackd so we don't
            // overwrite our current file. This way if something bad happens we wont ruin our
            // current data.
            Log.d(TAG, "JSON file already exist");
            fileName = "updating_trackd.json";
        }

        // If the file updating_trackd.json already exist for some reason then we need to
        // delete it. The only reason it would exist is if a update went bad.
        File badUpdate = new File(getExternalFilesDir(null), "updating_trackd.json");
        badUpdate.delete();

        /* The download will be put into the follwoing location:
                /mnt/sdcard/Android/data/edu.washington.chau93.trackd/files/trackd.json
        */
        request.setDestinationInExternalFilesDir(
                getApplicationContext(),
                null,
                fileName
        );

        // Start the download
        long downloadId = dm.enqueue(request);

        // Get the download receiver ready.
        dlReceiver = new Receiver();
        // Set the download id in the receiver so we can query our file
        dlReceiver.setDownloadId(downloadId);
        // Set the updating old file flag.
        dlReceiver.setUpdatingOld(!fileName.equals("trackd.json"));

        // Register the receiver so it can listen to broadcast?
        registerReceiver(dlReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
