package edu.csumb.ma6317.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class successMsg extends AppCompatActivity implements View.OnClickListener{

    private TextView etaMinutesTextView;
    private Button rateTranslatorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_msg);

        // Get references to the widgets in activity_success_msg.xml
        etaMinutesTextView = (TextView) findViewById(R.id.textView4);
        rateTranslatorButton = (Button) findViewById(R.id.rateButt);

        // Set the click listeners for the buttons
        rateTranslatorButton.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.rateButt) {
            Toast.makeText(this, "Rate Button Clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, rateCall.class);
            startActivity(intent);
        }
    }
}
