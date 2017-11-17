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
        Spinner languageSpinner = (Spinner) findViewById(R.id.mainLangSpin);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.languages_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        languageSpinner.setAdapter(adapter);

    }

    public void onClick(View v) {
        if (v.getId() == R.id.submitButt) {
            // User is signed in
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}

