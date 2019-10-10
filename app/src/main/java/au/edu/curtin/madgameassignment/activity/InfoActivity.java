package au.edu.curtin.madgameassignment.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import au.edu.curtin.madgameassignment.MapData;
import au.edu.curtin.madgameassignment.MapElement;
import au.edu.curtin.madgameassignment.R;

public class InfoActivity extends AppCompatActivity
{
    private static final String ROW = "au.edu.curtin.madgameassignment.activity.row";
    private static final String COL = "au.edu.curtin.madgameassignment.activity.col";

    private int row;
    private int col;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Callbacks
        TextView typeText        = (TextView)findViewById(R.id.typeText);
        TextView positionText    = (TextView)findViewById(R.id.positionText);
        EditText nameEntry       = (EditText)findViewById(R.id.nameEntry);
        ImageView structureImage = (ImageView)findViewById(R.id.structureImage);
        Button photoButton       = (Button)findViewById(R.id.photoButton);
        Button backButton        = (Button)findViewById(R.id.backButton);

        //Retrieve row and column value from calling context
        Intent intent = getIntent();
        row = intent.getIntExtra(ROW, -1);
        col = intent.getIntExtra(COL, -1);

        //Get map element in row, col
        MapElement current = MapData.get().get(row, col);

        //Set structure type, name and position
        typeText.setText(current.getStructure().getLabel());
        nameEntry.setText(current.getOwnerName());
        String position = "Row: "+(row+1)+", Column: "+(col+1); //+1 on numbers to count from 1
        positionText.setText(position);

        //Set custom image if available, otherwise use the standard image
        if(current.getCustomImage() != null)
        {
            structureImage.setImageBitmap(current.getCustomImage());
        }
        else
        {
            structureImage.setImageResource(current.getStructure().getDrawableId());
        }

        //Simple return to map activity button.
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

    }

    public static Intent getIntent(Context c, int row, int col)
    {
        Intent i = new Intent(c, InfoActivity.class);
        i.putExtra(ROW, row);
        i.putExtra(COL, col);

        return i;
    }
}