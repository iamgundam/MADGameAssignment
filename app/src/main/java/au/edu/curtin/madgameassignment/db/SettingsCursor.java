package au.edu.curtin.madgameassignment.db;

import android.database.Cursor;
import android.database.CursorWrapper;

public class SettingsCursor extends CursorWrapper
{
    public SettingsCursor(Cursor cursor)
    {
        super(cursor);
    }

    public int[] getSettings()
    {
        int[] settings = new int[4];

        settings[0] = getInt(getColumnIndex(Schema.SettingsTable.Cols.MAPH));
        settings[1] = getInt(getColumnIndex(Schema.SettingsTable.Cols.MAPW));
        settings[2] = getInt(getColumnIndex(Schema.SettingsTable.Cols.INITMON));
        settings[3] = getInt(getColumnIndex(Schema.SettingsTable.Cols.SALARY));

        return settings;
    }
}
