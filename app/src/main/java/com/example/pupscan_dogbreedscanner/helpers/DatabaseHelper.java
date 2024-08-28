package com.example.pupscan_dogbreedscanner.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DogBreedScannerDB";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your tables here
        createTableBcsHistory(db);
        createTableModelLabelScore(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades as needed
    }

    private void createTableBcsHistory(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + BcsHistoryTable.TABLE_NAME + " (" +
                BcsHistoryTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BcsHistoryTable.COLUMN_BREED + " TEXT NOT NULL, " +
                BcsHistoryTable.COLUMN_GENDER + " TEXT NOT NULL, " +
                BcsHistoryTable.COLUMN_WEIGHT + " REAL NOT NULL, " +
                BcsHistoryTable.COLUMN_RESULT + " INTEGER NOT NULL);";

        db.execSQL(createTableQuery);
    }

    private void createTableModelLabelScore(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + ModelLabelScoreTable.TABLE_NAME + " (" +
                ModelLabelScoreTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ModelLabelScoreTable.COLUMN_MODEL + " TEXT NOT NULL, " +
                ModelLabelScoreTable.COLUMN_LABEL + " TEXT NOT NULL, " +
                ModelLabelScoreTable.COLUMN_SCORE + " REAL NOT NULL);";

        db.execSQL(createTableQuery);
    }

    public class BcsHistoryTable {

        public static final String TABLE_NAME = "bcs_history";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_BREED = "breed";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_RESULT = "result";
    }

    public class ModelLabelScoreTable {

        public static final String TABLE_NAME = "model_label_score";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_MODEL = "model";
        public static final String COLUMN_LABEL = "label";
        public static final String COLUMN_SCORE = "score";
    }

    public Cursor getAllBcsHistory() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BcsHistoryTable.COLUMN_ID,
                BcsHistoryTable.COLUMN_BREED,
                BcsHistoryTable.COLUMN_GENDER,
                BcsHistoryTable.COLUMN_WEIGHT,
                BcsHistoryTable.COLUMN_RESULT
        };

        Cursor cursor = db.query(
                BcsHistoryTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Do not close the database here

        return cursor;
    }

    public Cursor getAllModelLabelScore() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                ModelLabelScoreTable.COLUMN_ID,
                ModelLabelScoreTable.COLUMN_MODEL,
                ModelLabelScoreTable.COLUMN_LABEL,
                ModelLabelScoreTable.COLUMN_SCORE
        };

        Cursor cursor = db.query(
                ModelLabelScoreTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // Do not close the database here

        return cursor;
    }

    public long insertModelLabelScore(String model, String label, float score) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ModelLabelScoreTable.COLUMN_MODEL, model);
        values.put(ModelLabelScoreTable.COLUMN_LABEL, label);
        values.put(ModelLabelScoreTable.COLUMN_SCORE, score);

        long newRowId = db.insert(ModelLabelScoreTable.TABLE_NAME, null, values);


        return newRowId;
    }

}
