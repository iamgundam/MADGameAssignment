package au.edu.curtin.madgameassignment.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.edu.curtin.madgameassignment.GameData;
import au.edu.curtin.madgameassignment.MapElement;
import au.edu.curtin.madgameassignment.MapFragment;
import au.edu.curtin.madgameassignment.R;

public class InfoActivity extends AppCompatActivity
{
    public static final String ROW = "au.edu.curtin.madgameassignment.activity.row";
    public static final String COL = "au.edu.curtin.madgameassignment.activity.col";
    private static final int REQUEST_THUMBNAIL = 1;

    private int row;
    private int col;
    private Bitmap photo;
    private MapFragment.AdapterMap adapter;

    private TextView typeText;
    private TextView positionText;
    private EditText nameEntry;
    private ImageView structureImage;
    private Button photoButton;
    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Retrieve row and column value from calling context
        Intent intent = getIntent();
        row = intent.getIntExtra(ROW, -1);
        col = intent.getIntExtra(COL, -1);

        //Set callbacks
        typeText          = (TextView)findViewById(R.id.typeText);
        positionText      = (TextView)findViewById(R.id.positionText);
        nameEntry         = (EditText)findViewById(R.id.nameEntry);
        structureImage    = (ImageView)findViewById(R.id.structureImage);
        photoButton       = (Button)findViewById(R.id.photoButton);
        backButton        = (Button)findViewById(R.id.backButton);

        //Get map element in row, col, then retrieve photo where null means no photo
        MapElement current = GameData.get().get(row, col);
        photo = current.getCustomImage();

        //Set structure type, name and position
        typeText.setText(current.getStructure().getLabel());
        nameEntry.setText(current.getOwnerName());
        String position = "Row: "+(row+1)+", Column: "+(col+1); //+1 on numbers to count from 1
        positionText.setText(position);

        //Set custom image if available, otherwise use the standard image
        if(photo != null)
        {
            structureImage.setImageBitmap(photo);
        }
        else
        {
            structureImage.setImageResource(current.getStructure().getDrawableId());
        }

        //Changes the ownerName label of the map element
        nameEntry.addTextChangedListener(new TextWatcher()
        {
            //Valid string if it contains only alphanumeric characters.
            Pattern validEntry = Pattern.compile("^[A-Za-z0-9]*$");
            String previous;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                //In case input is invalid, save the previous value for reverting to
                previous = nameEntry.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                int current = 1;
                boolean valid;

                //If text is in box, proceed with checks.
                if(!nameEntry.getText().toString().equals(""))
                {
                    //Get string input
                    Matcher m = validEntry.matcher(nameEntry.getText().toString());

                    //Revert text if invalid.
                    //String can be up to length 15
                    if(nameEntry.getText().toString().length() >= 15)
                    {
                        nameEntry.setText(previous);
                    }

                    if (!m.matches())
                    {
                        nameEntry.setText(previous);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                //Do nothing.
            }
        });

        //Allow user to take a photo from camera and set it as a thumbnail photo.
        photoButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                MapElement data = GameData.get().get(row, col);

                Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(photoIntent, REQUEST_THUMBNAIL);

                data.setCustomImage(photo);
            }
        });

        //Returns to map and applies any changes.
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Apply changes to ownerName.
                GameData data = GameData.get();
                data.get(row, col).setOwnerName(nameEntry.getText().toString());

                onBackPressed();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultIntent)
    {

        if(resultCode == Activity.RESULT_OK && requestCode == REQUEST_THUMBNAIL)
        {
            MapElement data = GameData.get().get(row, col);
            photo = (Bitmap)resultIntent.getExtras().get("data");

            data.setCustomImage(photo);
            structureImage.setImageBitmap(photo);

            setResult(RESULT_OK);
        }
    }

    public static Intent getIntent(Context c, int row, int col)
    {
        Intent i = new Intent(c, InfoActivity.class);
        i.putExtra(ROW, row);
        i.putExtra(COL, col);

        return i;
    }
}