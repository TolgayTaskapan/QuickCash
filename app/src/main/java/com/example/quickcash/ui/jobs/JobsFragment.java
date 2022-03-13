package com.example.quickcash.ui.jobs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.AddUpdateJobPostActivity;
import com.example.quickcash.JobPost;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.WrapLinearLayoutManager;
import com.example.quickcash.databinding.FragmentJobsBinding;
import com.example.quickcash.identity.Employee;
import com.example.quickcash.util.FirebaseUtil;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;
import java.util.LinkedList;

public class JobsFragment extends Fragment {

    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private FloatingActionButton addFAB;

    private FragmentJobsBinding binding;
    private final String userID = MainActivity.userFirebase.getUsrID();

    private LinkedList<JobPost> mJobs;

    private String selectedCategory = "Category - All";


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        JobsViewModel jobsViewModel = new ViewModelProvider(this).get(JobsViewModel.class);
        binding = FragmentJobsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);

        init(root);
        connectToFirebaseRTDB();
        attachListeners();

        if ( !setupCategorySpinner() ) {
            System.out.println("Job Fragment: Fail to set up category spinner");
        }

       // retrieveJobsFormFirebase();

        return root;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.jobsRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        addFAB = view.findViewById(R.id.addButton);
    }

    private void attachListeners() {
        addFAB.setOnClickListener(view ->
                startActivity(new Intent(this.getContext(), AddUpdateJobPostActivity.class)));
    }

    private void connectToFirebaseRTDB() {
        final FirebaseRecyclerOptions<JobPost> options = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(FirebaseDatabase.getInstance(FirebaseUtil.FIREBASE_URL)
                        .getReference()
                        .child(FirebaseUtil.JOB_COLLECTION).orderByChild("userID").equalTo(MainActivity.userFirebase.getUsrID()), JobPost.class)
                .build();

        jobAdapter = new JobAdapter(options);
        recyclerView.setAdapter(jobAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        jobAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        jobAdapter.stopListening();
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
                    filterTheJobList(selectedCategory);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    selectedCategory = "Category - All";
                    System.out.println(selectedCategory);
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

    private void filterTheJobList(String category) {
        LinkedList<JobPost> filteredList = new LinkedList<JobPost>();

        // If null, set to default category, which is all
        if (category == null || category.equals("Category - All")) {
            category = "Category - All";
            //showUpJobList(this.mJobs);
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

        //showUpJobList(filteredList);
        return;
    }

    private void showToastMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    private void showUpJobList(LinkedList<JobPost> inCategory) {
        Context mContext = this.getContext();
        ListView jobListView = (ListView) binding.listJobs;

        jobAdapter = new JobAdapter(inCategory, mContext);
        jobListView.setAdapter(jobAdapter);
    }

//    private void retrieveJobsFormFirebase() {
//        DatabaseReference jobRef = FirebaseDatabase.getInstance(FirebaseUtil.FIREBASE_URL).getReference("job");
//        mJobs = new LinkedList<JobPost>();
//
//        jobRef.orderByChild("userID").equalTo(userID).addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Iterator<DataSnapshot> iterator = snapshot.getChildren().iterator();
//                String userID = MainActivity.userFirebase.getUsrID();
//
//                while (iterator.hasNext()) {
//                    Integer duration = iterator.next().getValue(Integer.class);
//                    Double wage = iterator.next().getValue(Double.class);
//                    String title = iterator.next().getValue(String.class);
//                    String type = iterator.next().getValue(String.class);
//                    Double latitude = iterator.next().getValue(Double.class);
//                    Double longitude = iterator.next().getValue(Double.class);
//                    iterator.next();    // Skip the userID in Firebase
//                    mJobs.add(new JobPost(title,type,wage,duration,latitude,longitude,userID));
//                }
//
//                Spinner spinner = binding.categorySpinner;
//                spinner.setSelection(0);
//
//               // showUpJobList(mJobs);
//            }

//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
   // }


}