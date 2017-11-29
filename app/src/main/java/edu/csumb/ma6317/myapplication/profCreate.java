package edu.csumb.ma6317.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class profCreate extends AppCompatActivity implements View.OnClickListener {

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    // Spinner variables
    private Spinner mainLanguageSpinner;
    private Spinner altLanguage1Spinner;
    private Spinner altLanguage2Spinner;
    private Spinner altLanguage3Spinner;

    private TextView radiusText;
    private SeekBar radiusSeekBar;

    private int radiusTextNum;   // Holds user's desired search radius (in miles)
    private int stepRadius;
    private int minRadius;
    private int maxRadius;

    // Button variables
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_create);

        // Initialize Firebase components
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get references to the widgets in activity_prof_create.xml
        mainLanguageSpinner = (Spinner) findViewById(R.id.mainLangSpin);
        altLanguage1Spinner = (Spinner) findViewById(R.id.altLang1Spin);
        altLanguage2Spinner = (Spinner) findViewById(R.id.altLang2Spin);
        altLanguage3Spinner = (Spinner) findViewById(R.id.altLang3Spin);
        radiusText = (TextView) findViewById(R.id.info4Txt);
        radiusSeekBar = (SeekBar) findViewById(R.id.mileRadiusSB);
        continueButton = findViewById(R.id.submitButt);

        // Set the click listeners for the button
        continueButton.setOnClickListener(this);

        // Initialize form elements
        initializeSpinners();
        initializeSeekBar();
    }

    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            // Verify user input

            boolean inputValid = validateInput();

            if (inputValid == true) {
                sendProfileToDatabase();
                Toast.makeText(this, "Profile created successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, home.class);
                startActivity(intent);
            }
            else {
                showErrorMessage();
            }

        }
    }

    // Initializes the SeekBar and allows SeekBar changes to update the radius
    private void initializeSeekBar() {

        // Read this link to understand the logic of step
        // https://stackoverflow.com/questions/20762001/how-to-set-seekbar-min-and-max-value

        // Set range of the minutesAwaySeekBar here [minMinute, maxMinute]
        minRadius = 10; // lower bound radius miles value
        maxRadius = 100; // upper bound radius miles value
        stepRadius = 10; // the interval to skip by when user drags seekbar
        radiusTextNum = minRadius;
        radiusSeekBar.setMax( (maxRadius - minRadius) / stepRadius );

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // Update radius TextView when user moves SeekBar
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                radiusTextNum = minRadius + (progress * stepRadius);
                radiusText.setText("I seek translators within " + radiusTextNum + " miles");
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    // Initializes data to language Spinners (drop-down menus)
    private void initializeSpinners() {


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

    // Retrieve data from widgets and send to Firebase Database
    private boolean sendProfileToDatabase() {
        // Retrieve languages from Spinners
        String mainLanguage = mainLanguageSpinner.getSelectedItem().toString();
        String altLanguage1 = altLanguage1Spinner.getSelectedItem().toString();
        String altLanguage2 = altLanguage2Spinner.getSelectedItem().toString();
        String altLanguage3 = altLanguage3Spinner.getSelectedItem().toString();
        List<String> userLanguages = new ArrayList<String>();
        userLanguages.add(mainLanguage);

        // Only add languages that the user selected
        if (!altLanguage1.isEmpty()) { userLanguages.add(altLanguage1); }
        if (!altLanguage2.isEmpty()) { userLanguages.add(altLanguage2); }
        if (!altLanguage3.isEmpty()) { userLanguages.add(altLanguage3); }

        // Retrieve translator value from Switch
        Switch translatorSwitch = (Switch) findViewById(R.id.translatorSwitch);
        boolean isTranslator = false;
        if (translatorSwitch.isChecked()) {
            isTranslator = true;
        }

        // Set values in database
        if (mAuth.getCurrentUser() != null) {
            String uid = mUser.getUid();
            String email = mUser.getEmail();
            String displayName = mUser.getDisplayName();

            mDatabase.child("users").child(uid).child("displayName").setValue(displayName);
            mDatabase.child("users").child(uid).child("ratingAverage").setValue(5);
            mDatabase.child("users").child(uid).child("ratingSum").setValue(5);
            mDatabase.child("users").child(uid).child("ratingCount").setValue(1);
            mDatabase.child("users").child(uid).child("languages").setValue(userLanguages);
            mDatabase.child("users").child(uid).child("isTranslator").setValue(isTranslator);
            mDatabase.child("users").child(uid).child("radius").setValue(radiusTextNum);
        }

        return true;
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