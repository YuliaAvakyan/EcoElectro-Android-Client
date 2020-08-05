package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.RecyclerAndFilter.MyAdapter;

public class Services_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    //ArrayList<Service> services;
    MainActivity m;

    public static Services_Fragment newInstance() {
        return new Services_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.services_list_fragment, container, false);

        return v;
    }
}