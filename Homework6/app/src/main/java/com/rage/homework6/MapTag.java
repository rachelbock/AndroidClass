package com.rage.homework6;

import android.content.ContentValues;
import android.provider.BaseColumns;

/**
 * MapTag object to hold the location of a map tag and the user put in description and title.
 */
public class MapTag implements BaseColumns {

    private int userId;
    private int mapId;
    private double latitude;
    private double longitude;
    private String tagTitle;
    private String tagDescription;

    public static final String TABLE_NAME = "map_tags";
    public static final String COLUMN_MAP_ID = "map_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TAG_TITLE = "title";
    public static final String COLUMN_TAG_DESCRIPTION = "description";
    public static final String SQL_CREATE_MAPTAGTABLE = "CREATE TABLE " + MapTag.TABLE_NAME + " (" +
            MapTag.COLUMN_MAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MapTag.COLUMN_USER_ID + " INTEGER, " +
            MapTag.COLUMN_LATITUDE + " REAL, " +
            MapTag.COLUMN_LONGITUDE + " REAL, " +
            MapTag.COLUMN_TAG_TITLE + " TEXT, " +
            MapTag.COLUMN_TAG_DESCRIPTION + " TEXT)";
    public static final String SQL_DELETE_USERTABLE = "DROP TABLE IF EXISTS " + MapTag.TABLE_NAME;

    public MapTag(int userIdNum, double lat, double longi, String title, String description){
        userId = userIdNum;
        latitude = lat;
        longitude = longi;
        tagTitle = title;
        tagDescription = description;
    }

    public MapTag(int userIdNum, int mapIdnum, double lat, double longi, String title, String description){
        userId = userIdNum;
        mapId = mapIdnum;
        latitude = lat;
        longitude = longi;
        tagTitle = title;
        tagDescription = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTagTitle() {
        return tagTitle;
    }

    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }

    public String getTagDescription() {
        return tagDescription;
    }

    public void setTagDescription(String tagDescription) {
        this.tagDescription = tagDescription;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, userId);
        contentValues.put(COLUMN_LATITUDE, latitude);
        contentValues.put(COLUMN_LONGITUDE, longitude);
        contentValues.put(COLUMN_TAG_TITLE, tagTitle);
        contentValues.put(COLUMN_TAG_DESCRIPTION, tagDescription);
        return contentValues;
    }
}
