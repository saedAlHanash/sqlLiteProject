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
public class MaterialFragment extends Fragment implements View.OnClickListener, AdapterMaterial.OnItemClicked {

    @BindView(R.id.add_material)
    Button addMaterial;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.switch1)
    Switch switch1;
    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    DataBaseAccess dataBase;
    AdapterMaterial adapter;
    Material material;

    int idselected;

    MainActivity mainActivity;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_material, container, false);
        ButterKnife.bind(this, view);

        mainActivity = (MainActivity) requireActivity();

        dataBase = DataBaseAccess.getInstance(requireContext());

        getDataFromDB();

        addMaterial.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);

        return view;
    }

    void getDataFromDB() {

        if (adapter == null)
            adapter = new AdapterMaterial(requireActivity(), dataBase.getAlMaterial());
        else
            adapter.setAndRefresh(dataBase.getAlMaterial());

        adapter.setOnItemClicked(this);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }

    void restViews() {
        name.setText(null);
        description.setText(null);
        switch1.setChecked(false);
    }

    void initViews(Material material) {
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add_material: {
                if (checkFields()) {
                    Material material = new Material(name.getText().toString(),
                            description.getText().toString(), switch1.isChecked());

                    if (dataBase == null)
                        return;

                    boolean test = dataBase.insertMaterial(material);
                    if (test) {
                        adapter.insertItem(material);
                        getDataFromDB();
                        Toast.makeText(requireContext(), "material added", Toast.LENGTH_SHORT).show();
                        restViews();
                    }
                }
                break;
            }
            case R.id.edit: {
                if (!checkFields())
                    return;

                material.name = name.getText().toString();
                material.isService = switch1.isChecked();
                material.description = description.getText().toString();

                if (dataBase.updateMaterial(material)) {
                    restViews();
                    adapter.editItem(idselected, material);
                    Toast.makeText(requireContext(), "done edit", Toast.LENGTH_SHORT).show();
                }
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                break;
            }
            case R.id.delete: {
                if (dataBase.deleteMaterial(this.material)) {
                    adapter.deleteItem(idselected);
                    restViews();
                }
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                break;
            }

        }
    }

    @Override
    public void onClicked(int position, ArrayList<Material> list) {
        if (edit.getVisibility() != View.VISIBLE)
            edit.setVisibility(View.VISIBLE);
        if (delete.getVisibility() != View.VISIBLE)
            delete.setVisibility(View.VISIBLE);

        idselected = position;
        initViews(list.get(position));
    }
}
