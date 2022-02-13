package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.ScanUserFragBinding;
import com.example.hill_brayden_finalproject.models.User;

public class ScanUserFrag extends Fragment {
    private UserViewModel vm;

    public ScanUserFrag () {
        super(R.layout.scan_user_frag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ScanUserFragBinding binding = ScanUserFragBinding.inflate(getLayoutInflater());

//        Attempt to load data
        MutableLiveData<User> loadedLiveUser = vm.getScannedUser();
        loadedLiveUser.observe(requireActivity(), Observer ->{
            Log.d("Updated User", "User Changed");

//            Set the name
            TextView profileName = binding.profileUsername;
            profileName.setText(loadedLiveUser.getValue().getName());

//          Set Bindings
            EditText phone = binding.phoneNumber;
            EditText email = binding.emailAddress;
            EditText name = binding.profileUsername;
            EditText role = binding.profileUserRole;
            EditText facebook = binding.fbLink;
            EditText instagram = binding.instaLink;
            EditText twitter = binding.twitterLink;
            EditText linkedin = binding.linkinLink;

//            Set Text
            name.setText(loadedLiveUser.getValue().getName());
            role.setText(loadedLiveUser.getValue().getRole());
            phone.setText(loadedLiveUser.getValue().getPhone());
            email.setText(loadedLiveUser.getValue().getEmail());
            facebook.setText(loadedLiveUser.getValue().getFacebook());
            instagram.setText(loadedLiveUser.getValue().getInstagram());
            twitter.setText(loadedLiveUser.getValue().getTwitter());
            linkedin.setText(loadedLiveUser.getValue().getLinkedin());

        });

        return binding.getRoot();
    }
}
