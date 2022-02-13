package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.NewUserFragmentBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewUserFrag extends Fragment {
//    Variables for registration
    private String nameStr = null;
    private String emailStr = null;
    private String passwordStr = null;
    private UserViewModel vm;


    public NewUserFrag() {
        super(R.layout.new_user_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NewUserFragmentBinding binding = NewUserFragmentBinding.inflate(getLayoutInflater());

//        Generate animations
        genAnimation(binding);

//        Gather data for onClick
        MaterialButton regBtn = binding.registerRegBtn;
        TextInputEditText nameEdit = binding.registerNameEdit;
        TextInputEditText emailEdit = binding.registerEmailEdit;
        TextInputEditText passEdit = binding.registerPasswordEdit;

//        Set onclick to register new user
        regBtn.setOnClickListener(v -> {
            nameStr = nameEdit.getText().toString();
            emailStr = emailEdit.getText().toString();
            passwordStr = passEdit.getText().toString();

//            Register the user in Auth and Firestore
            regUser(nameStr, emailStr, passwordStr);

        });

        return binding.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    public void genAnimation(NewUserFragmentBinding binding) {

        TextView registerText = binding.newuserRegister;
        TextInputLayout name = binding.registerName;
        TextInputLayout email = binding.registerEmail;
        TextInputLayout password = binding.registerPassword;
        MaterialButton regBtn = binding.registerRegBtn;

        registerText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        name.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        email.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        password.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        regBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));

//        Stagger the animations.
        name.getAnimation().setStartOffset(300);
        email.getAnimation().setStartOffset(450);
        password.getAnimation().setStartOffset(600);
        regBtn.getAnimation().setStartOffset(150);

    }

    public boolean regUser(String name, String email, String pass) {
        final boolean[] flag = {true};

//  Source the mAuth from the main activity
        MainActivity activity = (MainActivity)getActivity();
        FirebaseAuth mAuth = activity.getFireAuth();

//region            If entered data, attempt auth, and send to new fragment
        if (name.equals("") && email.equals("") && pass.equals("")) {
            Toast.makeText(activity, "Please fill-out all fields", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            Log.d("REGISTRATION", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                            vm.initUser(name, user.getUid());
//                                updateUI(user);
                        } else {
                            Log.w("REGISTRATION", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                                updateUI(null);
                            flag[0] = false;
                        }
                    });
        }
        return flag[0];
    }
}
