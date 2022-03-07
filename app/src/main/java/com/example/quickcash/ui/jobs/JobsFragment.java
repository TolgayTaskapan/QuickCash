package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.JobPost;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.databinding.FragmentJobsBinding;
import com.example.quickcash.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JobsFragment extends Fragment {

    private FragmentJobsBinding binding;

    private LinkedList<JobPost> mJobs;
    private JobAdapter mAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobsViewModel jobsViewModel =
                new ViewModelProvider(this).get(JobsViewModel.class);

        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        retrieveJobsFormFirebase();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void showUpJobList() {
        Context mContext = this.getContext();
        ListView jobListView = (ListView) binding.listJobs;

        mAdapter = new JobAdapter(mJobs, mContext);
        jobListView.setAdapter(mAdapter);
    }

    private void retrieveJobsFormFirebase() {
        DatabaseReference jobRef = FirebaseDatabase.getInstance(FirebaseUtil.FIREBASE_URL).getReference("job");
        mJobs = new LinkedList<JobPost>();

        jobRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                String userID = MainActivity.userFirebase.getUsrID();

                while (iterator.hasNext()) {
                    Integer duration = iterator.next().getValue(Integer.class);
                    Double wage = iterator.next().getValue(Double.class);
                    String title = iterator.next().getValue(String.class);
                    String type = iterator.next().getValue(String.class);
                    Double latitude = iterator.next().getValue(Double.class);
                    Double longitude = iterator.next().getValue(Double.class);
                    iterator.next();
                    System.out.println(userID);
                    System.out.println(wage);
                    System.out.println(title);
                    System.out.println(type);
                    mJobs.add(new JobPost(title,type,wage,duration,latitude,longitude,userID));
                }

                showUpJobList();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}