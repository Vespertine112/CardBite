package com.example.hill_brayden_finalproject;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        UserViewModel vm = new ViewModelProvider(this).get(UserViewModel.class);

//        Check for current auth, update if currently logged in.
        FirebaseUser currentUser = mAuth.getCurrentUser();

//        DEBUGGING STATEMENT
        currentUser = null;

//        If current user is signed in, update user info in the viewModel
        if (currentUser != null) {
            vm.setUser(currentUser);
            vm.signedFlag = true;
        }

//        Send to splash screen
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .disallowAddToBackStack()
                    .replace(R.id.main_frag_container, SplashFragment.class, null)
                    .commit();
        }


        setContentView(binding.getRoot());
    }


    public FirebaseAuth getFireAuth() {
        return mAuth;
    }

}