package com.example.quickcash.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.JobPost;
import com.example.quickcash.JobRequest;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.WrapLinearLayoutManager;
import com.example.quickcash.databinding.FragmentDashboardBinding;
import com.example.quickcash.ui.jobs.JobAdapter;
import com.example.quickcash.ui.jobs.JobPostAdapter;
import com.example.quickcash.util.UserSession;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Iterator;
import java.util.LinkedList;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private RecyclerView recyclerView;
    private ListView dashboardListView;
    private DashboardEmployerAdapter dashboardEmployerAdapter;

    private LinkedList<JobRequest> mJobs;
    public static DatabaseReference userRef;
    public DatabaseReference database;
    public MainActivity mainActivity;

    private static final String CATEGORY_ALL = "Category - All";
    private static final String RECOMMEND = "Recommend";
    public String prefer_type;
    private String selectedCategory;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        DashboardViewModel dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mainActivity = (MainActivity) getActivity();
        userRef = UserSession.getInstance().getCurrentUserRef();

        initializeDatabase();
        init(root);
        setActivityView();

        connectToFirebaseRTDB();


        return root;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.appliedJobRecyclerView);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
    }

    public void initializeDatabase(){
        database = FirebaseDatabase.getInstance("https://quick-cash-ca106-default-rtdb.firebaseio.com/").getReference().child(UserSession.JOB_REQUEST);
    }

    private void setActivityView() {
    }


    private void connectToFirebaseRTDB() {
        final String userID = UserSession.getInstance().getUsrID();
        final FirebaseRecyclerOptions<JobPost> options = new FirebaseRecyclerOptions.Builder<JobPost>()
                .setQuery(FirebaseDatabase.getInstance(UserSession.FIREBASE_URL)
                        .getReference()
                        .child(UserSession.JOB_COLLECTION).orderByChild("duration").equalTo(5), JobPost.class)
                .build();

        dashboardEmployerAdapter = new DashboardEmployerAdapter(options);
        recyclerView.setAdapter(dashboardEmployerAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}