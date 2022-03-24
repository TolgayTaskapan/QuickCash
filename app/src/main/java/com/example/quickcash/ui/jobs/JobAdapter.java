package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.quickcash.JobPost;
import com.example.quickcash.R;

import java.util.LinkedList;

public class JobAdapter extends BaseAdapter {
    private LinkedList<JobPost> mJob;
    private Context mContext;

    public JobAdapter(LinkedList<JobPost> mJob, Context mContext) {
        this.mJob = mJob;
        this.mContext = mContext;
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item_employee,parent,false);
        TextView txtJobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
        TextView txtJobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
        TextView txtJobWage = (TextView) convertView.findViewById(R.id.item_job_wage);

        txtJobTitle.setText(mJob.get(position).getJobTitle());
        txtJobCategory.setText(mJob.get(position).getJobType());
        String wageStr = mJob.get(position).getHourlyWage() + "$";
        txtJobWage.setText(wageStr);
        return convertView;
    }
}
