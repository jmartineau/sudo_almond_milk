package edu.csumb.ma6317.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class profCreate extends AppCompatActivity implements View.OnClickListener {

    // Button variables
    private Button continueButton;

    // Spinner variables
    private Spinner mainLanguageSpinner;
    private Spinner altLanguage1Spinner;
    private Spinner altLanguage2Spinner;
    private Spinner altLanguage3Spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_create);

        // Set the click listeners for the button
        continueButton = findViewById(R.id.submitButt);
        continueButton.setOnClickListener(this);

        initializeSpinners();
        initializeSeekBar();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            // User is signed in
            //Intent intent = new Intent(this, MapsActivity.class);
            //startActivity(intent);
            boolean inputValid = validateInput();

            if (inputValid == true) {

            }
            else {
                showErrorMessage();
            }

        }
    }

    // Initializes the SeekBar and allows SeekBar changes to update the radius
    private void initializeSeekBar() {
        SeekBar radiusSeekBar = (SeekBar) findViewById(R.id.mileRadiusSB);
        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            // Update radius TextView when user moves SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                TextView radiusText = (TextView) findViewById(R.id.info4Txt);
                int distance = progress + 10;   // progress starts at 0, so start radius at 10 miles
                radiusText.setText("I seek translators within " + distance + " miles");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // Initializes data to language spinners (drop-down menus)
    private void initializeSpinners() {
        // Initialize spinner menus
        mainLanguageSpinner = (Spinner) findViewById(R.id.mainLangSpin);
        altLanguage1Spinner = (Spinner) findViewById(R.id.altLang1Spin);
        altLanguage2Spinner = (Spinner) findViewById(R.id.altLang2Spin);
        altLanguage3Spinner = (Spinner) findViewById(R.id.altLang3Spin);

        // Create two ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mainLanguageAdapter = ArrayAdapter.createFromResource(this,
                R.array.main_languages_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> altLanguageAdapter = ArrayAdapter.createFromResource(this,
                R.array.alt_languages_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        mainLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        altLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        mainLanguageSpinner.setAdapter(mainLanguageAdapter);
        altLanguage1Spinner.setAdapter(altLanguageAdapter);
        altLanguage2Spinner.setAdapter(altLanguageAdapter);
        altLanguage3Spinner.setAdapter(altLanguageAdapter);
    }

    // Displays error message if the same language is selected multiple times
    private void showErrorMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(profCreate.this).create();
        alertDialog.setTitle("Invalid Language Selection");
        String errorMessage = "Please ensure that no duplicate languages have been selected.";
        alertDialog.setMessage(errorMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    // Ensures Spinner input is correct
    private boolean validateInput() {
        String mainLanguage = mainLanguageSpinner.getSelectedItem().toString();
        String altLanguage1 = altLanguage1Spinner.getSelectedItem().toString();
        String altLanguage2 = altLanguage2Spinner.getSelectedItem().toString();
        String altLanguage3 = altLanguage3Spinner.getSelectedItem().toString();

        // Ensure no language is selected multiple times
        if (altLanguage1.isEmpty()){
            // don't compare language choices if left blank
        }
        else {
            if (altLanguage1.equals(mainLanguage) || altLanguage1.equals(altLanguage2)
                    || altLanguage1.equals(altLanguage3)) {
                return false;
            }
        }

        if (altLanguage2.isEmpty()) {
            // don't compare language choices if left blank
        }
        else {
            if (altLanguage2.equals(mainLanguage) || altLanguage2.equals(altLanguage3)) {
                return false;
            }
        }

        if (altLanguage3.isEmpty()) {
            // don't compare language choices if left blank
        }
        else {
            if (altLanguage3.equals(mainLanguage)) {
                return false;
            }
        }

        // Spinner input is valid
        return true;
    }
}