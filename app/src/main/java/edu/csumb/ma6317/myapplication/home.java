package edu.csumb.ma6317.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class home extends AppCompatActivity {

    private Spinner langRequestSpin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get references to the widgets in activity_home.xml
        langRequestSpin = (Spinner) findViewById(R.id.langRequestSpin);

        // Create two ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> mainLanguageAdapter =
                ArrayAdapter.createFromResource(this,
                                                R.array.main_languages_array,
                                                android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        mainLanguageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapters to the spinners
        langRequestSpin.setAdapter(mainLanguageAdapter);
    }
}
