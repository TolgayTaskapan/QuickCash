package com.example.quickcash.jobSearchUS2;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

public class developmentJobSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_development_job_search);

        Spinner job_type_spinner = findViewById(R.id.job_type_list);
        Spinner hourly_wages_spinner = findViewById(R.id.hourly_wages_list);
        Spinner distance_spinner = findViewById(R.id.distance_list);
        Spinner job_length_spinner = findViewById(R.id.job_length_list);


        ArrayAdapter<CharSequence> job_type_adapter = ArrayAdapter.createFromResource(this, R.array.job_type_entries,
                android.R.layout.simple_spinner_item);

        job_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        job_type_spinner.setAdapter(job_type_adapter);

        ArrayAdapter<CharSequence> hourly_wages_adapter = ArrayAdapter.createFromResource(this, R.array.hourly_wages_entries,
                android.R.layout.simple_spinner_item);

        hourly_wages_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        hourly_wages_spinner.setAdapter(hourly_wages_adapter);

        ArrayAdapter<CharSequence> distance_adapter = ArrayAdapter.createFromResource(this, R.array.distance_entries,
                android.R.layout.simple_spinner_item);

        distance_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        distance_spinner.setAdapter(distance_adapter);

        
    }
}
