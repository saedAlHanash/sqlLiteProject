package com.example.sqlliteproject.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;


import com.example.sqlliteproject.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.login)
    AppCompatButton login;

    @BindView(R.id.editTextTextPersonName)
    EditText userName;
    @BindView(R.id.editTextTextPassword)
    EditText pass;

    View view;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, view);
        mainActivity = (MainActivity) requireActivity();

        login.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        mainActivity.replaceFragment(new MainFragment());
    }
}