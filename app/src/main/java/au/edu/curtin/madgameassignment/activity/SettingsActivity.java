package au.edu.curtin.madgameassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import au.edu.curtin.madgameassignment.GameData;
import au.edu.curtin.madgameassignment.R;
import au.edu.curtin.madgameassignment.Settings;


public class SettingsActivity extends AppCompatActivity
{
    Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        settings = new Settings();
        settings.load(getApplicationContext());

        //Callbacks --------------------------------------------------------------------------------
        final Button applyButton, backButton;
        final EditText heightEntry, widthEntry, moneyEntry, salaryEntry;

        applyButton = (Button)findViewById(R.id.applyButton);
        backButton  = (Button)findViewById(R.id.backButton);
        heightEntry = (EditText)findViewById(R.id.heightEntry);
        widthEntry  = (EditText)findViewById(R.id.widthEntry);
        moneyEntry = (EditText)findViewById(R.id.moneyEntry);
        salaryEntry = (EditText)findViewById(R.id.salaryEntry);

        //------------------------------------------------------------------------------------------

        //Display current settings in fields
        heightEntry.setText(String.valueOf(settings.getMapH()));
        widthEntry.setText(String.valueOf(settings.getMapW()));
        moneyEntry.setText(String.valueOf(settings.getInitialMoney()));
        salaryEntry.setText(String.valueOf(settings.getSalary()));

        /*  Check user input is valid, for
            Height: default 10, from 1 to 20.
            Width:  default 50, from 1 to 100.
            Starting money:  default 1000, from 100 to 10000.
            Salary: default 10, from 1 to 10000.
        */
        heightEntry.addTextChangedListener(new validateTextWatcher(heightEntry, 20, 1));
        widthEntry.addTextChangedListener(new validateTextWatcher(widthEntry, 100, 1));
        moneyEntry.addTextChangedListener(new validateTextWatcher(moneyEntry, 10000, 100));
        salaryEntry.addTextChangedListener(new validateTextWatcher(salaryEntry, 1000, 1));

        applyButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Update settings stored
                settings.setMapH(Integer.parseInt(heightEntry.getText().toString()));
                settings.setMapW(Integer.parseInt(widthEntry.getText().toString()));
                settings.setInitialMoney(Integer.parseInt(moneyEntry.getText().toString()));
                settings.setSalary(Integer.parseInt(salaryEntry.getText().toString()));

                //Notify database and restart game.
                settings.update();
                GameData.get().restartGame(settings.getMapH(), settings.getMapW(), settings.getInitialMoney());

                //Return to title.
                onBackPressed();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Return to title without saving changes.
                onBackPressed();
            }

        });
    }

    //Private inner class for checking each entry field is within an int range.
    public class validateTextWatcher implements TextWatcher
    {
        private boolean valid = false;
        private String previous;
        private EditText attached;
        private int upper, lower;

        public validateTextWatcher(EditText attached, int upper, int lower)
        {
            super();
            this.attached = attached;
            this.upper = upper;
            this.lower = lower;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            //In case input is invalid, save the previous value for reverting to
            previous = attached.getText().toString();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            int current = 1;

            //If text is in box, proceed with checks.
            if(!attached.getText().toString().equals(""))
            {
                current = Integer.parseInt(attached.getText().toString());
                if (current <= upper && current >= lower)
                {
                    valid = true;
                } else
                {
                    valid = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable)
        {
            //If not valid, revert. Else, keep input.
            if(!valid)
            {
                attached.setText(previous);
            }
        }
    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, SettingsActivity.class);
        return i;
    }

}
