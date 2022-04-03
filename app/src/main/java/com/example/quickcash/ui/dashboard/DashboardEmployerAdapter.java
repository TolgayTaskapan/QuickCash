package com.example.quickcash.ui.dashboard;

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
import com.example.quickcash.JobRequest;
import com.example.quickcash.R;
import com.example.quickcash.util.UserSession;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DashboardEmployerAdapter extends FirebaseRecyclerAdapter<JobPost, DashboardEmployerAdapter.DashBoardViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DashboardEmployerAdapter(@NonNull FirebaseRecyclerOptions<JobPost> options) {
        super(options);
    }

    @NonNull
    @Override
    public DashBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_job_item_employer, parent, false);
        return new DashBoardViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull DashBoardViewHolder holder, int position, @NonNull JobPost jobPost) {
        holder.titleTV.setText(jobPost.getJobTitle());
        holder.typeTV.setText(jobPost.getJobType());
        holder.wageTV.setText(String.valueOf(jobPost.getHourlyWage()));

    }

    public class DashBoardViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTV;
        private final TextView typeTV;
        private final TextView wageTV;
        private final Button approveBtn;
        private final Button declineBtn;
        private final Context context;

        public DashBoardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTV = itemView.findViewById(R.id.item_job_title);
            typeTV = itemView.findViewById(R.id.item_job_category);
            wageTV = itemView.findViewById(R.id.item_job_wage);
            approveBtn = itemView.findViewById(R.id.approveBtn);
            declineBtn = itemView.findViewById(R.id.declineBtn);
            context = itemView.getContext();
        }
    }
}
