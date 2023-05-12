package com.example.surveyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;

public class InsertSurveyNumber extends AppCompatActivity {

    private ImageButton buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_survey_number);

        buttonBack = findViewById(R.id.button_back);
        buttonBack.setOnClickListener((view)->{
            finish();
        });
    }
}