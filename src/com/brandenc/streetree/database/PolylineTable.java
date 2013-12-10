package com.brandenc.streetree.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bchaishorn on 10/31/13.
 */
public class PolylineTable extends SQLiteOpenHelper{

    public static final String TABLE_POLYLINES = "polylines";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_POLYLINES = "polylines";

    private static final String DATABASE_NAME = "commments.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_POLYLINES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_POLYLINES
            + " text not null);";

    public PolylineTable(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(PolylineTable.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POLYLINES);
        onCreate(db);
    }

}
