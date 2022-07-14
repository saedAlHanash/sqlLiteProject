package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sqlliteproject.Adadpters.AdapterOutlay;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.OutlayJoin;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutlayServiceFragment extends Fragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.count)
    TextView count;

    View view;

    DataBaseAccess db;

    AdapterOutlay adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outlay_material, container, false);
        ButterKnife.bind(this, view);
        db = DataBaseAccess.getInstance(requireContext());

        getDataFromDB();

        return view;
    }

    void getDataFromDB() {
        initAdapter(db.getMaterialOutlay(1));
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