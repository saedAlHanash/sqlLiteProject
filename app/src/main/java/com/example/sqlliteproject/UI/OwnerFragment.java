package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlliteproject.Adadpters.AdapterOwner;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OwnerFragment extends Fragment {

    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.add)
    Button addMaterial;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    DataBaseAccess db;

    AdapterOwner adapter;
    Owner owner;
    int idAdapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_owner, container, false);
        ButterKnife.bind(this, view);
        db = DataBaseAccess.getInstance(requireContext());

        getDataFromDB();

        listeners();

        return view;
    }

    void listeners() {
        addMaterial.setOnClickListener(addListener);
        edit.setOnClickListener(editListener);
        delete.setOnClickListener(deleteListener);
    }

    void initAdapter(ArrayList<Owner> list) {
        if (adapter == null)
            adapter = new AdapterOwner(requireActivity(), list);
        else
            adapter.setAndRefresh(list);

        adapter.setOnItemClicked(onItemClicked);

        initRecycler();
    }

    void initRecycler() {
        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }

    void getDataFromDB() {
        initAdapter(db.getAllOwners());
    }

    private final View.OnClickListener addListener = v -> {
        if (checkFields()) {

            Owner owner = new Owner(name.getText().toString(), description.getText().toString());

            if (db == null)
                return;

            boolean test = db.insertOwner(owner);

            if (test) {
                adapter.insertItem(owner);
                getDataFromDB();
                Toast.makeText(requireContext(), "added", Toast.LENGTH_SHORT).show();
                restFields();
            }
        } else {
            // TODO: 20/06/2022 handel onFailure
        }

    };

    private final View.OnClickListener deleteListener = v -> {
        if (db.deleteOwner(this.owner)) {
            adapter.deleteItem(idAdapter);
            restFields();
        } else {
            // TODO: 20/06/2022 handel onFailure
        }
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    };

    private final View.OnClickListener editListener = v -> {
        if (!checkFields())
            return;

        this.owner.name = name.getText().toString();
        this.owner.description = description.getText().toString();

        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

        if (db.updateOwner(owner)) {
            restFields();
            adapter.editItem(idAdapter, owner);
            Toast.makeText(requireContext(), "done edit", Toast.LENGTH_SHORT).show();
        } else {
            // TODO: 20/06/2022 handel onFailure
        }
    };

    private final AdapterOwner.OnItemClicked onItemClicked = (position, list) -> {
        if (edit.getVisibility() != View.VISIBLE)
            edit.setVisibility(View.VISIBLE);

        if (delete.getVisibility() != View.VISIBLE)
            delete.setVisibility(View.VISIBLE);

        idAdapter = position;
        setFields(list.get(position));
    };

    void restFields() {
        name.setText(null);
        description.setText(null);
    }

    void setFields(Owner owner) {
        this.owner = owner;
        name.setText(owner.name);
        description.setText(owner.description);

    }

    private boolean checkFields() {
        if (name.getText().toString().isEmpty()) {
            name.setError("");
            return false;
        }
        return true;

    }

}