package au.edu.curtin.madgameassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madgameassignment.MapFragment;
import au.edu.curtin.madgameassignment.R;
import au.edu.curtin.madgameassignment.Selector;


public class MapActivity extends AppCompatActivity
{
    private Selector fragSel;
    private MapFragment fragMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Callbacks
        final Button demolishButton = (Button)findViewById(R.id.demolishButton);
        final Button infoButton = (Button)findViewById(R.id.infoButton);

        //Fragments
        FragmentManager fm = getSupportFragmentManager();

        //Selector fragment
        fragSel = (Selector)fm.findFragmentById(R.id.selector);
        if(fragSel == null)
        {
            fragSel = new Selector();
            fm.beginTransaction().add(R.id.selector, fragSel).commit();
        }

        //Game map fragment
        fragMap    = (MapFragment)fm.findFragmentById(R.id.map);
        if(fragMap == null)
        {
            fragMap = new MapFragment();
            fragMap.setSelector(fragSel);
            fm.beginTransaction().add(R.id.map, fragMap).commit();
        }

        demolishButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int choice = fragMap.getChoice();

                //If choice is already on demolish, return to build mode.
                if(choice == MapFragment.DEMOLISH)
                {
                    fragMap.setChoice(MapFragment.BUILD);
                    demolishButton.setBackgroundColor(Color.LTGRAY);
                }
                //Else, set mode to demolish.
                else
                {
                    //If switching from info to demolish, change info colour to deactivate.
                    if(choice == MapFragment.INFO)
                    {
                        infoButton.setBackgroundColor(Color.LTGRAY);
                    }

                    fragMap.setChoice(MapFragment.DEMOLISH);
                    demolishButton.setBackgroundColor(Color.RED);

                }
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int choice = fragMap.getChoice();

                //If choice is already on info, return to build mode.
                if(choice == MapFragment.INFO)
                {
                    fragMap.setChoice(MapFragment.BUILD);
                    infoButton.setBackgroundColor(Color.LTGRAY);
                }
                //Else, set mode to info.
                else
                {
                    //If switching from demolish to info, change demolish colour to deactivate.
                    if(choice == MapFragment.DEMOLISH)
                    {
                        demolishButton.setBackgroundColor(Color.LTGRAY);
                    }

                    fragMap.setChoice(MapFragment.INFO);
                    infoButton.setBackgroundColor(Color.YELLOW);
                }
            }
        });

    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, MapActivity.class);
        return i;
    }

}
