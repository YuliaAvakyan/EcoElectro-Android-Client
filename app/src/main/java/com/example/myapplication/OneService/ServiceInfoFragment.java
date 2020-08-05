package com.example.myapplication.OneService;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myapplication.InfoFragment;
import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

public class ServiceInfoFragment extends Fragment {
    MaterialButton req_button;
    public final static String TAG = "Info";

    private OnFragment1DataListener mListener;
    String text;

    public static ServiceInfoFragment newInstance() {
        return new ServiceInfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_service_info, container, false);


        text = getArguments().getString("send_id");

        req_button = v.findViewById(R.id.button_send);
        req_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOpenSendReq(text);
            }
        });

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragment1DataListener) {
            mListener = (OnFragment1DataListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragment1DataListener");
        }
    }

}