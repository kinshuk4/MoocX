package course.labs.notificationslab;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class DownloaderTaskFragment extends Fragment {

    private DownloadFinishedListener mCallback;
    private Context mContext;
    private final int MY_NOTIFICATION_ID = 11151990;

    @SuppressWarnings("unused")
    private static final String TAG = "Lab-Notifications";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Preserve across reconfigurations
        setRetainInstance(true);

        Bundle bundle = getArguments();
        List<Integer> friendsList = bundle.getIntegerArrayList("friends");
        Integer[] friends = friendsList.toArray(new Integer[friendsList.size()]);

        DownloaderTask downloaderTask = new DownloaderTask();
        downloaderTask.execute(friends);
    }

    // Assign current hosting Activity to mCallback
    // Store application context for use by downloadTweets()
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity.getApplicationContext();

        // Make sure that the hosting activity has implemented
        // the correct callback interface.
        try {
            mCallback = (DownloadFinishedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DownloadFinishedListener");
        }
    }

    // Null out mCallback
    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    public class DownloaderTask extends AsyncTask<Integer, Void, String[]> {
        private String[] downloadTweets(Integer resourceIDS[]) {

            final int simulatedDelay = 2000;
            String[] feeds = new String[resourceIDS.length];
            boolean downLoadCompleted = false;

            try {
                for (int idx = 0; idx < resourceIDS.length; idx++) {
                    InputStream inputStream;
                    BufferedReader in;
                    try {
                        // Pretend downloading takes a long time
                        Thread.sleep(simulatedDelay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    inputStream = mContext.getResources().openRawResource(
                            resourceIDS[idx]);
                    in = new BufferedReader(new InputStreamReader(inputStream));

                    String readLine;
                    StringBuffer buf = new StringBuffer();

                    while ((readLine = in.readLine()) != null) {
                        buf.append(readLine);
                    }

                    feeds[idx] = buf.toString();

                    if (null != in) {
                        in.close();
                    }
                }

                downLoadCompleted = true;
                saveTweetsToFile(feeds);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Notify user that downloading has finished
            notify(downLoadCompleted);

            return feeds;

        }

        @Override
        protected String[] doInBackground(Integer... params) {
            return downloadTweets(params);
        }

        @Override
        protected void onPostExecute(String[] strings) {
            if (mCallback != null) {
                mCallback.notifyDataRefreshed(strings);
            }
        }

        private void notify(final boolean success) {

            final Intent restartMainActivityIntent = new Intent(mContext,
                    MainActivity.class);
            restartMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            mContext.sendOrderedBroadcast(new Intent(
                            MainActivity.DATA_REFRESHED_ACTION), null,
                    new BroadcastReceiver() {
                        final String failMsg = mContext
                                .getString(R.string.download_failed_string);
                        final String successMsg = mContext
                                .getString(R.string.download_succes_string);
                        final String notificationSentMsg = mContext
                                .getString(R.string.notification_sent_string);

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (this.getResultCode() != MainActivity.IS_ALIVE) {

                                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, restartMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                                RemoteViews mContentView = new RemoteViews(
                                        mContext.getPackageName(),
                                        R.layout.custom_notification);

                                mContentView.setTextViewText(R.id.text, success ? successMsg : failMsg);

                                Notification.Builder notificationBuilder = new Notification.Builder(context)
                                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                                        .setTicker(success ? successMsg : failMsg)
                                        .setAutoCancel(true)
                                        .setContentIntent(pendingIntent)
                                        .setContent(mContentView);

                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(MY_NOTIFICATION_ID,notificationBuilder.build());

                                Toast.makeText(mContext, notificationSentMsg,
                                        Toast.LENGTH_LONG).show();

                            } else {
                                Toast.makeText(mContext,
                                        success ? successMsg : failMsg,
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }, null, 0, null, null);
        }

        private void saveTweetsToFile(String[] result) {
            PrintWriter writer = null;
            try {
                FileOutputStream fos = mContext.openFileOutput(
                        MainActivity.TWEET_FILENAME, Context.MODE_PRIVATE);
                writer = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(fos)));

                for (String s : result) {
                    writer.println(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != writer) {
                    writer.close();
                }
            }
        }
    }
}