package com.example.quickcash.ui.jobs;

import android.app.Activity;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quickcash.JobApplication;
import com.example.quickcash.JobApplicantView.ViewApplicants;

import com.example.quickcash.JobPost;
import com.example.quickcash.JobRequest;
import com.example.quickcash.R;
import com.example.quickcash.util.UserSession;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.LinkedList;

public class JobAdapter extends BaseAdapter {
    private LinkedList<JobPost> mJob;
    private Context mContext;
    private LinkedList<String> mJobKey;
    private DatabaseReference database;
    private Activity activity;

    public JobAdapter(LinkedList<JobPost> mJob, LinkedList<String> mJobKey, DatabaseReference database, Context mContext, Activity activity) {
        this.mJob = mJob;
        this.mContext = mContext;
        this.mJobKey = mJobKey;
        this.database = database;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mJob.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (mJob.get(position).getJobState().equals(JobPost.JOB_PENDING) && mJob.get(position).getApplication().getEmployeeKey().equals(UserSession.getInstance().getUsrID())) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee, parent, false);
            TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
            TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
            TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);
            TextView jobStatus = (TextView) convertView.findViewById(R.id.job_status);
            Button applyBtn = convertView.findViewById(R.id.applyBtn);
            String currentJobID = mJobKey.get(position);
            JobPost currentJob = mJob.get(position);

            txtJobTitle.setText(mJob.get(position).getJobTitle());
            txtJobCategory.setText(mJob.get(position).getJobType());
            jobStatus.setText("Pending Approval from Employer");
            String wageStr = "$" + mJob.get(position).getHourlyWage();
            txtJobWage.setText(wageStr);
            applyBtn.setVisibility(View.GONE);
            return convertView;
        } else if (mJob.get(position).getJobState().equals(JobPost.JOB_IN_PROGRESS)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee, parent, false);
            TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
            TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
            TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);
            TextView jobStatus = (TextView) convertView.findViewById(R.id.job_status);
            Button applyBtn = convertView.findViewById(R.id.applyBtn);
            Button completeBtn = convertView.findViewById(R.id.markCompleteBtn);
            String currentJobID = mJobKey.get(position);
            JobPost currentJob = mJob.get(position);
            EditText ratingET = (EditText) convertView.findViewById(R.id.rateEmployerET);


            //ratingET.setVisibility(View.VISIBLE);
            txtJobTitle.setText(mJob.get(position).getJobTitle());
            txtJobCategory.setText(mJob.get(position).getJobType());
            jobStatus.setVisibility(View.GONE);
            String wageStr = "$" + mJob.get(position).getHourlyWage();
            txtJobWage.setText(wageStr);
            applyBtn.setVisibility(View.GONE);
            completeBtn.setVisibility(View.VISIBLE);
            completeBtn.setOnClickListener(
                    view -> {
//                        int rating = Integer.parseInt(ratingET.getText().toString());
//                        if (rating<1 || rating > 5){
//                            Toast.makeText(mContext, "rating must be between 1 and 5", Toast.LENGTH_LONG).show();
//                        } else {
//                            mJob.get(position).getApplication().setEmployeeRating(rating);
//                        }
                        mJob.get(position).getApplication().employeeMarkComplete();
                        this.activity.recreate();
                    }
            );
            return convertView;
        } else if (mJob.get(position).getJobState().equals(JobPost.JOB_OPEN)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee, parent, false);
            TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
            TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
            TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);
            TextView jobStatus = (TextView) convertView.findViewById(R.id.job_status);
            String currentJobID = mJobKey.get(position);
            JobPost currentJob = mJob.get(position);
            Button applyBtn = convertView.findViewById(R.id.applyBtn);

            applyBtn.setOnClickListener(
                    view -> {
                        JobApplication jobApplication = new JobApplication(mJob.get(position).getJobRef().getKey(), mJob.get(position).getUserID(), UserSession.getInstance().getUsrID());
                        mJob.get(position).setApplication(jobApplication);
                        mJob.get(position).updateDB();
                        jobApplication.applyEmployeeForJob();
                        this.activity.recreate();

                    }
            );

            txtJobTitle.setText(mJob.get(position).getJobTitle());
            txtJobCategory.setText(mJob.get(position).getJobType());
            String wageStr = "$" + mJob.get(position).getHourlyWage();
            txtJobWage.setText(wageStr);
            return convertView;
        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_empty, parent, false);
        }
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee,parent,false);
        TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
        TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
        TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);

        txtJobTitle.setText(mJob.get(position).getJobTitle());
        txtJobCategory.setText(mJob.get(position).getJobType());
        String wageStr = "$" + mJob.get(position).getHourlyWage();
        txtJobWage.setText(wageStr);

        return convertView;
    }

    private void saveJobRequest(JobRequest jobRequest) {
        final DatabaseReference job = database.push();

        job.setValue(jobRequest);
    }

    private void saveJobApplication(JobApplication jobApplication) {
        database = FirebaseDatabase.getInstance(UserSession.FIREBASE_URL).getReference();
        final DatabaseReference application = database.child("jobApplications").push();
        application.setValue(jobApplication);
    }



}
