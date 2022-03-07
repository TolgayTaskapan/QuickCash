package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class JobsFragment extends Fragment {

    private FragmentJobsBinding binding;

    private LinkedList<JobPost> mJobs;
    private JobAdapter jobAdapter;

    private String selectedCategory = "Category - All";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        JobsViewModel jobsViewModel =
                new ViewModelProvider(this).get(JobsViewModel.class);

        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if ( !setupCategorySpinner() ) {
            System.out.println("Job Fragment: Fail to set up category spinner");
        }

        retrieveJobsFormFirebase();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean setupCategorySpinner() {
        boolean setUp = false;

        // Create the spinner.
        Spinner spinner = binding.categorySpinner;
        if (spinner != null) {
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedCategory = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedCategory = adapterView.getSelectedItem().toString();
                }
            });
        } else {
            return setUp;
        }

        // Create ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.category_names, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner.
        if (spinner != null) {
            spinner.setAdapter(adapter);
            setUp = true;
        }

        return setUp;
    }

    private void showUpJobList() {
        Context mContext = this.getContext();
        ListView jobListView = (ListView) binding.listJobs;

        jobAdapter = new JobAdapter(mJobs, mContext);
        jobListView.setAdapter(jobAdapter);
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