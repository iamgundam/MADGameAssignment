package au.edu.curtin.madgameassignment.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import au.edu.curtin.madgameassignment.db.Schema.SettingsTable;

public class SettingsDBHelper extends SQLiteOpenHelper
{
    private static final int VERSION = 1;
    private static final String DB_NAME = "settings.db";

    public SettingsDBHelper(Context c)
    {
        super(c, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //Builds and executes SQL CREATE TABLE statement
        db.execSQL("CREATE TABLE " + SettingsTable.NAME + "(" +
                    SettingsTable.Cols.MAPH    + " INTEGER, " +
                    SettingsTable.Cols.MAPW    + " INTEGER, " +
                    SettingsTable.Cols.INITMON + " INTEGER, " +
                    SettingsTable.Cols.SALARY  + " INTEGER)" );

        //Inserts default settings into table.
        ContentValues cv = new ContentValues();
        cv.put(SettingsTable.Cols.MAPH, 10);
        cv.put(SettingsTable.Cols.MAPW, 50);
        cv.put(SettingsTable.Cols.INITMON, 1000);
        cv.put(SettingsTable.Cols.SALARY, 10);
        db.insert(SettingsTable.NAME, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2)
    {
        //not implemented, but required on class inherit from SQLiteOpenHelper
    }
}
