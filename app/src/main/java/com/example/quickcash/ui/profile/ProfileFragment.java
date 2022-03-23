package com.example.quickcash.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.quickcash.JobPostingActivity;
import com.example.quickcash.LandingPageActivity;
import com.example.quickcash.MainActivity;
import com.example.quickcash.R;
import com.example.quickcash.account.LoginActivity;
import com.example.quickcash.account.SignupActivity;
import com.example.quickcash.databinding.FragmentProfileBinding;
import com.example.quickcash.identity.User;
import com.example.quickcash.payment.PaymentActivity;
import com.example.quickcash.util.FirebaseUtil;
import com.google.firebase.database.DatabaseReference;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Button updateProfile;
    private Button testButton;
    public static DatabaseReference userRef;
    public MainActivity mainActivity;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mainActivity = (MainActivity) getActivity();
        userRef = mainActivity.userRef;
        updateProfile = root.findViewById(R.id.UpdateProfile);
        testButton = root.findViewById(R.id.testButton);
        updateProfile.setOnClickListener(view -> startActivity(new Intent(this.getContext(), ProfileUpdateActivity.class)));
        testButton.setOnClickListener(view -> startActivity(new Intent(this.getContext(), PaymentActivity.class)));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}