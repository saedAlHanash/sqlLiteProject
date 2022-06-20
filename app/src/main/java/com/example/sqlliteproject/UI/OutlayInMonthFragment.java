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

public class OutlayInMonthFragment extends Fragment {

    View view;

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.count)
    TextView count;

    AdapterOutlay adapter;
    DataBaseAccess DB;

    @BindView(R.id.go)
    Button go;
    @BindView(R.id.month)
    EditText month;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outlay_in_month, container, false);
        ButterKnife.bind(this, view);
        DB = DataBaseAccess.getInstance(requireContext());


        go.setOnClickListener(v -> {
            String m = month.getText().toString();
            if (m.isEmpty())
                return;

            int month = Integer.parseInt(m);
            getDataFromDB(month);

        });

        return view;
    }

    void getDataFromDB(int c) {
        if (c <= 12)
            initAdapter(DB.getMonthJoin(c));
        else
            initAdapter(DB.getYearJoin(c));
    }


    @SuppressLint("SetTextI18n")
    void initAdapter(ArrayList<OutlayJoin> list) {

        float count1 = 0;
        for (OutlayJoin outlayJoin : list) {
            count1 += outlayJoin.price;
        }
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

}