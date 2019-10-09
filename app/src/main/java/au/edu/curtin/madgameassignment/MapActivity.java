package au.edu.curtin.madgameassignment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MapActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Fragments
        FragmentManager fm = getSupportFragmentManager();

        //Selector fragment
        Selector fragSel = (Selector)fm.findFragmentById(R.id.selector);
        if(fragSel == null)
        {
            fragSel = new Selector();
            fm.beginTransaction().add(R.id.selector, fragSel).commit();
        }

        //Game map fragment
        MapFragment fragMap    = (MapFragment)fm.findFragmentById(R.id.map);
        if(fragMap == null)
        {
            fragMap = new MapFragment();
            fragMap.setSelector(fragSel);
            fm.beginTransaction().add(R.id.map, fragMap).commit();
        }

    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, MapActivity.class);
        return i;
    }

}
