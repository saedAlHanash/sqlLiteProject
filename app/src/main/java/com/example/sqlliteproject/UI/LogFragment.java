package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")

public class LogFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.month)
    Button month;
    @BindView(R.id.owner)
    Button owner;
    @BindView(R.id.material)
    Button material;
    @BindView(R.id.service)
    Button service;
    MainActivity mainActivity;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, view);
        mainActivity = (MainActivity) requireActivity();

        month.setOnClickListener(this);
        owner.setOnClickListener(this);
        material.setOnClickListener(this);
        service.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.month: {
                mainActivity.addFragment(new OutlayInMonthFragment());
                break;
            }
            case R.id.owner: {
                mainActivity.addFragment(new OutlayOwnerFragment());
                break;
            }
            case R.id.material: {
                mainActivity.addFragment(new OutlayMaterialFragment());
                break;
            }
            case R.id.service: {
                mainActivity.addFragment(new OutlayServiceFragment());
                break;
            }
        }
    }
}