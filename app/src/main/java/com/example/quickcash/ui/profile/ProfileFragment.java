package com.example.quickcash.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quickcash.AddUpdateJobPostActivity;
import com.example.quickcash.LandingPageActivity;
import com.example.quickcash.R;
import com.example.quickcash.WrapLinearLayoutManager;
import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.databinding.FragmentProfileBinding;
import com.example.quickcash.util.UserSession;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Button logoutButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        init(root);
        attachListeners();

        /*final TextView textView = binding.textProfile;
        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);*/
        return root;
    }

    private void init(View view) {
        logoutButton = view.findViewById(R.id.logoutButton);
    }

    private void attachListeners() {
        logoutButton.setOnClickListener(view ->
                logoutAccount());
    }

    public void logoutAccount() {
        Intent logoutIntent = new Intent(this.getContext(), LandingPageActivity.class);
        UserSession.getInstance().logout();
        startActivity(logoutIntent);
        this.getActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}