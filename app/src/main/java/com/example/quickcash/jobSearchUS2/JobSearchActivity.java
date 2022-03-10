package com.example.quickcash.jobSearchUS2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;

public class JobSearchActivity extends AppCompatActivity {
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

        ArrayAdapter<CharSequence> duration_adapter = ArrayAdapter.createFromResource(this, R.array.job_duration_entries,
                android.R.layout.simple_spinner_item);

        duration_adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        job_length_spinner.setAdapter(duration_adapter);


        Button job_search = findViewById(R.id.job_search_button);

        job_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String job_type = job_type_spinner.getSelectedItem().toString();
                String hourly_wages = hourly_wages_spinner.getSelectedItem().toString();
                String distance = distance_spinner.getSelectedItem().toString();
                String job_length = job_length_spinner.getSelectedItem().toString();

                Intent intent = new Intent(JobSearchActivity.this, JobSearchResultsActivity.class);
                intent.putExtra("job_type_key", job_type);
                intent.putExtra("hourly_wages_key", hourly_wages);
                intent.putExtra("distance_key", distance);
                intent.putExtra("job_length_key", job_length);

                startActivity(intent);
            }
        });


    }
}
