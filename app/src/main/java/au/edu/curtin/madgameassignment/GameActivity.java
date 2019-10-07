package au.edu.curtin.madgameassignment;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GameActivity extends AppCompatActivity
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
        GameMap fragMap    = (GameMap)fm.findFragmentById(R.id.map);
        if(fragMap == null)
        {
            fragMap = new GameMap();
            fragMap.setSelector(fragSel);
            fm.beginTransaction().add(R.id.map, fragMap).commit();
        }

    }

    public static Intent getIntent(Context c)
    {
        Intent intent = new Intent(c, GameActivity.class);
        return intent;
    }

}
