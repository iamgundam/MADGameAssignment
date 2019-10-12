package au.edu.curtin.madgameassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import au.edu.curtin.madgameassignment.database.Schema;
import au.edu.curtin.madgameassignment.database.SettingsCursor;
import au.edu.curtin.madgameassignment.database.SettingsDBHelper;

//Built from MAD P04 Local Data, to implement database interactions.
public class Settings
{
    //Database functionality
    private SQLiteDatabase db;

    //Changeable settings
    private int mapW;
    private int mapH;
    private int initialMoney;
    private int salary;

    //Unchangeable
    private static final int familySize        = 4;
    private static final int shopSize          = 6;
    private static final int serviceCost       = 2;
    private static final double tax            = 0.3;
    private static final int houseCost         = 100;
    private static final int commercialCost    = 500;
    private static final int roadCost          = 20;

    //Empty default, load must be called by context
    public Settings()
    { }

    //Fetches settings information from the database
    public void load(Context c)
    {
        //Retrieve and open database
        this.db = new SettingsDBHelper(c.getApplicationContext()).getWritableDatabase();

        //Create cursor, query to retrieve everything from table
        SettingsCursor cursor = new SettingsCursor(
                db.query(Schema.SettingsTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                ));

        //Get settings from cursor and update fields.
        try
        {
            cursor.moveToFirst();

            //'getSettings' returns array of int values relating to the respective
            //class field values. Order is same as declared.
            int[] in = cursor.getSettings();
            this.mapH = in[0];
            this.mapW = in[1];
            this.initialMoney = in[2];
            this.salary = in[3];
        }
        finally
        {
            cursor.close();
        }
    }

    //Updates saved settings by updating the database with current values.
    public void update()
    {
        ContentValues cv = new ContentValues();
        cv.put(Schema.SettingsTable.Cols.MAPH, this.mapH);
        cv.put(Schema.SettingsTable.Cols.MAPW, this.mapW);
        cv.put(Schema.SettingsTable.Cols.INITMON, this.initialMoney);
        cv.put(Schema.SettingsTable.Cols.SALARY,  this.salary);

        //Technically updates ALL rows, sufficient to update our single settings row.
        db.update(Schema.SettingsTable.NAME, cv, null, null);
    }

    //Getters and setters --------------------------------------------------------------------------

    public void setMapH(int mapH)
    {
        this.mapH = mapH;
    }

    public void setMapW(int mapW)
    {
        this.mapW = mapW;
    }

    public void setInitialMoney(int initialMoney)
    {
        this.initialMoney = initialMoney;
    }

    public void setSalary(int salary)
    {
        this.salary = salary;
    }

    public int getMapH()
    {
        return mapH;
    }

    public int getMapW()
    {
        return mapW;
    }

    public int getInitialMoney()
    {
        return initialMoney;
    }

    public int getSalary()
    {
        return salary;
    }

    public int getFamilySize()
    {
        return familySize;
    }

    public int getShopSize()
    {
        return shopSize;
    }

    public int getServiceCost()
    {
        return serviceCost;
    }

    public double getTax()
    {
        return tax;
    }

    public int getHouseCost()
    {
        return houseCost;
    }

    public int getCommercialCost()
    {
        return commercialCost;
    }

    public int getRoadCost()
    {
        return roadCost;
    }
}
