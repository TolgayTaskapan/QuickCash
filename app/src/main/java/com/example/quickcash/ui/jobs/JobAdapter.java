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

import java.util.LinkedList;

public class JobAdapter extends FirebaseRecyclerAdapter<JobPost, JobAdapter.JobViewHolder> {
    private LinkedList<JobPost> mJob;
    private Context mContext;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public JobAdapter(@NonNull FirebaseRecyclerOptions<JobPost> options) {
        super(options);
    }

//    public JobAdapter(LinkedList<JobPost> mJob, Context mContext) {
//        this.mJob = mJob;
//        this.mContext = mContext;
//    }

//    @Override
//    public int getCount() {
//        return mJob.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_job_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull JobViewHolder holder, int position, @NonNull JobPost job) {
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

    public class JobViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView typeTV;
        private final TextView wageTV;
        private final Button updateBtn;
        private final Button deleteBtn;
        private final Context context;

        public JobViewHolder(@NonNull View itemView) {
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
