package com.example.quickcash.JobApplicantView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.JobPost;
import com.example.quickcash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewApplicants extends AppCompatActivity implements Serializable {

    Context mContext;
    ListView listView;
    JobPost job ;

    ArrayList<String> applicant_names = new ArrayList<>();
    ArrayList<Long> applicant_ratings = new ArrayList<>();
    ArrayList<JobPost> applicant_history = new ArrayList<>();
    ArrayList<Long> applicant_earned = new ArrayList<>();

    TextView popup_applicant_username;
    TextView popup_applicant_rating;
    TextView popup_applicant_totalEarned;
    ListView popup_jobHistory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicants);

        listView = findViewById(R.id.applicants_listview);

        mContext = this;

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
           job = (JobPost) getIntent().getSerializableExtra("job_key");
        }

        //popup_init();


        getApplicants();


    }

    /*public void popup_init() {
        popup_applicant_username = findViewById(R.id.popup_name);
        popup_applicant_rating = findViewById(R.id.popup_rating);
        popup_applicant_totalEarned = findViewById(R.id.popup_total_earned);
        popup_jobHistory = findViewById(R.id.popup_listview);
    }*/

    public void createApplicantsView(ArrayList<String> applicant_names, ArrayList<Long> applicant_ratings, ArrayList<JobPost> applicant_history, ArrayList<Long> applicant_earned) {

        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        for(int i = 0; i < applicant_names.size(); i++) {
            HashMap<String, String> applicant_item = new HashMap<>();
            applicant_item.put("applicant_name_hash","Applicant Username: " + applicant_names.get(i));
            applicant_item.put("applicant_rating_hash", "Applicant Rating: " + applicant_ratings.get(i));
            if(applicant_ratings.get(i) >= 4.0) {
                applicant_item.put("applicant_recc_hash", "Recommended");
            }
            else {
                applicant_item.put("applicant_recc_hash", "");
            }
            list.add(applicant_item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.applicants_listview_item,
                new String[]{"applicant_name_hash", "applicant_rating_hash", "applicant_recc_hash"}, new int[]{R.id.applicant_username, R.id.applicant_rating, R.id.applicant_recc});

        listView.setAdapter(simpleAdapter);

        attachListeners();
    }

    public void attachListeners() {
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_view_applicant_detail, null);
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
                popupView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        popupWindow.dismiss();
                        return true;
                    }
                });

                Intent intent = new Intent(ViewApplicants.this, ViewApplicantDetails.class);

                intent.putExtra("applicant_names_key", applicant_names);
                intent.putExtra("applicant_ratings_key", applicant_ratings);
                intent.putExtra("applicant_earned_key", applicant_earned);
                intent.putExtra("applicant_history_key", applicant_history);

                startActivity(intent);
            }
        });
    }

    @SuppressLint("NewApi")
    public void createPopupView() {

        Toast.makeText(this, applicant_names.get(0), Toast.LENGTH_SHORT).show();

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

    public void getApplicants(){
        DatabaseReference appRef = FirebaseDatabase.getInstance().getReference("job");
        appRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> applicants = new ArrayList<String>();
                for(DataSnapshot ds : snapshot.getChildren()) {
                    if(ds.child("jobTitle").getValue(String.class).equals(job.getJobTitle())){

                        if(ds.child("userID").getValue(String.class).equals(job.getUserID())){
                            applicants.add(ds.child("application").child("employeeKey").getValue(String.class));
                            getApplicantDetails(applicants);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getApplicantDetails(ArrayList applicants){
        DatabaseReference appDetailsRef = FirebaseDatabase.getInstance().getReference("Account");
        Toast.makeText(getApplicationContext(),(String) applicants.get(0),Toast.LENGTH_LONG).show();
        appDetailsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> applicantNames = new ArrayList<>();
                ArrayList<Long> applicantRating = new ArrayList<>();
                ArrayList<Long> applicantEarned = new ArrayList<>();
                ArrayList<JobPost> applicantJobHistory = new ArrayList<>();
                applicantNames.add(snapshot.child("Employee").child((String) applicants.get(0)).child("username").getValue(String.class));
                applicantRating.add(snapshot.child("Employee").child((String) applicants.get(0)).child("rating").getValue(Long.class));
                applicantEarned.add(snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("totalAmountEarned").getValue(Long.class));
                int numJobs = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("numberOfJobs").getValue(Integer.class);
                int duration;
                double hourlyWage;
                String jobTitle;
                String jobType;
                double latitude;
                double longitude;
                for(int i = 0;i<numJobs;i++){
                    duration = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("duration").getValue(Integer.class);
                    hourlyWage = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("hourlyWage").getValue(double.class);
                    jobTitle = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("jobTitle").getValue(String.class);
                    jobType = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("jobType").getValue(String.class);
                    latitude = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("latitude").getValue(double.class);
                    longitude = snapshot.child("Employee").child((String) applicants.get(0)).child("jobHistory").child("completedJobs").child(String.valueOf(i)).child("longitude").getValue(double.class);
                    applicantJobHistory.add(new JobPost(jobTitle,jobType,hourlyWage,duration,latitude,longitude));
                }
                createApplicantsView(applicantNames, applicantRating, applicantJobHistory, applicantEarned);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
