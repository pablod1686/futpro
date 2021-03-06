package Databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import FUT.FUTPlayerAttributes;

/**
 * Created by jpablo09 on 10/16/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "FUTDatabaseXIX";

    // Table Names
    private static final String TABLE_FUT = "FUT_PLAYER_TBL";

    // Common column names
    private static final String KEY_ID = "fut_id";

    private static final String TAG_KEY = "player_id";

    private static final String TAG_NAME = "name";
    private static final String TAG_RTG = "rtg";
    private static final String TAG_POS = "pos";

    private static final String TAG_CLUB = "club";
    private static final String TAG_LEAGUE ="league";
    private static final String TAG_NATION ="nation";
    private static final String TAG_PACE ="pace";
    private static final String TAG_SHOT ="shot";
    private static final String TAG_PASS ="pass";
    private static final String TAG_DRIBBLE ="dribble";
    private static final String TAG_DEFENSE ="defense";
    private static final String TAG_PHYSICAL ="physical";
    private static final String TAG_WEAKFOOT ="weakfoot";
    private static final String TAG_SKILL ="skill";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_FUT + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"+  TAG_KEY + " TEXT," + TAG_NAME + " TEXT," + TAG_RTG + " TEXT," + TAG_POS +  " TEXT,"
                + TAG_CLUB +  " TEXT," + TAG_LEAGUE +  " TEXT," + TAG_NATION +  " TEXT,"
                + TAG_PACE +  " TEXT," + TAG_SHOT +  " TEXT," + TAG_PASS +  " TEXT," + TAG_DRIBBLE +  " TEXT,"
                + TAG_DEFENSE +  " TEXT," + TAG_PHYSICAL +  " TEXT," + TAG_WEAKFOOT +  " TEXT," + TAG_SKILL +  " TEXT" + ")";


        // creating required tables
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FUT);


        // create new tables
        onCreate(db);
    }

    public void addFUTPlayers(FUTPlayerAttributes attributes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TAG_KEY, attributes.getKey());
        values.put(TAG_NAME, attributes.getPlauerName());
        values.put(TAG_RTG, attributes.getPlayerRtg());
        values.put(TAG_POS, attributes.getPlayerPOS());
        values.put(TAG_CLUB, attributes.getPlayerClub());
        values.put(TAG_LEAGUE, attributes.getPlayerLeague());
        values.put(TAG_NATION, attributes.getPlayerNation());
        values.put(TAG_PACE, attributes.getPace());
        values.put(TAG_SHOT, attributes.getShot());
        values.put(TAG_PASS, attributes.getPass());
        values.put(TAG_DRIBBLE, attributes.getDribble());
        values.put(TAG_DEFENSE, attributes.getDefense());
        values.put(TAG_PHYSICAL, attributes.getPhysical());
        values.put(TAG_WEAKFOOT, attributes.getWeakFoot());
        values.put(TAG_SKILL, attributes.getSkill());



        // Inserting Row
        db.insert(TABLE_FUT, null, values);
        db.close(); // Closing database connection

    }

    public List<FUTPlayerAttributes> getFUTPlayers() {
        List<FUTPlayerAttributes> attributesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_FUT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                FUTPlayerAttributes attributes = new FUTPlayerAttributes(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11), cursor.getString(12),cursor.getString(13), cursor.getString(14), cursor.getString(15));

                attributesList.add(attributes);

            } while (cursor.moveToNext());
        }

        db.close();
        cursor.close();
        // return coefficient list
        return attributesList;
    }


    public void removePlayerFromTbl(String playerName){
        SQLiteDatabase data = this.getWritableDatabase();
        String whereClause = "name =?";
        String whereArgs[] = new String[] {playerName};

        data.delete(TABLE_FUT, whereClause, whereArgs);
        data.close();

    }

    public void deleteAllFromTbl(){
        SQLiteDatabase db = this.getWritableDatabase();

        // Select All Query
        db.delete(TABLE_FUT, null, null);
        db.execSQL("delete from " + TABLE_FUT);

    }




}
