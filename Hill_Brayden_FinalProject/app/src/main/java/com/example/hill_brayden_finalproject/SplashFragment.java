package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.SplashFragBinding;

public class SplashFragment extends Fragment {
    Boolean transist = false;
    UserViewModel vm;

    public SplashFragment(){
        super(R.layout.splash_frag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SplashFragBinding binding = SplashFragBinding.inflate(getLayoutInflater());

//        Create views from bindings
        TextView card_splash = binding.cardSplash;
        TextView bite_splash = binding.biteSplash;
        TextView slogan_splash = binding.splashSlogan;
        View hr_bar = binding.horizontalBarLogo;

//        Set Logo Names
        String card = getResources().getString(R.string.splash_card);
        String bite = getResources().getString(R.string.splash_bite);
        String slogan = getResources().getString(R.string.slogan);

//        Load animations
        Animation float_down = AnimationUtils.loadAnimation(getContext(),R.anim.float_down);
        Animation float_up = AnimationUtils.loadAnimation(getContext(),R.anim.float_up);
        Animation hr_bar_alpha = AnimationUtils.loadAnimation(getContext(), R.anim.horizontal_bar);
        Animation slogan_anim = AnimationUtils.loadAnimation(getContext(),R.anim.slogan_anim);


//        Set text
        card_splash.setText(card);
        bite_splash.setText(bite);
        slogan_splash.setText(slogan);

//        Hook Animations
        card_splash.setAnimation(float_down);
        bite_splash.setAnimation(float_up);
        hr_bar.setAnimation(hr_bar_alpha);
        slogan_splash.setAnimation(slogan_anim);

//        Allow click through of splash screen
        binding.getRoot().setOnClickListener(v ->{
            transist = true;
            if (vm.signedFlag) {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .disallowAddToBackStack()
                        .replace(R.id.main_frag_container, SignedInProfileFrag.class, null)
                        .commit();
            } else {
                getParentFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .disallowAddToBackStack()
                        .replace(R.id.main_frag_container, LoginFrag.class, null)
                        .commit();
            }
        });

//        Move to login after ~5 seconds Async, remove splash from backStack
        new Handler().postDelayed(() -> {
//                Stop the transition of the click skipped the splashscreen
            if (!transist) {
                if (vm.signedFlag) {
                    getParentFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .disallowAddToBackStack()
                            .replace(R.id.main_frag_container, SignedInProfileFrag.class, null)
                            .commit();
                } else {
                    getParentFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .disallowAddToBackStack()
                            .replace(R.id.main_frag_container, LoginFrag.class, null)
                            .commit();
                }
            }
        }, 5000);

//        Return view
        return binding.getRoot();
    }
}
