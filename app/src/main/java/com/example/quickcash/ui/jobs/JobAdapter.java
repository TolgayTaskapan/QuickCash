package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.quickcash.JobApplication;
import com.example.quickcash.JobPost;
import com.example.quickcash.JobRequest;
import com.example.quickcash.R;
import com.example.quickcash.util.UserSession;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;

public class JobAdapter extends BaseAdapter {
    private LinkedList<JobPost> mJob;
    private Context mContext;
    private LinkedList<String> mJobKey;
    private DatabaseReference database;

    public JobAdapter(LinkedList<JobPost> mJob, LinkedList<String> mJobKey, DatabaseReference database, Context mContext) {
        this.mJob = mJob;
        this.mContext = mContext;
        this.mJobKey = mJobKey;
        this.database = database;
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

        if (!mJob.get(position).getJobState().equals(JobPost.JOB_OPEN)) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_empty, parent, false);
        } else if (mJob.get(position).getJobState().equals(JobPost.JOB_PENDING)){

        } else {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee, parent, false);
            TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
            TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
            TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);
            String currentJobID = mJobKey.get(position);
            JobPost currentJob = mJob.get(position);
            Button applyBtn = convertView.findViewById(R.id.applyBtn);

            applyBtn.setOnClickListener(
                    view -> {
//                        JobRequest jobRequest = new JobRequest(currentJobID, currentJob.getUserID(), UserSession.getInstance().getUsrID(), txtJobTitle.getText().toString(), txtJobCategory.getText().toString(), Double.parseDouble(txtJobWage.getText().toString().substring(1)), 0);
//                        saveJobRequest(jobRequest);
                        JobApplication jobApplication = new JobApplication();
                    }
            );

            txtJobTitle.setText(mJob.get(position).getJobTitle());
            txtJobCategory.setText(mJob.get(position).getJobType());
            String wageStr = "$" + mJob.get(position).getHourlyWage();
            txtJobWage.setText(wageStr);
            return convertView;
        }
        return convertView;
    }

    private void saveJobRequest(JobRequest jobRequest) {
        final DatabaseReference job = database.push();

        job.setValue(jobRequest);
    }

}
