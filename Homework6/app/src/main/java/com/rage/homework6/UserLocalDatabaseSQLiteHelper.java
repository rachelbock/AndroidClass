package com.rage.homework6;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by rage on 3/31/16.
 */
public class UserLocalDatabaseSQLiteHelper extends SQLiteOpenHelper {

    public static final String TAG = UserLocalDatabaseSQLiteHelper.class.getSimpleName();

    public static final String DB_NAME = "users.db";

    public static UserLocalDatabaseSQLiteHelper dbInstance;

    public static UserLocalDatabaseSQLiteHelper getInstance(Context context) {
        if (dbInstance == null) {
            dbInstance = new UserLocalDatabaseSQLiteHelper(context.getApplicationContext(), DB_NAME, null, 2);
        }
        return dbInstance;
    }

    public UserLocalDatabaseSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.SQL_CREATE_USERTABLE);
        db.execSQL(MapTag.SQL_CREATE_MAPTAGTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(User.SQL_DELETE_USERTABLE);
        db.execSQL(MapTag.SQL_DELETE_USERTABLE);
        onCreate(db);
    }

    public String getCursorString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public int getCursorInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public void insertMapTag(MapTag mapTag){
        getWritableDatabase().insert(MapTag.TABLE_NAME, null, mapTag.getContentValues());
    }

    public void insertUser(User user) {
        getWritableDatabase().insert(User.TABLE_NAME, null, user.getContentValues());
    }

    public int getIdForUserWithName(String name) {
        String query = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + " = '" + name + "'";

        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        int id = 0;
        try {
            if (cursor.moveToFirst()) {
                id = getCursorInt(cursor, User.COLUMN_USER_ID);
            } else {
                throw new IllegalStateException("Attempted to get user Id for a user that did not exist: " + name);
            }
        } finally {
            cursor.close();
        }

        return id;
    }

    public boolean doesUserExist(String name) {
        String query = "SELECT * FROM " + User.TABLE_NAME + " WHERE " + User.COLUMN_USERNAME + " = '" + name + "'";

        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        boolean userExists = cursor.moveToFirst();

        cursor.close();

        return userExists;
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> allUsers = new ArrayList<>();
        Cursor cursor = getReadableDatabase().rawQuery("SELECT * FROM " + User.TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = getCursorInt(cursor, User.COLUMN_USER_ID);
                String name = getCursorString(cursor, User.COLUMN_USERNAME);
                allUsers.add(new User(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return allUsers;
    }

    public ArrayList<MapTag> getMapTagsForUser (int userId) {
        ArrayList<MapTag> mapTags = new ArrayList<>();

        String query = "SELECT * FROM " + MapTag.TABLE_NAME + " WHERE " + MapTag.COLUMN_USER_ID + " = " + userId;

        Cursor cursor = getReadableDatabase().rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int userIdNum = getCursorInt(cursor, MapTag.COLUMN_USER_ID);
                int mapIdNum = getCursorInt(cursor, MapTag.COLUMN_MAP_ID);
                String title = getCursorString(cursor, MapTag.COLUMN_TAG_TITLE);
                String description = getCursorString(cursor, MapTag.COLUMN_TAG_DESCRIPTION);
                double lat = cursor.getDouble(cursor.getColumnIndex(MapTag.COLUMN_LATITUDE));
                double longi = cursor.getDouble(cursor.getColumnIndex(MapTag.COLUMN_LONGITUDE));
                mapTags.add(new MapTag(userIdNum, mapIdNum, lat, longi, title, description));

            } while (cursor.moveToNext());
        }
        cursor.close();

        return mapTags;
    }

    public void removeMapTag(LatLng latLng) {
        String query = MapTag.COLUMN_LATITUDE + " <= " +
                (latLng.latitude+0.01) + " AND " + MapTag.COLUMN_LATITUDE + " >= " + (latLng.latitude-.01) + " AND " +
                MapTag.COLUMN_LONGITUDE + " <= " + (latLng.longitude+.01) + " AND " + MapTag.COLUMN_LONGITUDE +" >= " +
                (latLng.longitude-.01);
        Log.d(TAG, query);
        getWritableDatabase().delete(MapTag.TABLE_NAME, query, null);
    }
}
