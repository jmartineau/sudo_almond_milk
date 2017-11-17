package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class profCreate extends AppCompatActivity implements View.OnClickListener {

    // Button variables
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_create);

        // Set the click listeners for the button
        continueButton = findViewById(R.id.submitButt);
        continueButton.setOnClickListener(this);

        // Initialize spinner menus
        Spinner mainLanguage = (Spinner) findViewById(R.id.mainLangSpin);
        Spinner altLanguage1 = (Spinner) findViewById(R.id.altLang1Spin);
        Spinner altLanguage2 = (Spinner) findViewById(R.id.altLang2Spin);
        Spinner altLanguage3 = (Spinner) findViewById(R.id.altLang3Spin);

        // Create two ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mainLanguageAdapter = ArrayAdapter.createFromResource(this,
                R.array.main_languages_array, android.R.layout.simple_spinner_item);

        ArrayAdapter<CharSequence> altLanguageAdapter = ArrayAdapter.createFromResource(this,
                R.array.alt_languages_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        mainLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        altLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        mainLanguage.setAdapter(mainLanguageAdapter);
        altLanguage1.setAdapter(altLanguageAdapter);
        altLanguage2.setAdapter(altLanguageAdapter);
        altLanguage3.setAdapter(altLanguageAdapter);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            // User is signed in
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}

