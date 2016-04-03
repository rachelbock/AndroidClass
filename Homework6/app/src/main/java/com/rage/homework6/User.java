package com.rage.homework6;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

/**
 * User class to store user name and id number.
 */
public class User implements BaseColumns, Parcelable {

    private String name;
    private int idNumber;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "name";
    public static final String COLUMN_USER_ID = "id";
    public static final String SQL_CREATE_USERTABLE = "CREATE TABLE " + User.TABLE_NAME + " (" +
            User.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            User.COLUMN_USERNAME + " TEXT)";
    public static final String SQL_DELETE_USERTABLE = "DROP TABLE IF EXISTS " + User.TABLE_NAME;

    public User(String username){
        name = username;
    }

    public User(int id, String username){
        name = username;
        idNumber = id;
    }

    protected User(Parcel in) {
        name = in.readString();
        idNumber = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(idNumber);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, name);
        return contentValues;
    }

}
