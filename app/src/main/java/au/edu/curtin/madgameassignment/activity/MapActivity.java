package au.edu.curtin.madgameassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import au.edu.curtin.madgameassignment.GameData;
import au.edu.curtin.madgameassignment.MapFragment;
import au.edu.curtin.madgameassignment.R;
import au.edu.curtin.madgameassignment.Selector;
import au.edu.curtin.madgameassignment.Settings;


public class MapActivity extends AppCompatActivity
{
    private Selector fragSel;
    private MapFragment fragMap;
    private Settings settings;
    private GameData map;

    //Game status fields
    int money;
    int population;

    //Callbacks
    private Button demolishButton, infoButton;
    private TextView timeText, populationText, moneyText,  employmentText, incomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String insert;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //Callbacks
        demolishButton  = (Button)findViewById(R.id.demolishButton);
        infoButton      = (Button)findViewById(R.id.infoButton);
        timeText        = (TextView)findViewById(R.id.timeText);
        populationText  = (TextView)findViewById(R.id.populationText);
        moneyText       = (TextView)findViewById(R.id.moneyText);
        employmentText  = (TextView)findViewById(R.id.employmentText);
        incomeText      = (TextView)findViewById(R.id.incomeText);

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

        //Retrieve settings to start game
        settings = new Settings();
        settings.load(getApplicationContext());

        map = GameData.get();

        //Time


        //Population


        //Money
        money = map.getMoney();
        insert = "Money: " + String.valueOf(money);
        moneyText.setText(insert);

        //Employment


        //Income


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

    public void spend(int cost)
    {
        String insert;

        money = (money - cost);
        map.setMoney(money);
        insert = "Money: " + money;
        moneyText.setText(insert);
    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, MapActivity.class);
        return i;
    }

}
