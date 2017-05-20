package com.berlin.berlinreview.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.berlin.berlinreview.R;
import com.berlin.berlinreview.network.DataFactoryInetntService;

/**
 * Created by Junaid on 5/20/17.
 */

 public class DatabaseHandler extends SQLiteOpenHelper {

    private static String TAG = DatabaseHandler.class.getCanonicalName();
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "backupDatabase";

    private static DatabaseHandler singleton;
    private final Context context;

    public static DatabaseHandler getInstance(final Context context) {
        if (singleton == null) {
            singleton = new DatabaseHandler(context);
        }
        return singleton;
    }

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context.getApplicationContext();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbReviewObject.CREATE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized DbReviewObject getReviewObject(final long id) {
        final SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.query(DbReviewObject.TABLE_NAME,
                DbReviewObject.FIELDS, DbReviewObject.COL_ID + " IS ?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor == null || cursor.isAfterLast()) {
            return null;
        }

        DbReviewObject item = null;
        if (cursor.moveToFirst()) {
            item = new DbReviewObject(cursor);
        }
        cursor.close();

        return item;
    }



    public synchronized boolean putDbReviewObject(final DbReviewObject dbReviewObject) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        Log.d(TAG,"dbObj id is"+dbReviewObject.id);

        if (dbReviewObject.id > -1) {
            result += db.update(DbReviewObject.TABLE_NAME, dbReviewObject.getContent(),
                    DbReviewObject.REVIEW_ID + " IS ?",
                    new String[] { String.valueOf(dbReviewObject.review_id) });

            Log.d(TAG,"Update Executed on"+dbReviewObject.review_id);
        }

        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(DbReviewObject.TABLE_NAME, null,
                    dbReviewObject.getContent());

            if (id > -1) {
                dbReviewObject.id = id;
                success = true;
            }
        }

        if (success) {
            notifyProviderOnPersonChange();
        }

        return success;
    }


    private void notifyProviderOnPersonChange() {
        context.getContentResolver().notifyChange(
                DataContentProvider.URI_REVIEWOBJECTS, null, false);
    }
}
