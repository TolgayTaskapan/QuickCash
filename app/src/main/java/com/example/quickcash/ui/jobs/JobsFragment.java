package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.AddUpdateJobPostActivity;
import com.example.quickcash.JobApplicantView.ViewApplicants;
import com.example.quickcash.JobPost;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.WrapLinearLayoutManager;
import com.example.quickcash.databinding.FragmentJobsBinding;

import com.example.quickcash.jobsearch.JobSearchActivity;
import com.example.quickcash.util.UserSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class JobsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListView jobListView;
    private JobAdapter jobAdapter;
    private JobPostAdapter jobPostAdapter;
    private FloatingActionButton addFAB;
    private Button searchButton;
    private Spinner categorySpinner;

    private FragmentJobsBinding binding;

    private LinkedList<JobPost> mJobs;
    public static DatabaseReference userRef;
    public MainActivity mainActivity;

    private static final String CATEGORY_ALL = "Category - All";
    private static final String RECOMMEND = "Recommend";
    public String prefer_type;
    private String selectedCategory;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JobsViewModel jobsViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mainActivity = (MainActivity) getActivity();

        init(root);
        setActivityView();

        if (UserSession.getInstance().getUser().isEmployer()) {
            connectToFirebaseRTDB();
        }
        attachListeners();

        return root;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.jobsRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        jobListView = view.findViewById(R.id.list_jobs);
        addFAB = view.findViewById(R.id.addButton);
        searchButton = view.findViewById(R.id.JobSearchBtn);
        categorySpinner = view.findViewById(R.id.categorySpinner);
    }

    private void setActivityView() {
        if (UserSession.getInstance().getUser().isEmployee()) {
            getPreferenceCategory();
            if ( !setupCategorySpinner() ) showToastMessage("Job Fragment: Fail to set up category spinner");

            retrieveJobsFormFirebase();
        }
        String userType = UserSession.getInstance().getUser().getIdentity();
        if (userType != null) {
            if (userType.equals("Employer")) {
                searchButton.setVisibility(View.GONE);
                jobListView.setVisibility(View.GONE);
                categorySpinner.setVisibility(View.GONE);
            } else {
                searchButton.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                addFAB.setVisibility(View.GONE);
            }
        } else {
            System.out.println("nothing found");
        }
    }

    private void attachListeners() {
        addFAB.setOnClickListener(view ->
                startActivity(new Intent(this.getContext(), AddUpdateJobPostActivity.class)));
        searchButton.setOnClickListener(view ->
                startActivity(new Intent(this.getContext(), JobSearchActivity.class)));
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = adapterView.getItemAtPosition(i).toString();
                filterTheJobList(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                selectedCategory = RECOMMEND;
            }
        });
    }

    private void connectToFirebaseRTDB() {
        final String userID = UserSession.getInstance().getUsrID();
        final FirebaseRecyclerOptions<JobPost> options = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(FirebaseDatabase.getInstance(UserSession.FIREBASE_URL)
                        .getReference()
                        .child(UserSession.JOB_COLLECTION).orderByChild("userID").equalTo(userID), JobPost.class)
                .build();

        jobPostAdapter = new JobPostAdapter(options);
        recyclerView.setAdapter(jobPostAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (UserSession.getInstance().getUser().isEmployer()) {
            jobPostAdapter.startListening();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setActivityView();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (UserSession.getInstance().getUser().isEmployer()) {
            jobPostAdapter.stopListening();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getPreferenceCategory() {
        userRef = UserSession.getInstance().getCurrentUserRef();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prefer_type = snapshot.child("prefer").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean setupCategorySpinner() {
        boolean setUp = false;

        // Create the spinner.
        //Spinner spinner = binding.categorySpinner;
        //Spinner spinner = categorySpinner;
        if (categorySpinner != null) {
            categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedCategory = adapterView.getItemAtPosition(i).toString();
                    filterTheJobList(selectedCategory);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedCategory = RECOMMEND;
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
        categorySpinner.setAdapter(adapter);
        setUp = true;

        return setUp;
    }

    private void filterTheJobList(String category) {
        LinkedList<JobPost> filteredList = new LinkedList<JobPost>();

        if (category.equals(RECOMMEND)) {
            category = prefer_type;
        }

        System.out.println("=========" + category + "==========");
        System.out.println("=========" + mJobs.size() + "==========");

        // The option of showing all jobs
        if (category == null || category.equals(CATEGORY_ALL)) {
            showUpJobList(this.mJobs);
            return;
        }

        // Go through the jobs to filter out the desired category
        for (int i = 0; i < mJobs.size(); i++) {
            JobPost job = mJobs.get(i);
            if (job.getJobType().equals(category)) {
                filteredList.add(job);
            }
        }

        if (filteredList.size() == 0) {
            showToastMessage("There is no job under this category");
        }

        showUpJobList(filteredList);
        return;


    }

    private void showToastMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }


    private void retrieveJobsFormFirebase() {
        DatabaseReference jobRef = FirebaseDatabase.getInstance(UserSession.FIREBASE_URL).getReference("job");
        mJobs = new LinkedList<JobPost>();

        jobRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
                String userID = UserSession.getInstance().getUsrID();

                while (iterator.hasNext()) {
                    Integer duration = iterator.next().getValue(Integer.class);
                    Double wage = iterator.next().getValue(Double.class);
                    String title = iterator.next().getValue(String.class);
                    String type = iterator.next().getValue(String.class);
                    Double latitude = iterator.next().getValue(Double.class);
                    String location = iterator.next().getValue(String.class);
                    Double longitude = iterator.next().getValue(Double.class);
                    iterator.next();    // Skip the userID in Firebase
                    mJobs.add(new JobPost(title,type,wage,duration, location, latitude,longitude,userID));
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // not being used
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // not being used
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // not being used

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // not being used

            }
        });
    }

    private void showUpJobList(LinkedList<JobPost> inCategory) {
        Context mContext = this.getContext();
        jobListView = binding.listJobs;

        jobAdapter = new JobAdapter(inCategory, mContext);
        jobListView.setAdapter(jobAdapter);
    }




}