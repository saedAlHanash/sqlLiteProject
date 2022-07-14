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
public class OwnerFragment extends Fragment implements View.OnClickListener,AdapterOwner.OnItemClicked {


    @BindView(R.id.add)
    Button addMaterial;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.delete)
    Button delete;
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

        addMaterial.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }


    void getDataFromDB() {

        if (adapter == null)
            adapter = new AdapterOwner(requireActivity(), db.getAllOwners());
        else
            adapter.setAndRefresh(db.getAllOwners());

        adapter.setOnItemClicked(this);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add: {
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
                }

                break;
            }
            case R.id.edit: {
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
                }
                break;
            }
            case R.id.delete: {
                if (db.deleteOwner(this.owner)) {
                    adapter.deleteItem(idAdapter);
                    restFields();
                }
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                break;
            }

        }
    }

    @Override
    public void onClicked(int position, ArrayList<Owner> list) {
        if (edit.getVisibility() != View.VISIBLE)
            edit.setVisibility(View.VISIBLE);

        if (delete.getVisibility() != View.VISIBLE)
            delete.setVisibility(View.VISIBLE);

        idAdapter = position;
        setFields(list.get(position));
    }
}