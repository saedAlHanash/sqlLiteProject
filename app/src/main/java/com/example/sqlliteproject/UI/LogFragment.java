package com.example.sqlliteproject.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.sqlliteproject.DataBases.FTH;
import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class LogFragment extends Fragment {

    @BindView(R.id.month)
    Button month;
    @BindView(R.id.owner)
    Button owner;
    @BindView(R.id.material)
    Button material;
    @BindView(R.id.service)
    Button service;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log, container, false);
        ButterKnife.bind(this, view);

        month.setOnClickListener(v -> {
            FTH.addToStakeFragment(R.id.FCV, requireActivity(), new OutlayInMonthFragment(), "omf");
        });

        owner.setOnClickListener(v -> {
            FTH.addToStakeFragment(R.id.FCV, requireActivity(), new OutlayOwnerFragment(), "oofn");
        });

        material.setOnClickListener(v -> {
            FTH.addToStakeFragment(R.id.FCV, requireActivity(), new OutlayMaterialFragment(), "omfn");

        });

        service.setOnClickListener(v -> {
            FTH.addToStakeFragment(R.id.FCV, requireActivity(), new outlayServiceFragment(), "omfn");
        });

        return view;
    }

}