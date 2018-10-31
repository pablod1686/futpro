package Databases;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import FUT.FUTPlayerAttributes;

/**
 * Created by jpablo09 on 10/16/2018.
 */

public class DatabaseHelperII extends SQLiteOpenHelper {


    // Logcat tag
    private static final String LOG = "DatabaseHelperII";

    // Database Version
    private static final int DATABASE_VERSION = 5;

    // Database Name
    private static final String DATABASE_NAME = "LineUpDatabase";

    // Table Names
    private static final String TABLE_TEAM= "FUT_LINEUP_TBL";

    // Common column names
    private static final String KEY_ID = "player_id";


    private static final String TAG_KEY = "playerKey";
    private static final String TAG_GAMERTAG = "gamerTag";
    private static final String TAG_FORMATION = "formation";
    private static final String TAG_FORMATION_POS = "formPos";
    private static final String TAG_POS_NUMBER = "posNum";
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

    public DatabaseHelperII(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_TEAM + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +  TAG_KEY + " TEXT," + TAG_GAMERTAG + " TEXT," + TAG_FORMATION + " TEXT," + TAG_FORMATION_POS +  " TEXT,"
                + TAG_POS_NUMBER + " INTEGER," + TAG_NAME + " TEXT," + TAG_RTG + " TEXT," + TAG_POS +  " TEXT,"
                + TAG_CLUB +  " TEXT," + TAG_LEAGUE +  " TEXT," + TAG_NATION +  " TEXT,"
                + TAG_PACE +  " TEXT," + TAG_SHOT +  " TEXT," + TAG_PASS +  " TEXT," + TAG_DRIBBLE +  " TEXT,"
                + TAG_DEFENSE +  " TEXT," + TAG_PHYSICAL +  " TEXT," + TAG_WEAKFOOT +  " TEXT," + TAG_SKILL +  " TEXT" + ")";


        // creating required tables
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAM);


        // create new tables
        onCreate(db);
    }

    public void addLineUpPlayers(FUTPlayerAttributes attributes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(TAG_KEY, attributes.getKey());
        values.put(TAG_GAMERTAG, attributes.getFormation());
        values.put(TAG_FORMATION, attributes.getFormation());
        values.put(TAG_FORMATION_POS, attributes.getFormPos());
        values.put(TAG_POS_NUMBER, attributes.getPosNum());
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
        db.insert(TABLE_TEAM, null, values);
        db.close(); // Closing database connection

    }

    public List<FUTPlayerAttributes> getLineUpPlayers() {
        List<FUTPlayerAttributes> attributesList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_TEAM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                FUTPlayerAttributes attributes = new FUTPlayerAttributes(cursor.getString(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9),
                        cursor.getString(10), cursor.getString(11), cursor.getString(12),cursor.getString(13),
                        cursor.getString(14) ,cursor.getString(15), cursor.getString(16),  cursor.getString(17),  cursor.getString(18), cursor.getString(19));

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

        data.delete(TABLE_TEAM, whereClause, whereArgs);
        data.close();

    }

    public void deleteAllFromTbl2(){
        SQLiteDatabase db = this.getWritableDatabase();

        // Select All Query
        db.delete(TABLE_TEAM, null, null);
        db.execSQL("delete from " + TABLE_TEAM);

    }




}
