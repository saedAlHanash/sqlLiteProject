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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sqlliteproject.Adadpters.AdapterOutlay;
import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.Material;
import com.example.sqlliteproject.DataBases.Models.Outlay;
import com.example.sqlliteproject.DataBases.Models.OutlayJoin;
import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.R;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("NonConstantResourceId")
public class OutlayFragment extends Fragment implements View.OnClickListener, AdapterOutlay.OnItemClicked {

    @BindView(R.id.materials_id)
    Spinner spinnerMaterials;
    @BindView(R.id.owners_id)
    Spinner spinnerOwners;
    @BindView(R.id.add)
    Button addMaterial;
    @BindView(R.id.edit)
    Button edit;
    @BindView(R.id.delete)
    Button delete;

    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.recycler)
    RecyclerView recycler;

    DataBaseAccess db;
    AdapterOutlay adapter;
    OutlayJoin outlayJoin;
    int idAdapter;

    ArrayList<String> m_ids = new ArrayList<>();
    ArrayList<String> ow_ids = new ArrayList<>();

    ArrayList<Material> materials = new ArrayList<>();
    ArrayList<Owner> owners = new ArrayList<>();

    int selectedMaterialId;
    int selectedOwnerId;

    MainActivity mainActivity;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_outlay, container, false);
        ButterKnife.bind(this, view);
        db = DataBaseAccess.getInstance(requireContext());
        mainActivity = (MainActivity) requireActivity();

        getDataFromDB();

        initSpinners();


        addMaterial.setOnClickListener(this);
        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        return view;
    }


    private void initSpinners() {
        ArrayAdapter<String> a1 =
                new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, m_ids);
        ArrayAdapter<String> a2 =
                new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, ow_ids);

        spinnerMaterials.setAdapter(a1);
        spinnerOwners.setAdapter(a2);

        spinnerMaterials.setOnItemSelectedListener(materialSelectedListener);
        spinnerOwners.setOnItemSelectedListener(ownerSelectedListener);
    }

    private final AdapterView.OnItemSelectedListener materialSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedMaterialId = materials.get(position).id;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    private final AdapterView.OnItemSelectedListener ownerSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedOwnerId = owners.get(position).id;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            };

    void getDataFromDB() {

        this.materials = db.getAlMaterial();
        this.owners = db.getAllOwners();

        m_ids.clear();
        ow_ids.clear();
        for (Material material : this.materials)
            m_ids.add(material.name);

        for (Owner owner : this.owners)
            ow_ids.add(owner.name);

        if (adapter == null)
            adapter = new AdapterOutlay(requireActivity(), db.getAllOutlayJoin());
        else
            adapter.setAndRefresh(db.getAllOutlayJoin());

        adapter.setOnItemClicked(this);

        recycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        recycler.setAdapter(adapter);
    }


    private Outlay getOutlay() {
        return new Outlay(
                selectedMaterialId,
                selectedOwnerId,
                Integer.parseInt(price.getText().toString()),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                Calendar.getInstance().get(Calendar.MONTH) + 1,
                Calendar.getInstance().get(Calendar.YEAR),
                "");
    }

    void restViews() {
        price.setText(null);
        spinnerMaterials.setSelection(0);
        spinnerOwners.setSelection(0);
    }

    void initViews(OutlayJoin outlayJoin) {

        this.outlayJoin = outlayJoin;

        price.setText(String.valueOf(outlayJoin.price));

        spinnerMaterials.setSelection(m_ids.indexOf(this.outlayJoin.material_name));
        spinnerOwners.setSelection(ow_ids.indexOf(this.outlayJoin.owner_name));

    }

    boolean checkFields() {
        if (price.getText().toString().isEmpty()) {
            price.setError("");
            return false;
        }

        return spinnerMaterials.getSelectedItemPosition() >= 0 && spinnerOwners.getSelectedItemPosition() >= 0;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.add: {
                if (checkFields()) {

                    Outlay model = getOutlay();

                    if (db == null)
                        return;

                    boolean test = db.insertOutlay(model);
                    if (test) {
                        getDataFromDB();
                        Toast.makeText(requireContext(), " added", Toast.LENGTH_SHORT).show();
                        restViews();
                    }
                }
                break;
            }
            case R.id.edit: {
                if (!checkFields())
                    return;

                outlayJoin.price = Float.parseFloat(price.getText().toString());

                outlayJoin.outlay_description = "";

                outlayJoin.materialId = this.selectedMaterialId;
                outlayJoin.ownerId = this.selectedOwnerId;

                if (db.updateOutlay(outlayJoin)) {
                    restViews();
                    getDataFromDB();
                    Toast.makeText(requireContext(), "done edit", Toast.LENGTH_SHORT).show();
                }

                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                break;
            }
            case R.id.delete: {
                if (db.deleteOutlay(this.outlayJoin.id)) {
                    adapter.deleteItem(idAdapter);
                    restViews();
                }
                edit.setVisibility(View.GONE);
                delete.setVisibility(View.GONE);
                break;
            }

        }
    }

    @Override
    public void onClicked(int position, ArrayList<OutlayJoin> list) {

        if (edit.getVisibility() != View.VISIBLE)
            edit.setVisibility(View.VISIBLE);

        if (delete.getVisibility() != View.VISIBLE)
            delete.setVisibility(View.VISIBLE);

        idAdapter = position;

        initViews(list.get(position));
    }
}