package com.berlin.berlinreview.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.berlin.berlinreview.network.DataFactoryInetntService;

public class DataContentProvider extends ContentProvider {
    private static String TAG = DataFactoryInetntService.class.getCanonicalName();
    public static final String AUTHORITY = "com.berlin.provider";
    public static final String SCHEME = "content://";

    public static final String REVIEWOBJECTS = SCHEME + AUTHORITY + "/review";
    public static final Uri URI_REVIEWOBJECTS = Uri.parse(REVIEWOBJECTS);
    public static final String REVIEWOBJECTS_BASE = REVIEWOBJECTS + "/";


    public DataContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor result = null;
        if (URI_REVIEWOBJECTS.equals(uri)) {
            Log.d(TAG,uri.toString());
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(DbReviewObject.TABLE_NAME, DbReviewObject.FIELDS, null, null, null,
                            null, null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_REVIEWOBJECTS);
        } else if (uri.toString().startsWith(REVIEWOBJECTS_BASE)) {
            final long id = Long.parseLong(uri.getLastPathSegment());
            result = DatabaseHandler
                    .getInstance(getContext())
                    .getReadableDatabase()
                    .query(DbReviewObject.TABLE_NAME, DbReviewObject.FIELDS,
                            DbReviewObject.COL_ID + " IS ?",
                            new String[] { String.valueOf(id) }, null, null,
                            null, null);
            result.setNotificationUri(getContext().getContentResolver(), URI_REVIEWOBJECTS);

        } else {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
