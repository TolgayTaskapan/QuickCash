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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.list_job_item,parent,false);
        TextView txt_jobTitle = (TextView) convertView.findViewById(R.id.item_job_title);
        TextView txt_jobCategory = (TextView) convertView.findViewById(R.id.item_job_category);
        TextView txt_jobWage = (TextView) convertView.findViewById(R.id.item_job_wage);

        txt_jobTitle.setText(mJob.get(position).getJobTitle());
        txt_jobCategory.setText(mJob.get(position).getJobType());
        String wage_str =  "$" + mJob.get(position).getHourlyWage();
        txt_jobWage.setText(wage_str);
        return convertView;
    }
}
