package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class home extends AppCompatActivity implements View.OnClickListener {
    private Button profileButton;
    private Button goButton;
    private Spinner langRequestSpin;
    private SeekBar minutesAwaySeekBar;
    private TextView minutesText;
    private int minutesTextNum; // Holds the number of minutes a user would like a translator by
    private int stepMinute;
    private int minMinute;
    private int maxMinute;
    private boolean hasFoundSomeoneAvailable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get references to the widgets in activity_home.xml
        profileButton = (Button) findViewById(R.id.profileButt);
        goButton = (Button) findViewById(R.id.seekButt);
        langRequestSpin = (Spinner) findViewById(R.id.langRequestSpin);
        minutesText = (TextView) findViewById(R.id.info3Txt);
        minutesAwaySeekBar = (SeekBar) findViewById(R.id.minutesAwaySeekBar);


        initializeLangRequestSpinner();
        initializeMinutesAwaySeekBar();

        // Set the click listeners for the buttons
        profileButton.setOnClickListener(this);
        goButton.setOnClickListener(this);

        // For testing, set hasFoundSomeoneAvailable here
        hasFoundSomeoneAvailable = true;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.profileButt) {
            Toast.makeText(this, "Profile Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, profile.class);
            //startActivity(intent);
        }

        else if (v.getId() == R.id.seekButt) {
            //TODO: Display "searching...." view

            //TODO: SUCCESS
            //User B is a translator, is nearby, and clicked accept request
            if (hasFoundSomeoneAvailable){
                Toast.makeText(this, "Found someone! :D", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, successMsg.class);
                startActivity(intent);
            }
            //TODO: FAILURE:
            //User B clicked decline request, or no one nearby
            else if (!hasFoundSomeoneAvailable){
                Toast.makeText(this, "No one nearby. :(", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, failMsg.class);
                startActivity(intent);
            }
        }
    }

    // Initializes the langRequestSpin with the list of languages in values/strings.xml
    private boolean initializeLangRequestSpinner() {
        // Create two ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mainLanguageAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.main_languages_array,
                        android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        mainLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapters to the spinners
        langRequestSpin.setAdapter(mainLanguageAdapter);
        //Should return true if sucessfully initialized to a default string value (English)
        return (langRequestSpin.getSelectedItem().toString() != null);
    }

    // Initializes the SeekBar and allows SeekBar changes to update the radius
    private void initializeMinutesAwaySeekBar() {
        // Read this link to understand the logic of step
        // https://stackoverflow.com/questions/20762001/how-to-set-seekbar-min-and-max-value

        // Set range of the minutesAwaySeekBar here [minMinute, maxMinute]
        minMinute = 5; // starting minute value
        maxMinute = 120; // ending minute value
        stepMinute = 5; // the interval to skip by when user drags seekbar

        minutesAwaySeekBar.setMax( (maxMinute - minMinute) / stepMinute );

        minutesAwaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // Update Minutes Away TextView when user moves SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minutesTextNum = minMinute + (progress * stepMinute);
                if (minutesTextNum < 60) {
                    minutesText.setText("Within The Next " + minutesTextNum + " Minutes");
                }
                else if (minutesTextNum == 60) {
                    minutesText.setText("Within The Next 1 Hour");
                }
                else if (minutesTextNum > 60 && minutesTextNum < 120) {
                    int hrToMin;
                    hrToMin = minutesTextNum - 60;
                    minutesText.setText("Within The Next 1 Hour and " + hrToMin + " Minutes");
                }
                else if (minutesTextNum == 120) {
                    minutesText.setText("Within The Next 2 Hours");
                }
            }

            // Overloaded to do nothing, but needs to be included with SeekBar
            public void onStartTrackingTouch(SeekBar seekBar) {} //Notification that the user has started a touch gesture.
            public void onStopTrackingTouch(SeekBar seekBar) {} //Notification that the user has finished a touch gesture.

        });
    }
}
