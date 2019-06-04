package com.example.beequiet1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button select_button;
    TextView coords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        select_button = (Button) findViewById(R.id.select_btn);
        coords = (TextView) findViewById(R.id.coord_view);
        select_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setActivity();
            }
        });
        Bundle intentData = getIntent().getExtras();
        if (intentData != null) {
            String value = intentData.getString("coordinates");
            coords.setText(value);
        }

    }

    private void setActivity() {
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }
}
