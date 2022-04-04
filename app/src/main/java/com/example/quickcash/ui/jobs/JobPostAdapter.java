package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.AddUpdateJobPostActivity;

import com.example.quickcash.JobApplication;
import com.example.quickcash.JobHistory;
import com.example.quickcash.JobApplicantView.ViewApplicants;
import com.example.quickcash.JobPost;
import com.example.quickcash.R;
import com.example.quickcash.identity.Employee;
import com.example.quickcash.identity.Employer;
import com.example.quickcash.identity.User;
import com.example.quickcash.util.UserSession;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import kotlinx.coroutines.Job;

public class JobPostAdapter extends FirebaseRecyclerAdapter<JobPost, JobPostAdapter.JobPostViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public JobPostAdapter(@NonNull FirebaseRecyclerOptions<JobPost> options) {
        super(options);
    }

    @NonNull
    @Override
    public JobPostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_job_item_employer, parent, false);
        return new JobPostViewHolder(view);
    }

    protected void onBindViewHolder(@NonNull JobPostViewHolder holder, int position, @NonNull JobPost job) {
        job.setJobRef(FirebaseDatabase.getInstance(UserSession.FIREBASE_URL)
                .getReference().child(UserSession.JOB_COLLECTION)
                .child(getRef(position).getKey()));

        if (job.getJobState().equals(JobPost.JOB_PENDING)){
            holder.updateBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
            holder.approveBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setVisibility(View.VISIBLE);
            holder.applicantName.setVisibility(View.VISIBLE);
            holder.applicantName.setText("Applicant Pending Approval!");
        } else if (job.getJobState().equals(JobPost.JOB_IN_PROGRESS)){
            holder.updateBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
            holder.approveBtn.setVisibility(View.GONE);
            holder.declineBtn.setVisibility(View.GONE);
            holder.applicantName.setVisibility(View.VISIBLE);
            holder.applicantName.setText("Job is in Progress");

        }else  if (job.getJobState().equals(JobPost.JOB_COMPLETE)){
            holder.approveBtn.setVisibility(View.GONE);
            holder.declineBtn.setVisibility(View.GONE);
            holder.updateBtn.setVisibility(View.GONE);
            holder.deleteBtn.setVisibility(View.GONE);
            holder.paymentBtn.setVisibility(View.VISIBLE);
            //holder.ratingET.setVisibility(View.VISIBLE);
            holder.paymentBtn.setOnClickListener(view -> {
//                int rating = Integer.parseInt(holder.ratingET.getText().toString());
//                if (rating<1 || rating > 5){
//                    Toast.makeText(holder.context, "rating must be between 1 and 5", Toast.LENGTH_LONG).show();
//                } else {
//                    //send payment to employee
//
//                }
            });
        }
        holder.titleTV.setText(job.getJobTitle());
        holder.typeTV.setText(job.getJobType());
        holder.wageTV.setText("$" + String.valueOf(job.getHourlyWage()));
        holder.wageTV.setText(String.valueOf(job.getHourlyWage()));
        holder.jobItems.setOnClickListener(view -> {
            Intent intent = new Intent(holder.context, ViewApplicants.class);
            intent.putExtra("job_key", job);
            holder.context.startActivity(intent);
        });

        holder.updateBtn.setOnClickListener(view -> {
            final Intent intent = new Intent(holder.context, AddUpdateJobPostActivity.class);
            intent.putExtra(AddUpdateJobPostActivity.TAG, AddUpdateJobPostActivity.UPDATE_JOB);
            intent.putExtra(AddUpdateJobPostActivity.UPDATE_JOB_KEY, getRef(position).getKey());
            intent.putExtra(JobPost.TAG, job);
            holder.context.startActivity(intent);
        });
        holder.deleteBtn.setOnClickListener(view -> FirebaseDatabase.getInstance(UserSession.FIREBASE_URL)
                .getReference().child(UserSession.JOB_COLLECTION)
                .child(getRef(position).getKey())
                .removeValue()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(holder.context, "Job deleted successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(holder.context, "Job delete failed", Toast.LENGTH_SHORT).show()));
        holder.approveBtn.setOnClickListener(view -> {
            job.getApplication().employerApproveEmployee();

        });


    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView typeTV;
        private final TextView wageTV;
        private final TextView applicantName;
        private final Button updateBtn;
        private final Button deleteBtn;
        private final Button approveBtn;
        private final Button declineBtn;
        private final Button paymentBtn;
        private final EditText ratingET;
        private final Context context;
        private final LinearLayout jobItems;

        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = itemView.findViewById(R.id.item_job_title);
            typeTV = itemView.findViewById(R.id.item_job_category);
            wageTV = itemView.findViewById(R.id.item_job_wage);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            approveBtn = itemView.findViewById(R.id.approveBtn);
            declineBtn = itemView.findViewById(R.id.declineBtn);
            paymentBtn = itemView.findViewById(R.id.paymentBtn);
            ratingET = itemView.findViewById(R.id.rateEmployeeET);
            applicantName = itemView.findViewById(R.id.applicant_name);
            context = itemView.getContext();
            jobItems = itemView.findViewById(R.id.JobItem);
        }
    }

    public void approveJobApplication(JobApplication application){
        application.employerApproveEmployee();
    }




}
