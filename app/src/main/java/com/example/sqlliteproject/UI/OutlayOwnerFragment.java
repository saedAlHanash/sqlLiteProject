package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sqlliteproject.Adadpters.AdapterOutlay;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.FTH;
import com.example.sqlliteproject.DataBases.Models.Material;
import com.example.sqlliteproject.DataBases.Models.OutlayJoin;
import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OutlayOwnerFragment extends Fragment {

    @BindView(R.id.owners_id)
    Spinner spinnerOwners;

    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.count)
    TextView count;

    @BindView(R.id.go)
    Button go;


    View view;

    DataBaseAccess db;

    AdapterOutlay adapter;

    ArrayList<String> ow_ids = new ArrayList<>();

    ArrayList<Owner> owners = new ArrayList<>();

    int selectedOwnerId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outlay_owner, container, false);
        ButterKnife.bind(this, view);
        db = DataBaseAccess.getInstance(requireContext());

        getDataFromDB();

        initSpinners();

        go.setOnClickListener(v -> {

            if (selectedOwnerId < 0)
                return;
            initAdapter(db.getOwnerOutlay(selectedOwnerId));

        });

        return view;
    }

    //region spinners
    private void initSpinners() {
        ArrayAdapter<String> a1 =
                new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, ow_ids);

        spinnerOwners.setAdapter(a1);

        spinnerOwners.setOnItemSelectedListener(materialSelectedListener);

    }

    private final AdapterView.OnItemSelectedListener materialSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedOwnerId = owners.get(position).id;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };


    //endregion

    void getDataFromDB() {

        this.owners = db.getAllOwners();

        ow_ids.clear();
        for (Owner owner : this.owners)
            ow_ids.add(owner.name);
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