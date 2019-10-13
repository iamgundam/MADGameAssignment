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
    private GameData game;

    //Game status fields
    int money;
    int population;
    double employmentRate;
    int income;

    //Callbacks
    private Button demolishButton, infoButton, timeButton;
    private TextView timeText, populationText, moneyText,  employmentText, incomeText, overText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        String insert;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //Callbacks
        demolishButton  = (Button)findViewById(R.id.demolishButton);
        infoButton      = (Button)findViewById(R.id.infoButton);
        timeButton      = (Button)findViewById(R.id.timeButton);
        timeText        = (TextView)findViewById(R.id.timeText);
        populationText  = (TextView)findViewById(R.id.populationText);
        moneyText       = (TextView)findViewById(R.id.moneyText);
        employmentText  = (TextView)findViewById(R.id.employmentText);
        incomeText      = (TextView)findViewById(R.id.incomeText);
        overText        = (TextView)findViewById(R.id.overText);

        //Fragments
        FragmentManager fm = getSupportFragmentManager();

        //Selector fragment
        fragSel = (Selector)fm.findFragmentById(R.id.selector);
        if(fragSel == null)
        {
            fragSel = new Selector();
            fm.beginTransaction().add(R.id.selector, fragSel).commit();
        }

        //Game game fragment
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

        game = GameData.get();
        if(game.isOver())
        {
            overText.setVisibility(View.VISIBLE);
        }

        //Set values for status display
        updateTime();
        updateResidential(0);
        updateMoney(0);
        updateCommercial(0);
        updateIncome();

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

        timeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                updateTime();
            }
        });
    }

    public void updateTime()
    {
        String insert;

        game.setTime(game.getTime()+1);
        insert = "Time: "+game.getTime();
        timeText.setText(insert);

        //If money is less than 0 after timestep, game over!
        updateMoney(income);
        if(money < 0)
        {
            money = 0;
            game.setMoney(0);
            game.setOver(true);
            overText.setVisibility(View.VISIBLE);
        }
    }

    public void updateMoney(int num)
    {
        String insert;

        game.setMoney(game.getMoney()+num);
        money = game.getMoney();

        insert = "Money: " + money;
        moneyText.setText(insert);
    }

    public void updateResidential(int num)
    {
        String insert;

        game.setnResidential(game.getnResidential()+num);
        population = game.getnResidential()*settings.getFamilySize();
        insert = "Population: " + population;
        populationText.setText(insert);

        //Population changed, must update employment and income
        updateCommercial(0);
        updateIncome();
    }

    public void updateCommercial(int num)
    {
        String insert;

        game.setnCommercial(game.getnCommercial()+num);
        if(game.getnResidential() > 0 && game.getnCommercial() > 0)
        {
            //minimum(1, nCommercial*shopSize/population), calculated in real numbers
            employmentRate =  Math.min(1, (double)game.getnCommercial() *
                                          (double)settings.getShopSize() / (double)population);

            insert = "Employment: " + employmentRate;
        }
        else
        {
            insert = "Employment: 0";
            employmentRate = 0.0;
        }
        employmentText.setText(insert);

        //Employment rate changed, must update income
        updateIncome();
    }

    public void updateIncome()
    {
        String insert;
        double calc;

        //income = population * (employmentRate * salary * taxRate - serviceCost)
        calc = (double)population*(employmentRate*(double)settings.getSalary()*settings.getTax() -
                (double)settings.getServiceCost());

        income = (int)Math.round(calc);
        insert = "Income: "+income;
        incomeText.setText(insert);
    }

    public boolean hasMoney(int cost)
    {
        boolean result = true;
        if(cost > money)
        {
            result = false;
        }
        return result;
    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, MapActivity.class);
        return i;
    }

}
