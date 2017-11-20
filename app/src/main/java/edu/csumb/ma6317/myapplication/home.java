package edu.csumb.ma6317.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class home extends AppCompatActivity {

    private Spinner langRequestSpin;
    private SeekBar minutesAwaySeekBar;
    private TextView minutesText;
    private int minutesTextNum; // Holds the number of minutes a user would like a translator by

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get references to the widgets in activity_home.xml
        langRequestSpin = (Spinner) findViewById(R.id.langRequestSpin);
        minutesAwaySeekBar = (SeekBar) findViewById(R.id.minutesAwaySeekBar);
        minutesText = (TextView) findViewById(R.id.info3Txt);

        initializeLangRequestSpinner();
        initializeMinutesAwaySeekBar();
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
        minutesAwaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Update radius TextView when user moves SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                minutesTextNum = progress + 5;   // progress starts at 0, so start radius at 10 miles
                if (minutesTextNum < 60) {
                    minutesText.setText("Within The Next " + minutesTextNum + " Minutes");
                }
                else if (minutesTextNum == 60) {
                    minutesText.setText("Within The Next Hour");
                }
                else if (minutesTextNum > 60) {
                    int hrToMin;
                    hrToMin = minutesTextNum - 60;
                    minutesText.setText("Within The Next Hour and " + hrToMin + " Minutes");
                }

            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }
}
