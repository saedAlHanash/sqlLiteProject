package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class MainFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.material)
    Button material;
    @BindView(R.id.owner)
    Button owner;
    @BindView(R.id.outlay)
    Button outlay;
    @BindView(R.id.log)
    Button log;
    MainActivity mainActivity;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        mainActivity = (MainActivity) requireActivity();

        material.setOnClickListener(this);
        owner.setOnClickListener(this);
        outlay.setOnClickListener(this);
        log.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.material: {
                mainActivity.addFragment(new MaterialFragment());
                break;
            }
            case R.id.owner: {
                mainActivity.addFragment(new OwnerFragment());
                break;
            }
            case R.id.outlay: {
                mainActivity.addFragment(new OutlayFragment());
                break;
            }
            case R.id.log: {
                mainActivity.addFragment(new LogFragment());
                break;
            }
        }
    }
}