package com.example.patientfeedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class DashBoard extends AppCompatActivity {

    ImageButton imageButton_newEntry, imageButton_viewEntry, imageButton_downloadEntries, imageButton_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        imageButton_newEntry = findViewById(R.id.imageButton_new_entry);
        imageButton_viewEntry = findViewById(R.id.imageButton_view_entry);
        imageButton_downloadEntries = findViewById(R.id.imageButton_download_entries);
        imageButton_settings = findViewById(R.id.imageButton_settings);

        imageButton_downloadEntries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), DownloadEntries.class));
            }
        });

        imageButton_newEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Main2Activity.class));
            }
        });

        imageButton_viewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewSubjectData.class));
            }
        });
    }
}
