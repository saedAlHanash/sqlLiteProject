package com.example.sqlliteproject.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.sqlliteproject.DataBases.FTH;
import com.example.sqlliteproject.R;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthFragment extends Fragment {

    @BindView(R.id.sing_in_txt_bold)
    TextView singInTxtBold;
    @BindView(R.id.sing_in_text_view)
    TextView singInTextView;
    @BindView(R.id.sing_in_user_name)
    TextInputEditText userName;
    @BindView(R.id.sing_in_password)
    TextInputEditText password;
    @BindView(R.id.login)
    AppCompatButton login;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auth, container, false);
        ButterKnife.bind(this, view);

        login.setOnClickListener(v -> {
            FTH.replaceFragment(R.id.FCV, requireActivity(), new MainFragment());
        });

        return view;
    }
}