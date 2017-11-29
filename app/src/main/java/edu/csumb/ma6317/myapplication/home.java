package edu.csumb.ma6317.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class home extends AppCompatActivity implements View.OnClickListener {

    private Button goButton;
    private Button profileButton;
    private Spinner langRequestSpin;
    private SeekBar minutesAwaySeekBar;
    private TextView minutesText;
    private int minutesTextNum; // Holds the number of minutes a user would like a translator by
    private int stepMinute;
    private int minMinute;
    private int maxMinute;
    private boolean hasFoundSomeoneAvailable;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mUser;

    public static boolean requestValid;

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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    public int add(int a, int b) {
        return a+b;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.profileButt) {
            Intent intent = new Intent(this, profile.class);
            startActivity(intent);
        }

        else if (v.getId() == R.id.seekButt) {
            // Goes on to MapsActivity if successful, otherwise error
            Intent intent = new Intent(this, MapsActivity.class);
            requestLanguage(intent);
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
        //Should return true if successfully initialized to a default string value (English)
        return (langRequestSpin.getSelectedItem().toString() != null);
    }

    // User selects the language that they need a translator for and gets sent to Firebase
    // Goes on to MapsActivity if successful, otherwise error
    private void requestLanguage(final Intent intent){
        final String reqLanguage = langRequestSpin.getSelectedItem().toString();

        // Read from Firebase database
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // GenericTypeIndicator is needed to retrieve arrays from Firebase
                GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};
                List<String> languages = dataSnapshot.child("users").child(mUser.getUid()).child("languages").getValue(genericTypeIndicator);

                System.out.println("Language status: " + languages.contains(reqLanguage));
                if (languages.contains(reqLanguage)) {
                    requestValid = false;
                    showErrorMessage();
                }
                else {
                    requestValid = true;
                    mDatabase.child("users").child(mUser.getUid()).child("requestLanguage").setValue(reqLanguage);
                    startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // Displays error message if the same language is selected multiple times
    private void showErrorMessage() {
        AlertDialog alertDialog = new AlertDialog.Builder(home.this).create();
        alertDialog.setTitle("Invalid Language Selection");
        String errorMessage = "Please ensure that the requested language is not one you speak.";
        alertDialog.setMessage(errorMessage);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
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
