package au.edu.curtin.madgameassignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class SettingsActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        //Callbacks --------------------------------------------------------------------------------
        final Button applyButton, backButton;
        final TextView heightText, widthText, moneyText, salaryText;
        final EditText heightEntry, widthEntry, moneyEntry, salaryEntry;

        applyButton = (Button)findViewById(R.id.applyButton);
        backButton  = (Button)findViewById(R.id.backButton);
        heightText  = (TextView)findViewById(R.id.heightText);
        widthText   = (TextView)findViewById(R.id.widthText);
        moneyText   = (TextView)findViewById(R.id.moneyText);
        salaryText  = (TextView)findViewById(R.id.salaryText);
        heightEntry = (EditText)findViewById(R.id.heightEntry);
        widthEntry  = (EditText)findViewById(R.id.widthEntry);
        moneyEntry = (EditText)findViewById(R.id.moneyEntry);
        salaryEntry = (EditText)findViewById(R.id.salaryEntry);

        //------------------------------------------------------------------------------------------

        heightEntry.addTextChangedListener(new TextWatcher()
        {
            boolean valid = false;
            String previous;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //In case input is invalid, save the previous value for reverting to
                previous = heightEntry.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                int current = 1;

                //If text is in box, proceed with checks.
                if(!heightEntry.getText().toString().equals(""))
                {
                    current = Integer.parseInt(heightEntry.getText().toString());
                    if (current <= 30 && current > 0)
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
                    heightEntry.setText(previous);
                }
            }
        });
    }

    public static Intent getIntent(Context c)
    {
        Intent i = new Intent(c, SettingsActivity.class);
        return i;
    }

}
