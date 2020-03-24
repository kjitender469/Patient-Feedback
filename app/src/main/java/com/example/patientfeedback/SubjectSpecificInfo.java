package com.example.patientfeedback;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SubjectSpecificInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_specific_info);

        RecyclerView recyclerView = findViewById(R.id.recycleView_subjectSpecificInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle;
        bundle = getIntent().getExtras();
        List<String> subjectSpecificData = bundle.getStringArrayList("SubjectInfo");
        List<String> info_fields = bundle.getStringArrayList("fields");
        RecyclerView.Adapter adapter = new RecyclerViewAdapter(subjectSpecificData, info_fields, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
