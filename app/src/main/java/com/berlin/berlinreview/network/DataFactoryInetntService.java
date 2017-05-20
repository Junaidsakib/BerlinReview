package com.berlin.berlinreview.network;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class DataFactoryInetntService extends IntentService {

    public static final String TAG = DataFactoryInetntService.class.getCanonicalName();
    private static final String ACTION_DOWNLOAD = "download";
    private static final String ACTION_UPLOAD = "upload";

    private static final String EXTRA_PARAM_URL = "url";
    private static final String EXTRA_PARAM_PAYLOAD = "payload";

    public DataFactoryInetntService() {
        super("DataFactoryInetntService");
    }

    /**
     * Starts this service to perform action Download with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startActionDownload(Context context, String param1) {
        Intent intent = new Intent(context, DataFactoryInetntService.class);
        intent.setAction(ACTION_DOWNLOAD);
        intent.putExtra(EXTRA_PARAM_URL, param1);
        context.startService(intent);
    }


    /**
     * Starts this service to perform action Upload with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void startActionUpload(Context context, String param1, String param2) {
        Intent intent = new Intent(context, DataFactoryInetntService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(EXTRA_PARAM_URL, param1);
        intent.putExtra(EXTRA_PARAM_PAYLOAD,param2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            final String param1 = intent.getStringExtra(EXTRA_PARAM_URL);
            final String param2 = intent.getStringExtra(EXTRA_PARAM_PAYLOAD);
            if (ACTION_DOWNLOAD.equals(action)) {
                handleActionDownload(param1);
            } else if (ACTION_UPLOAD.equals(action)) {
                handleActionUpload(param1,param2);
            }
        }
    }

    /**
     * Handle action Download in the provided background thread with the provided
     * parameters.
     */
    private void handleActionDownload(String param1) {
        HttpDataDownloader httpConnection = new HttpDataDownloader(getApplicationContext());
        httpConnection.getHttpData(param1);
    }


    /**
     * Handle action Upload in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpload(String url, String payload) {
        HttpDataUploader httpDataUploader = new HttpDataUploader();
        httpDataUploader.sendReview(url, payload);

    }
}
