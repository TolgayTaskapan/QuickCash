package com.example.quickcash.JobApplicantView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPost;
import com.example.quickcash.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewApplicantDetails extends AppCompatActivity {

    Context mContext;

    ArrayList<String> applicant_names = new ArrayList<>();
    ArrayList<Long> applicant_ratings = new ArrayList<>();
    ArrayList<JobPost> applicant_history = new ArrayList<>();
    ArrayList<Long> applicant_earned = new ArrayList<>();

    TextView popup_applicant_username;
    TextView popup_applicant_rating;
    TextView popup_applicant_totalEarned;
    ListView popup_jobHistory;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicant_detail);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            applicant_names = getIntent().getStringArrayListExtra("applicant_names_key");
            applicant_ratings = (ArrayList<Long>) getIntent().getSerializableExtra("applicant_ratings_key");
            applicant_history = (ArrayList<JobPost>) getIntent().getSerializableExtra("applicant_history_key");
            applicant_earned = (ArrayList<Long>) getIntent().getSerializableExtra("applicant_earned_key");
        }

        /*popup_applicant_username = findViewById(R.id.popup_name);
        popup_applicant_rating = findViewById(R.id.popup_rating);
        popup_applicant_totalEarned = findViewById(R.id.popup_total_earned);
        popup_jobHistory = findViewById(R.id.popup_listview); */

        //Toast.makeText(this, applicant_names.get(0), Toast.LENGTH_SHORT).show();

        popup_applicant_username.setText(applicant_names.get(0));
        popup_applicant_rating.setText(Math.toIntExact(applicant_ratings.get(0)));
        popup_applicant_totalEarned.setText(Math.toIntExact(applicant_earned.get(0)));


        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        for(int i = 0; i < applicant_history.size(); i++) {
            HashMap<String, String> popup_item = new HashMap<>();

            popup_item.put("job_history_title_hash","Job Title: " + applicant_history.get(i).getJobTitle());
            popup_item.put("job_history_type_hash", "Job Type: " + applicant_history.get(i).getJobType());
            popup_item.put("job_history_wage_hash", "Hourly Wage: " + applicant_history.get(i).getHourlyWage());

            list.add(popup_item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.popup_listview_layout,
                new String[]{"job_history_title_hash", "job_history_type_hash", "job_history_wage_hash"}, new int[]{R.id.job_history_title, R.id.job_history_type, R.id.job_history_wage});

        popup_jobHistory.setAdapter(simpleAdapter);

    }
}
