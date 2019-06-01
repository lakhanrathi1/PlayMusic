package com.example.lakhan.musicapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.R.string.ok;

/**
 * Created by lakhan on 14/2/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contact";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ANAME = "frate";
    private static final String KEY_FNAME = "fname";
    private static final String KEY_FAV = "fav";
    private static final String KEY_URL = "url";
    private static final String KEY_MOST = "most";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Create tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACTS="CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID +" INTEGER PRIMARY KEY,"
                + KEY_FNAME +" TEXT,"
                + KEY_ANAME +" TEXT,"
                + KEY_URL +" TEXT,"
                + KEY_FAV +" TEXT,"
                + KEY_MOST+" INTEGER"+")";
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Insert values to the table contacts
    public void addContacts(SongInfo s){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values=new ContentValues();

        values.put(KEY_FNAME, s.getSongname());
        values.put(KEY_ANAME, s.getArtistname());
        values.put(KEY_URL, s.getSongUrl());
        values.put(KEY_FAV,s.getFav());
        values.put(KEY_MOST,s.getmost());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }


    /**
     *Getting All Contacts
     **/

    public List<SongInfo> getAllContacts() {
        List<SongInfo> contactList = new ArrayList<SongInfo>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT fname,frate,url FROM " + TABLE_CONTACTS + " WHERE fav = '0'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
               SongInfo contact = new SongInfo();
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                contact.setSongname(cursor.getString(0));
                contact.setSongUrl(cursor.getString(2));
                contact.setArtistname(cursor.getString(1));


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    public void update(String id) {

        SQLiteDatabase db = this.getReadableDatabase();
        String strSQL = "UPDATE "+TABLE_CONTACTS+" SET fav = '0' WHERE url = '"+ id+"'";
        db.execSQL(strSQL);
    }

    public void most(String songUrl) {

     int my=0;
        String selectQuery = "SELECT most,fav FROM " + TABLE_CONTACTS + " WHERE url = '"+songUrl+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            int ok = cursor.getInt(0);
            // my = Integer.parseInt(ok);
           // my++;
            ok++;
            Log.i("MY",""+ok);
        }
        String strSQL = "UPDATE "+TABLE_CONTACTS+" SET most = "+ok +" WHERE url = '"+ songUrl+"'";
        db.execSQL(strSQL);

    }

    public List<SongInfo> getAllContactsMost() {

        List<SongInfo> contactList = new ArrayList<SongInfo>();
        // Select All Query
        String selectQuery = "SELECT DISTINCT fname,frate,url,most FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                int ok = cursor.getInt(3);
                //int  my = Integer.parseInt(ok);
                Log.i("MY1",""+ok);

                if(ok>3) {
                    SongInfo contact = new SongInfo();
                    //contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setSongname(cursor.getString(0));
                    contact.setSongUrl(cursor.getString(2));
                    contact.setArtistname(cursor.getString(1));


                    // Adding contact to list
                    contactList.add(contact);
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;

    }
}
