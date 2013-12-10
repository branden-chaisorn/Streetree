package com.brandenc.streetree.contentprovider;


import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.brandenc.streetree.database.PolylineTable;

import java.util.Arrays;
import java.util.HashSet;

//import com.brandenc.streetree.database.TodoDatabaseHelper;


/**
 * Created by bchaishorn on 11/1/13.
 */
public class PolylineContentProvider extends ContentProvider{

    // database
    private PolylineTable database;

    // used for the UriMacher
    private static final int POLYLINES = 10;
    private static final int POLYLINE_ID = 20;

    private static final String AUTHORITY = "com.brandenc.streetree.contentprovider";

    private static final String BASE_PATH = "polylines";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/polylines";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/polyline";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, POLYLINES);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", POLYLINE_ID);
    }

    @Override
    public boolean onCreate() {
        database = new PolylineTable(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the table
        queryBuilder.setTables(PolylineTable.TABLE_POLYLINES);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case POLYLINES:
                break;
            case POLYLINE_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(PolylineTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case POLYLINES:
                id = sqlDB.insert(PolylineTable.TABLE_POLYLINES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case POLYLINES:
                rowsDeleted = sqlDB.delete(PolylineTable.TABLE_POLYLINES, selection,
                        selectionArgs);
                break;
            case POLYLINE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(PolylineTable.TABLE_POLYLINES,
                            PolylineTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(PolylineTable.TABLE_POLYLINES,
                            PolylineTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case POLYLINES:
                rowsUpdated = sqlDB.update(PolylineTable.TABLE_POLYLINES,
                        values,
                        selection,
                        selectionArgs);
                break;
            case POLYLINE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(PolylineTable.TABLE_POLYLINES,
                            values,
                            PolylineTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(PolylineTable.TABLE_POLYLINES,
                            values,
                            PolylineTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }


    private void checkColumns(String[] projection) {
        String[] available = { PolylineTable.COLUMN_POLYLINES,
                PolylineTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
