package edu.washington.chau93.trackd.download_manager;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;

import edu.washington.chau93.trackd.R;
import edu.washington.chau93.trackd.Trackd;

/**
 * Created by Aaron Chau on 3/10/2015.
 */
public class Receiver extends BroadcastReceiver {
    private final String TAG = "Receiver";
    private long downloadId = -1;
    private boolean updatingOld = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the download manager
        DownloadManager dm = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
        String action = intent.getAction();
        if(DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action) && (downloadId != -1)){
            Log.d(TAG, "Download is done");
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(downloadId);
            Cursor cursor = dm.query(query);
            if(cursor.moveToFirst()){
                // Check the status of our download
                checkStatus(context, cursor);
            }
        }
    }

    private void checkStatus(Context context, Cursor cursor) {

        //column for status
        int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
        int status = cursor.getInt(columnIndex);
        //column for reason code if the download failed or paused
        int columnReason = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
        int reason = cursor.getInt(columnReason);
        //get the download filename
        int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
        String filename = cursor.getString(filenameIndex);

        String statusText = "";
        String reasonText = "";

        switch(status){
            case DownloadManager.STATUS_FAILED:
                statusText = "STATUS_FAILED";
                switch(reason){
                    case DownloadManager.ERROR_CANNOT_RESUME:
                        reasonText = "ERROR_CANNOT_RESUME";
                        break;
                    case DownloadManager.ERROR_DEVICE_NOT_FOUND:
                        reasonText = "ERROR_DEVICE_NOT_FOUND";
                        break;
                    case DownloadManager.ERROR_FILE_ALREADY_EXISTS:
                        reasonText = "ERROR_FILE_ALREADY_EXISTS";
                        break;
                    case DownloadManager.ERROR_FILE_ERROR:
                        reasonText = "ERROR_FILE_ERROR";
                        break;
                    case DownloadManager.ERROR_HTTP_DATA_ERROR:
                        reasonText = "ERROR_HTTP_DATA_ERROR";
                        break;
                    case DownloadManager.ERROR_INSUFFICIENT_SPACE:
                        reasonText = "ERROR_INSUFFICIENT_SPACE";
                        break;
                    case DownloadManager.ERROR_TOO_MANY_REDIRECTS:
                        reasonText = "ERROR_TOO_MANY_REDIRECTS";
                        break;
                    case DownloadManager.ERROR_UNHANDLED_HTTP_CODE:
                        reasonText = "ERROR_UNHANDLED_HTTP_CODE";
                        break;
                    case DownloadManager.ERROR_UNKNOWN:
                        reasonText = "ERROR_UNKNOWN";
                        break;
                }
                break;
            case DownloadManager.STATUS_PAUSED:
                statusText = "STATUS_PAUSED";
                switch(reason){
                    case DownloadManager.PAUSED_QUEUED_FOR_WIFI:
                        reasonText = "PAUSED_QUEUED_FOR_WIFI";
                        break;
                    case DownloadManager.PAUSED_UNKNOWN:
                        reasonText = "PAUSED_UNKNOWN";
                        break;
                    case DownloadManager.PAUSED_WAITING_FOR_NETWORK:
                        reasonText = "PAUSED_WAITING_FOR_NETWORK";
                        break;
                    case DownloadManager.PAUSED_WAITING_TO_RETRY:
                        reasonText = "PAUSED_WAITING_TO_RETRY";
                        break;
                }
                break;
            case DownloadManager.STATUS_PENDING:
                statusText = "STATUS_PENDING";
                break;
            case DownloadManager.STATUS_RUNNING:
                statusText = "STATUS_RUNNING";
                break;
            case DownloadManager.STATUS_SUCCESSFUL:
                statusText = "Update Successful.";

                //Get the URI of the download.
                //String uriString = cursor
                //        .getString(cursor
                //                .getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));

                if(updatingOld){
                    // Replace our old file with our new file.
                    replaceOld(context);
                }
                // Update our data in Trackd singleton
                updateData(context);
                break;
        }


//        Toast toast = Toast.makeText(context,
//                statusText + "\n" +
//                        reasonText,
//                Toast.LENGTH_LONG);
//        toast.show();

        cursor.close();
        Trackd.setUpdating(false);
        context.unregisterReceiver(this);
    }

    private void updateData(Context context) {
        Log.d(TAG, "Updating data.");
        JSONObject dataObj = null;
        try {
            // Get our file
            File f = new File(context.getExternalFilesDir(null), "trackd.json");
            // Make an Input Stream out of it.
            InputStream is = new FileInputStream(f);
            // Give it to Trackd to make the data.
            Trackd.setJsonData(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void replaceOld(Context context) {
        Log.d(TAG, "Replacing old file.");
        File path = context.getExternalFilesDir(null);
        // Get our files
        File reallyOldFile = new File(path, "old_trackd.json");
        File currFile = new File(path, "trackd.json");
        File newFile = new File(path, "updating_trackd.json");

        // Delete our old file if it's still around
        reallyOldFile.delete();

        // Rename our current file (trackd.json) to the backup file
        currFile.renameTo(new File(path, "old_trackd.json"));

        // Rename the new file (update_trackd.json) to trackd.json
        newFile.renameTo(new File(path, "trackd.json"));
    }

    public void setDownloadId(long downloadId) {
        this.downloadId = downloadId;
    }

    public void setUpdatingOld(boolean updatingOld) {
        this.updatingOld = updatingOld;
    }
}
