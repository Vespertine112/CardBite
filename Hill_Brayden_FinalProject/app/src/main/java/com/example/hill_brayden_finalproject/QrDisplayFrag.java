package com.example.hill_brayden_finalproject;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hill_brayden_finalproject.databinding.QrDisplayFragBinding;
import com.example.hill_brayden_finalproject.models.User;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrDisplayFrag extends Fragment {
    private UserViewModel vm;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    public QrDisplayFrag() {
        super(R.layout.qr_display_frag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        QrDisplayFragBinding binding = QrDisplayFragBinding.inflate(getLayoutInflater());

        /*
         *  As This was a technology I learned for this app, this code is based on a tutorial which
         *  I followed, however I do understand how it works.
         *
         *  Further, I utilized an exterior package to assist in the QR generation.
         */

//        Vars from binding
        ImageView qrImage = binding.qrCode;
        TextView title = binding.qrTitle;

//        Create window manager
        WindowManager manager = (WindowManager) requireActivity().getSystemService(WINDOW_SERVICE);

//        Get the display from the window manager || The display represents all usable application area WITHOUT the system 'decor'
        Display display = manager.getDefaultDisplay();

//        Create a point for reference in display
        Point point = new Point();
        display.getSize(point);

        int wid = point.x;
        int height = point.y;
        int dimensions = Math.max(wid, height);

        //        Setting size for sub-boxes
        dimensions = dimensions * 3 / 4;

//        Grab UID
        User user = vm.sendbackUser.getValue();
        String UID = user.getUID();

//        Use the encoder to create a qr obj
        qrgEncoder = new QRGEncoder(UID, null, QRGContents.Type.TEXT, dimensions);
        try {
//            Export as bitmap and set image
            bitmap = qrgEncoder.encodeAsBitmap();
            qrImage.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        title.setText(user.getName() + getString(R.string.qr_display_title));

        return binding.getRoot();
    }
}
