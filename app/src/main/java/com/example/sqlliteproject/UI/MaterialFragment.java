package com.example.sqlliteproject.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlliteproject.Adadpters.AdapterMaterial;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.Material;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("NonConstantResourceId")
public class MaterialFragment extends Fragment {

    @BindView(R.id.add_material)
    Button addMaterial;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    DataBaseAccess db;
    AdapterMaterial adapter;
    Material material;
    int idAdapter;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_material, container, false);
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

    void initAdapter(ArrayList<Material> list) {
        if (adapter == null)
            adapter = new AdapterMaterial(requireActivity(), list);
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
        initAdapter(db.getAlMaterial());
    }

    private final View.OnClickListener addListener = v -> {
        if (checkFields()) {
            Material material = new Material(name.getText().toString(),
                    description.getText().toString(), switch1.isChecked());

            if (db == null)
                return;

            boolean test = db.insertMaterial(material);
            if (test) {
                adapter.insertItem(material);
                getDataFromDB();
                Toast.makeText(requireContext(), "material added", Toast.LENGTH_SHORT).show();
                restFields();
            }
        }

    };

    private final View.OnClickListener editListener = v -> {
        if (!checkFields())
            return;

        material.name = name.getText().toString();
        material.isService = switch1.isChecked();
        material.description = description.getText().toString();

        if (db.updateMaterial(material)) {
            restFields();
            adapter.editItem(idAdapter, material);
            Toast.makeText(requireContext(), "done edit", Toast.LENGTH_SHORT).show();
        }
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    };

    private final View.OnClickListener deleteListener = v -> {
        if (db.deleteMaterial(this.material)) {
            adapter.deleteItem(idAdapter);
            restFields();
        }
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
    };

    private final AdapterMaterial.OnItemClicked onItemClicked = (position, list) -> {
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
        switch1.setChecked(false);
    }

    void setFields(Material material) {
        this.material = material;
        name.setText(material.name);
        description.setText(material.description);
        switch1.setChecked(material.isService);
    }

    private boolean checkFields() {
        if (name.getText().toString().isEmpty()) {
            name.setError("");
            return false;
        }
        return true;

    }


}
