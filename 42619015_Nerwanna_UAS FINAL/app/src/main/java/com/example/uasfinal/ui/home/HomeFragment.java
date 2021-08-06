package com.example.uasfinal.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.uasfinal.MainActivity;
import com.example.uasfinal.R;

public class HomeFragment extends Fragment {
    private static final int TEXT_REQUEST = 1;
    private EditText mWebsiteEditText, mCallingEditText, mShareTextEditText;
    public static final String EXTRA_MESSAGE = "com.e.android.twoactivities.extra.MESSAGE";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int PERMISSIONS_REQUEST_PHONE_CALL = 1;
    Button share_text_button,open_website_button,call_button;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mWebsiteEditText = (EditText) root.findViewById(R.id.website_edittext);
        mCallingEditText = (EditText) root.findViewById(R.id.calling_edittext);
        mShareTextEditText = (EditText) root.findViewById(R.id.share_edittext);
        share_text_button = root.findViewById(R.id.share_text_button);
        open_website_button = root.findViewById(R.id.open_website_button);
        call_button = root.findViewById(R.id.call_button);

        share_text_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt = mShareTextEditText.getText().toString();
                String mimeType = "text/plain";
                ShareCompat.IntentBuilder
                        .from(getActivity())
                        .setType(mimeType)
                        .setChooserTitle("Share this text with: ")
                        .setText(txt)
                        .startChooser();
            }
        });
        open_website_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mWebsiteEditText.getText().toString();
                Uri webpage = Uri.parse(url);

                Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivity(intent);
                }else {
                    Log.d("Implicit Intents", "Cant't handle this!");
                }
            }
        });
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = mCallingEditText.getText().toString();
                Uri phone = Uri.parse("tel:" + phoneNumber);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && getActivity().checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PERMISSIONS_REQUEST_PHONE_CALL);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL, phone);
                    if (callIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(callIntent);
                    }else {
                        Log.d("Implicit Intents", "Cant't handle this!");
                    }
                }
            }
        });
        return root;
    }
}