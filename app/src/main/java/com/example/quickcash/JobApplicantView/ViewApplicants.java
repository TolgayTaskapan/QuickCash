package com.example.quickcash.JobApplicantView;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
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
import android.view.ViewGroup.LayoutParams;

public class ViewApplicants extends AppCompatActivity implements Serializable {

    Context mContext;
    ListView listView;
    JobPost job ;

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

        attachListeners();
        getApplicants();



    }

    public void createApplicantsView(ArrayList<String> applicant_names) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();

        for(int i = 0; i < applicant_names.size(); i++) {
            HashMap<String, String> applicant_item = new HashMap<>();
            applicant_item.put("applicant_name_hash","Applicant Name: " + applicant_names.get(i));
            list.add(applicant_item);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), list, R.layout.applicants_listview_item,
                new String[]{"applicant_name_hash"}, new int[]{R.id.applicant_firstname_lastname});

        listView.setAdapter(simpleAdapter);
    }

    public void attachListeners() {
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.applicant_popup, null);
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
            }
        });
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
                ArrayList<String> applicantNames = new ArrayList<String>();
                applicantNames.add(snapshot.child("Employee").child((String) applicants.get(0)).child("username").getValue(String.class));
                createApplicantsView(applicantNames);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
