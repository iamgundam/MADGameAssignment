package au.edu.curtin.madgameassignment.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import au.edu.curtin.madgameassignment.R;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mapButton, settingsButton;

        mapButton = (Button)findViewById(R.id.mapButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);


        mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(MapActivity.getIntent(MainActivity.this));
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
