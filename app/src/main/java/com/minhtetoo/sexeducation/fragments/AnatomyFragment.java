package com.minhtetoo.sexeducation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minhtetoo.sexeducation.R;

public class AnatomyFragment extends BaseFragment {

    public AnatomyFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_anatomy,container,false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
