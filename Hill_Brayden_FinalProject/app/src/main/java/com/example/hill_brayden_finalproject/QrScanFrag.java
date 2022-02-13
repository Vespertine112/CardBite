package com.example.hill_brayden_finalproject;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.example.hill_brayden_finalproject.databinding.QrFragBinding;


public class QrScanFrag extends Fragment {
    private CodeScanner mCodeScanner;
    private UserViewModel vm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        Create a binding and a CodeScanner obj.
        QrFragBinding binding = QrFragBinding.inflate(getLayoutInflater());
        CodeScannerView scannerView = binding.scannerView;
        mCodeScanner = new CodeScanner(requireActivity(), scannerView);
        mCodeScanner.setCamera(CodeScanner.CAMERA_BACK);
        mCodeScanner.setFormats(CodeScanner.ALL_FORMATS);
        mCodeScanner.setAutoFocusEnabled(false);
        mCodeScanner.setFlashEnabled(false);

//        Logic to resolve decode actions
        mCodeScanner.setDecodeCallback(result -> {
            if (vm.attemptUidFetch(result.getText())) {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireActivity(), "Scan Success!", Toast.LENGTH_SHORT).show();
                    System.out.println("SCANNED UID OF " + result.getText() + " ||||||");
                    updateUI();
                });
            } else {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(requireActivity(), "Scan Failure!", Toast.LENGTH_SHORT).show();
                    requireActivity().onBackPressed();
                });
            }
        });

//        Start the preview when view is created.
        mCodeScanner.startPreview();
        return binding.getRoot();
    }

//    If app resumes, restart the preview
    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

//    Release resources if left
    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

//    Helper method to update frag if valid QR code scanned
    public void updateUI() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getParentFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .setReorderingAllowed(true)
                        .replace(R.id.main_frag_container, ScanUserFrag.class,null)
                        .commit();
            }
        },800);

    }
}