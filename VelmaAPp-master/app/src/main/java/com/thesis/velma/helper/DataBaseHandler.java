package com.thesis.velma.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.thesis.velma.helper.DBInfo.DataInfo;

import java.util.StringTokenizer;

import static com.thesis.velma.LandingActivity.db;

/**
 * Created by andrewlaurienrsocia on 03/01/2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    public static final int database_version = 1;

    public String CREATE_EVENTS = "CREATE TABLE " + DataInfo.TBl_Events + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "" + DataInfo.UserID + " TEXT, " +
            "" + DataInfo.EventID + " TEXT, " +
            DataInfo.EventName + " TEXT," + DataInfo.EventDescription + " TEXT," + DataInfo.EventLocation + " TEXT," +
            DataInfo.EventStartDate + " TEXT," + DataInfo.EventStartTime + " TEXT," + DataInfo.EventEndDate + " TEXT," +
            DataInfo.EventEndTime + " TEXT," + DataInfo.Notify + " TEXT," + DataInfo.Extra1 + " TEXT," +
            DataInfo.Extra2 + " TEXT," + DataInfo.Extra3 + " TEXT," + DataInfo.Extra4 + " TEXT)";
    public String CREATE_CONTACTS = "CREATE TABLE " + DataInfo.TBlContacts + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            DataInfo.Name + " TEXT," + DataInfo.EmailAdd + " TEXT," + DataInfo.Extra1 + " TEXT," +
            DataInfo.Extra2 + " TEXT," + DataInfo.Extra3 + " TEXT," + DataInfo.Extra4 + " TEXT)";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EVENTS);
        db.execSQL(CREATE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    //region CONSTRUCTOR
    //***********************
    //CONSTRUCTOR
    //***********************
    public DataBaseHandler(Context context) {
        super(context, DataInfo.DATABASE_NAME, null, database_version);

    }

    //endregion

    //region METHODS

    public void saveEvent(String userid, Long eventid, String eventname, String eventDescription, String eventLocation,
                          String eventStartDate, String eventStartTime, String eventEndDate, String eventEndTime, String notify, String invitedfirends) {

        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DataInfo.UserID, userid);
        cv.put(DataInfo.EventID, eventid);
        cv.put(DataInfo.EventName, eventname);
        cv.put(DataInfo.EventDescription, eventDescription);
        cv.put(DataInfo.EventLocation, eventLocation);
        cv.put(DataInfo.EventStartDate, eventStartDate);
        cv.put(DataInfo.EventStartTime, eventStartTime);
        cv.put(DataInfo.EventEndDate, eventEndDate);
        cv.put(DataInfo.EventEndTime, eventEndTime);
        cv.put(DataInfo.Notify, notify);
        cv.put(DataInfo.Extra1, invitedfirends);

        sql.insert(DataInfo.TBl_Events, null, cv);

    }

    public void saveContact(String name, String email) {

        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DataInfo.Name, name);
        cv.put(DataInfo.EmailAdd, email);

        sql.insert(DataInfo.TBlContacts, null, cv);

    }

    public Cursor getEvents() {

        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM " + DataInfo.TBl_Events, null);

        return c;

    }

    public Cursor getContacts() {

        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM " + DataInfo.TBlContacts, null);

        return c;

    }

    public Cursor getEventDetails(long id) {

        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor c = sql.rawQuery("SELECT * FROM " + DataInfo.TBl_Events + " Where _id=" + id, null);

        return c;

    }

    public void deleteEvent(long id) {

        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(DataInfo.TBl_Events, "_id =" + id, null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

    }

    //endregion

    public int retrieveDayEvent()
    {
        String countQuery = "SELECT  * FROM " + DataInfo.TBl_Events;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        Log.d("Velama", ""+cnt);
        cursor.close();
        return cnt;

//        SQLiteDatabase sql = db.getReadableDatabase();
//
//        Cursor c = sql.rawQuery("SELECT COUNT(*) FROM " + DataInfo.TBl_Events + " Where StartDate='" + StartDate +"'", null);
//        int a = c.getCount();
//        Log.d("Velama", ""+a);
//
//        return a;
    }

    public void updateTable(long eventID, String eventName, String eventDescription, String eventLocation, String startDate,
                            String startTime, String endDate, String endTime, String notify, String invitedFriends ){

        SQLiteDatabase sql = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(DataInfo.EventName, eventName);
        cv.put(DataInfo.EventDescription, eventDescription);
        cv.put(DataInfo.EventLocation, eventLocation);
        cv.put(DataInfo.EventStartDate, startDate);
        cv.put(DataInfo.EventStartTime, startTime);
        cv.put(DataInfo.EventEndDate, endDate);
        cv.put(DataInfo.EventEndTime, endTime);
        cv.put(DataInfo.Notify, notify);
        cv.put(DataInfo.Extra1, invitedFriends);

        sql.update(DataInfo.TBl_Events, cv, "_id=" + eventID, null);
        sql.close();

    }

    public int getRowFromId(long id) {

        String query = "SELECT * FROM " + DataInfo.TBl_Events + " WHERE _id=" + id;
        SQLiteDatabase sql = db.getReadableDatabase();

        Cursor cursor = sql.rawQuery(query, null);
        int cnt = cursor.getCount();
        Log.d("Position", ""+cnt);
        cursor.close();
        return cnt;
    }

    public Cursor conflictChecker(String sd, String st, String ed, String et ){

        StringTokenizer sdtoken = new StringTokenizer(sd, "-");
        StringTokenizer edtoken = new StringTokenizer(ed, "-");
        StringTokenizer sttoken = new StringTokenizer(st, ":");
        StringTokenizer ettoken = new StringTokenizer(et, ":");

        String sdd = sdtoken.nextToken();
        String sdm = sdtoken.nextToken();
        String sdy = sdtoken.nextToken();
        String edd = edtoken.nextToken();
        String edm = edtoken.nextToken();
        String edy = edtoken.nextToken();

        String sth = sttoken.nextToken();
        String stm = sttoken.nextToken();
        String eth = ettoken.nextToken();
        String etm = ettoken.nextToken();

        if(sdd.length()==1){
            sdd = "0"+sdd;
        }
        if(sdm.length()==1){
            sdm = "0"+sdm;
        }
        if(edd.length()==1){
            edd = "0"+edd;
        }
        if(edm.length()==1){
            edm = "0"+edm;
        }

        if(sth.length()==1){
            sth = "0"+sth;
        }
        if(stm.length()==1){
            stm = "0"+stm;
        }
        if(eth.length()==1){
            eth = "0"+eth;
        }
        if(etm.length()==1){
            etm = "0"+etm;
        }
        sd= sdd+"-"+sdm+"-"+sdy;
        ed= edd+"-"+edm+"-"+edy;

        st= sth+":"+stm;
        et= eth+":"+etm;

        SQLiteDatabase sql = db.getReadableDatabase();

        //  cast(date as datetime)+cast(time as datetime)


        Cursor c = sql.rawQuery("SELECT _id, EventName, EventDescription,StartDate, StartTime, EndTime, EndDate  FROM " + DataInfo.TBl_Events+
                " WHERE ((StartTime BETWEEN '"+st+"' AND '"+et+
                "') AND ((StartDate BETWEEN '" +sd+ "' AND '"+ed+"') OR (EndDate BETWEEN '"+sd+"' AND '"+ed+
                "') OR ('"+ sd+ "' BETWEEN StartDate AND EndDate))) OR ((EndTime BETWEEN '" +st+"' AND '"+et+
                "') AND ((StartDate BETWEEN '" +sd+ "' AND '"+ed+"') OR (EndDate BETWEEN '"+sd+"' AND '"+ed+
                "') OR ('"+ sd+ "' BETWEEN StartDate AND EndDate))) OR (('"+st+"' BETWEEN StartTime AND EndTime) AND ((StartDate BETWEEN '" +sd+ "' AND '"+ed+"') OR (EndDate BETWEEN '"+sd+"' AND '"+ed+
                "') OR ('"+ sd+"' BETWEEN StartDate AND EndDate)))", null);




        return c;
    }

}
