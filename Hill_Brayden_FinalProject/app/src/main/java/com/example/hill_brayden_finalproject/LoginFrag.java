package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.os.Handler;
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

import com.example.hill_brayden_finalproject.databinding.LoginFragBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFrag extends Fragment {
    FirebaseAuth mAuth;
    private UserViewModel vm;

    public LoginFrag() {
        super(R.layout.login_frag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoginFragBinding binding = LoginFragBinding.inflate(getLayoutInflater());

//        Create vars from bindings
        TextView loginText = binding.loginLoginText;
        TextInputLayout emailText = binding.loginEmail;
        TextInputLayout passText = binding.loginPassword;
        MaterialButton loginBtn = binding.loginSignin;
        MaterialButton newUserBtn = binding.loginNewUser;
        MaterialButton scanCode = binding.scanQr;

//        Set Animations
        loginText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        emailText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        passText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        loginBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        newUserBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));
        scanCode.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_translates));


//        Stagger the animations.
        emailText.getAnimation().setStartOffset(150);
        passText.getAnimation().setStartOffset(300);
        loginBtn.getAnimation().setStartOffset(450);
        newUserBtn.getAnimation().setStartOffset(600);
        scanCode.getAnimation().setStartOffset(750);

        newUserBtn.setOnClickListener(view -> {
            loginText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            emailText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            passText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            loginBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            newUserBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            scanCode.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));


            new Handler().postDelayed(() -> getParentFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .replace(R.id.main_frag_container, NewUserFrag.class, null)
                    .commit(), 1000);
        });

        loginBtn.setOnClickListener(view ->{
            TextInputEditText emailEdit = binding.loginEmailEdit;
            TextInputEditText passEdit = binding.loginPasswordEdit;
            String email = emailEdit.getText().toString();
            String pass = passEdit.getText().toString();

            if (email.equals("") && pass.equals("")) {
                Toast.makeText(getContext(), "Please Fill All Fields", Toast.LENGTH_SHORT).show();
            } else {
                attemptLogin(email, pass);

            }
        });

        scanCode.setOnClickListener(view ->{
            loginText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            emailText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            passText.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            loginBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            newUserBtn.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));
            scanCode.setAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.login_nav_away));


            new Handler().postDelayed(() -> getParentFragmentManager().beginTransaction()
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .replace(R.id.main_frag_container, QrScanFrag.class, null)
                    .commit(), 1000);
        });

        return binding.getRoot();
    }

//        Attempt login, return UID if login success.
        public void attemptLogin(String email, String pass) {

//        Source the activity
            MainActivity activity = (MainActivity)getActivity();
            mAuth = FirebaseAuth.getInstance();

//        If entered data, attempt auth, and send to new fragment
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            Log.d("LOGIN", "signInWithEmailAndPass:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getContext(), "Authentication successful", Toast.LENGTH_SHORT).show();
                                        updateUI(user);
                        } else {
                            Log.w("LOGIN", "signInWithEmailAndPass:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                                        updateUI(null);
                        }
                    });
                }

    public void updateUI(FirebaseUser user) {
        vm.setUser(user);

        new Handler().postDelayed(() -> getParentFragmentManager().beginTransaction()
                .addToBackStack(null)
                .setReorderingAllowed(true)
                .replace(R.id.main_frag_container, SignedInProfileFrag.class,null)
                .commit(), 1000);
    }

}
