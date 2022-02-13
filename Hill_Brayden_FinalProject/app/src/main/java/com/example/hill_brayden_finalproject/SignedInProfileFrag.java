package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.SignedInProfileFragBinding;
import com.example.hill_brayden_finalproject.models.User;

import java.util.HashMap;
import java.util.Map;

public class SignedInProfileFrag extends Fragment {
    private UserViewModel vm;

    public SignedInProfileFrag()
    {
        super(R.layout.signed_in_profile_frag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SignedInProfileFragBinding binding = SignedInProfileFragBinding.inflate(getLayoutInflater());

//        Create save btn
        Button saveBtn = binding.saveBtn;

//        Set the QR Icon as a button to generate images
        ImageView qrIcon = binding.qrCode;

//        Create all needed editText Databinds
        EditText nameEdit = binding.profileUsername;
        EditText roleEdit = binding.profileUserRole;
        EditText phoneEdit = binding.phoneNumber;
        EditText emailEdit = binding.emailAddress;
        EditText fbEdit = binding.fbLink;
        EditText instEdit = binding.instaLink;
        EditText twitEdit = binding.twitterLink;
        EditText linkedinEdit = binding.linkinLink;

//        Set onClick to save the data.
        saveBtn.setOnClickListener(view -> {
            Map<String, Object> infoDict = new HashMap<>();
            infoDict.put("name",nameEdit.getText().toString());
            infoDict.put("role",roleEdit.getText().toString());
            infoDict.put("phone", phoneEdit.getText().toString());
            infoDict.put("email", emailEdit.getText().toString());
            infoDict.put("facebook", fbEdit.getText().toString());
            infoDict.put("instagram", instEdit.getText().toString());
            infoDict.put("twitter", twitEdit.getText().toString());
            infoDict.put("linkedin", linkedinEdit.getText().toString());

//            Notify the user if the save works
            if (vm.saveData(infoDict) == true) {
                Toast.makeText(getContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(),"Save Failed!",Toast.LENGTH_SHORT).show();
            }
        });

//        Attempt to load data
        MutableLiveData<User> loadedLiveUser = vm.loadData();
        loadedLiveUser.observe(requireActivity(), Observer ->{
            Log.d("Updated User", "User Changed");

//            Set the name
            TextView profileName = binding.profileUsername;
            profileName.setText(loadedLiveUser.getValue().getName());

//          Set the text with the updated data
            EditText phone = binding.phoneNumber;
            EditText email = binding.emailAddress;
            EditText name = binding.profileUsername;
            EditText role = binding.profileUserRole;
            EditText facebook = binding.fbLink;
            EditText instagram = binding.instaLink;
            EditText twitter = binding.twitterLink;
            EditText linkedin = binding.linkinLink;

            name.setText(loadedLiveUser.getValue().getName());
            role.setText(loadedLiveUser.getValue().getRole());
            phone.setText(loadedLiveUser.getValue().getPhone());
            email.setText(loadedLiveUser.getValue().getEmail());
            facebook.setText(loadedLiveUser.getValue().getFacebook());
            instagram.setText(loadedLiveUser.getValue().getInstagram());
            twitter.setText(loadedLiveUser.getValue().getTwitter());
            linkedin.setText(loadedLiveUser.getValue().getLinkedin());

        });

//        Set Onclick for the qr icon
        qrIcon.setOnClickListener(view -> getParentFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .replace(R.id.main_frag_container, QrDisplayFrag.class, null)
                .commit());


        return binding.getRoot();
    }


}
