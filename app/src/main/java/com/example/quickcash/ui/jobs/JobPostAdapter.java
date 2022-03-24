package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.AddUpdateJobPostActivity;
import com.example.quickcash.JobPost;
import com.example.quickcash.R;
import com.example.quickcash.util.UserSession;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

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
        holder.titleTV.setText(job.getJobTitle());
        holder.typeTV.setText(job.getJobType());
        holder.wageTV.setText(String.valueOf(job.getHourlyWage()));
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
    }

    public class JobPostViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView typeTV;
        private final TextView wageTV;
        private final Button updateBtn;
        private final Button deleteBtn;
        private final Context context;

        public JobPostViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.item_job_title);
            typeTV = itemView.findViewById(R.id.item_job_category);
            wageTV = itemView.findViewById(R.id.item_job_wage);
            updateBtn = itemView.findViewById(R.id.updateBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
            context = itemView.getContext();
        }
    }


}
