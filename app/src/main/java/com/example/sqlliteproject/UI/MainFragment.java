package com.example.sqlliteproject.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.sqlliteproject.DataBases.FTH;
import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainFragment extends Fragment {

    View view;
    @BindView(R.id.material)
    Button material;
    @BindView(R.id.owner)
    Button owner;
    @BindView(R.id.outlay)
    Button outlay;
    @BindView(R.id.log)
    Button log;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        listeners();

        return view;
    }

    void listeners() {
        //اضافة مواد
        material.setOnClickListener(materialListener);
        //اضافة مالك
        owner.setOnClickListener(ownerListener);
        //اضافة مصروف
        outlay.setOnClickListener(outlayListener);
        // التقارير
        log.setOnClickListener(logListener);
    }

    //اضافة مواد
    private final View.OnClickListener materialListener = v -> {
        FTH.addToStakeFragment(R.id.FCV, requireActivity(), new MaterialFragment(), "m_fn");
    };

    //اضافة مالك
    private final View.OnClickListener ownerListener = v -> {
        FTH.addToStakeFragment(R.id.FCV, requireActivity(), new OwnerFragment(), "o_fn");
    };

    //اضافة مصروف
    private final View.OnClickListener outlayListener = v -> {
        FTH.addToStakeFragment(R.id.FCV, requireActivity(), new OutlayFragment(), "ol_fn");
    };

    // التقارير
    private final View.OnClickListener logListener = v -> {
        FTH.addToStakeFragment(R.id.FCV, requireActivity(), new LogFragment(), "logfn");
    };


}