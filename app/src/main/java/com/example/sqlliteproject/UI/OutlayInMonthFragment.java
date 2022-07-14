package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlliteproject.Adadpters.AdapterOutlay;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.OutlayJoin;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutlayInMonthFragment extends Fragment implements View.OnClickListener {

    View view;

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.count)
    TextView count;

    AdapterOutlay adapter;
    DataBaseAccess dataBase;

    @BindView(R.id.go)
    Button mDo;
    @BindView(R.id.month)
    EditText month;

    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outlay_in_month, container, false);
        ButterKnife.bind(this, view);
        dataBase = DataBaseAccess.getInstance(requireContext());
        mainActivity = (MainActivity) requireActivity();

        mDo.setOnClickListener(this);

        return view;
    }

    void getDataFromDB(int c) {
        if (c <= 12)
            initAdapter(dataBase.getMonthJoin(c));
        else
            initAdapter(dataBase.getYearJoin(c));
    }


    @SuppressLint("SetTextI18n")
    void initAdapter(ArrayList<OutlayJoin> list) {

        float count1 = 0;
        for (OutlayJoin outlayJoin : list)
            count1 += outlayJoin.price;

        count.setText("المجموع :" + count1);

        if (adapter == null)
            adapter = new AdapterOutlay(requireActivity(), list);
        else
            adapter.setAndRefresh(list);

        initRecycler();
    }

    void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        String m = month.getText().toString();
        if (m.isEmpty())
            return;

        int month = Integer.parseInt(m);
        getDataFromDB(month);
    }
}