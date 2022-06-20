package com.example.sqlliteproject.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import com.example.sqlliteproject.DataBases.FTH;
import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.FCV)
    FragmentContainerView FCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        FTH.replaceFragment(R.id.FCV, this, new AuthFragment());
    }
}