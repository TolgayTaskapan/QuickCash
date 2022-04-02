package com.example.quickcash.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.quickcash.JobPost;
import com.example.quickcash.JobRequest;
import com.example.quickcash.R;
import com.example.quickcash.util.UserSession;
import com.google.firebase.database.DatabaseReference;

import java.util.LinkedList;

public class DashBoardEmployeeAdapter extends BaseAdapter {

    private LinkedList<JobRequest> mJob;
    private Context mContext;


    public DashBoardEmployeeAdapter(LinkedList<JobRequest> mJob, Context mContext) {
        this.mJob = mJob;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_dashboard_item_employee,parent,false);
        TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
        TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
        TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);
        TextView txtJobStatus = (TextView) convertView.findViewById(R.id.item_job_status);

        txtJobTitle.setText(mJob.get(position).getJobTitle());
        txtJobCategory.setText(mJob.get(position).getJobType());
        String wageStr = "$" + mJob.get(position).getHourlyWage();
        txtJobWage.setText(wageStr);

        if(mJob.get(position).getStatus() == 0){
            txtJobStatus.setText("In progress");
        }else {
            txtJobStatus.setText("Approved");
        }


        return convertView;
    }
}
