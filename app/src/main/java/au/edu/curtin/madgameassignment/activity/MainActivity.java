package au.edu.curtin.madgameassignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madgameassignment.GameData;
import au.edu.curtin.madgameassignment.R;
import au.edu.curtin.madgameassignment.Settings;

public class MainActivity extends AppCompatActivity
{
    private Button mapButton, settingsButton;
    private Settings settings;
    private boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapButton = (Button)findViewById(R.id.mapButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);

        settings = new Settings();
        settings.load(getApplicationContext());

        isStarted = false;
        mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                GameData game = GameData.get();
                if(false) //saved game state from previous session
                {

                }
                else
                {
                    //Load from stored settings or update from new settings
                    if(!isStarted || game.hasUpdate())
                    {
                        //New game from stored settings
                        game.restartGame(settings.getMapH(), settings.getMapW(), settings.getInitialMoney());
                    }
                }
                startActivity(MapActivity.getIntent(MainActivity.this));
                isStarted = true;
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(SettingsActivity.getIntent(MainActivity.this));
            }
        });

    }
}
