package com.esgi.astrologia;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BddHandler extends SQLiteOpenHelper {
    public static final String SIGN_TABLE_NAME = "Sign";
    public static final String SIGN_KEY = "id";
    public static final String SIGN_LABEL = "label";
    public static final String SIGN_START_DATE = "start_date";
    public static final String SIGN_END_DATE = "end_date";

    public static final String SIGN_TABLE_CREATE = "CREATE TABLE " + SIGN_TABLE_NAME + " (" +
            SIGN_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            SIGN_LABEL + " VARCHAR(255), " +
            SIGN_START_DATE + " DATE(255), " +
            SIGN_END_DATE + " DATE);";

    public static final String INIT_SIGN_TABLE = "INSERT INTO " + SIGN_TABLE_NAME + " VALUES " +
            "('', 'Bélier', '2017-03-21', '2017-04-20'), " +
            "('', 'Taureau', '2017-04-21', '2017-05-21'), " +
            "('', 'Gémeaux', '2017-05-22', '2017-06-21'), " +
            "('', 'Cancer', '2017-06-22', '2017-07-21'), " +
            "('', 'Lion', '2017-07-22', '2017-08-22'), " +
            "('', 'Vierge', '2017-08-22', '2017-09-22'), " +
            "('', 'Balance', '2017-09-23', '2017-10-20'), " +
            "('', 'Scorpion', '2017-10-21', '2017-11-22'), " +
            "('', 'Sagittaire', '2017-11-23', '2017-12-21'), " +
            "('', 'Capricorne', '2017-12-22', '2017-01-20'), " +
            "('', 'Verseau', '2017-01-21', '2017-02-19'), " +
            "('', 'Poisson', '2017-02-20', '2017-03-20')";



    public BddHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public BddHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("TAG_BDD", "onCreate START");

        sqLiteDatabase.execSQL(SIGN_TABLE_CREATE);
        sqLiteDatabase.execSQL(INIT_SIGN_TABLE);

        Log.i("TAG_BDD", "onCreate END");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
