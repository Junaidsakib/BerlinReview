package com.berlin.berlinreview.database;

import android.content.ContentValues;
import android.database.Cursor;


/**
 * Created by prime on 5/20/17.
 */

public class DbReviewObject {

    // SQL convention says Table name should be "singular"
    public static final String TABLE_NAME = "ReviewObject";

    public static final String COL_ID = "_id";
    public static final String REVIEW_ID = "review_id";
    public static final String RATING = "rating";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";
    public static final String AUTHOR = "author";
    public static final String FOREIGN_LANGUAGE = "foreign_language";
    public static final String DATE = "date";
    public static final String DATE_UNFORMATTED = "date_unformatted";
    public static final String LANGUAGE_CODE = "language_code";
    public static final String TRAVELLER_TYPE = "traveller_type";
    public static final String REVIEWER_NAME = "reviewer_name";
    public static final String REVIEWER_COUNTRY = "reviewer_country";


    public long id = 2; // Try update, -1 otherwise
    public String review_id;
    public String rating;
    public String title;
    public String message;
    public String author;
    public String foreignLanguage;
    public String date;
    public String date_unformatted;
    public String languageCode;
    public String traveler_type;
    public String reviewerName;
    public String reviewerCountry;


    public static final String[] FIELDS = {COL_ID, REVIEW_ID, RATING,
            TITLE, MESSAGE, AUTHOR, FOREIGN_LANGUAGE, DATE, DATE_UNFORMATTED, LANGUAGE_CODE,
            TRAVELLER_TYPE, REVIEWER_NAME, REVIEWER_COUNTRY};

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY,"
                    + REVIEW_ID + " TEXT NOT NULL DEFAULT '',"
                    + RATING + " TEXT NOT NULL DEFAULT '',"
                    + TITLE + " TEXT NOT NULL DEFAULT '',"
                    + MESSAGE + " TEXT NOT NULL DEFAULT '',"
                    + AUTHOR + " TEXT NOT NULL DEFAULT '',"
                    + DATE + " TEXT NOT NULL DEFAULT '',"
                    + FOREIGN_LANGUAGE + " TEXT NOT NULL DEFAULT '',"
                    + DATE_UNFORMATTED + " TEXT NOT NULL DEFAULT '',"
                    + LANGUAGE_CODE + " TEXT NOT NULL DEFAULT '',"
                    + TRAVELLER_TYPE + " TEXT NOT NULL DEFAULT '',"
                    + REVIEWER_NAME + " TEXT NOT NULL DEFAULT '',"
                    + REVIEWER_COUNTRY + " TEXT NOT NULL DEFAULT ''"
                    + ")";


    public DbReviewObject(){}

    public DbReviewObject(final Cursor cursor) {

        this.author = cursor.getString(cursor.getColumnIndex(AUTHOR));
        this.date = cursor.getString(cursor.getColumnIndex(DATE));
        this.date_unformatted = cursor.getString(cursor.getColumnIndex(DATE_UNFORMATTED));
        this.foreignLanguage = cursor.getString(cursor.getColumnIndex(FOREIGN_LANGUAGE));
        this.id = cursor.getLong(cursor.getColumnIndex(COL_ID));
        this.message = cursor.getString(cursor.getColumnIndex(MESSAGE));
        this.rating = cursor.getString(cursor.getColumnIndex(RATING));
        this.review_id = cursor.getString(cursor.getColumnIndex(REVIEW_ID));
        this.reviewerName = cursor.getString(cursor.getColumnIndex(REVIEWER_NAME));
        this.reviewerCountry= cursor.getString(cursor.getColumnIndex(REVIEWER_COUNTRY));
        this.title = cursor.getString(cursor.getColumnIndex(TITLE));
        this.languageCode= cursor.getString(cursor.getColumnIndex(LANGUAGE_CODE));
        this.traveler_type = cursor.getString(cursor.getColumnIndex(TRAVELLER_TYPE));

    }


    /**
     * Return the fields in a ContentValues object, suitable for insertion
     * into the database.
     */
    public ContentValues getContent() {
        final ContentValues values = new ContentValues();
        // Note that id is NOT included here, we are attempting an update.
        values.put(REVIEW_ID, review_id);
        values.put(AUTHOR, author);
        values.put(DATE,date);
        values.put(DATE_UNFORMATTED,date_unformatted);
        values.put(FOREIGN_LANGUAGE,foreignLanguage);
        values.put(MESSAGE,message);
        values.put(RATING,rating);
        values.put(REVIEW_ID,review_id);
        values.put(REVIEWER_NAME,reviewerName);
        values.put(REVIEWER_COUNTRY,reviewerCountry);
        values.put(TITLE,title);
        values.put(LANGUAGE_CODE,languageCode);
        values.put(TRAVELLER_TYPE,traveler_type);

        return values;
    }

}
