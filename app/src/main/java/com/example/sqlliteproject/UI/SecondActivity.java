package com.example.sqlliteproject.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlliteproject.DataBases.DataBaseAccess;
import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.R;

public class SecondActivity extends AppCompatActivity {

    EditText editText;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        DataBaseAccess dataBaseAccess = DataBaseAccess.getInstance(this);

        button.setOnClickListener(v -> {
            boolean s = dataBaseAccess.insertOwner(new Owner(editText.getText().toString(), " jbd"));
            Log.d("SAED__", "onCreate: " + s);
        });
        findViewById(R.id.outlay).setOnClickListener(v -> {
            Log.d("SAED__", "onCreate: "+dataBaseAccess.getAllOwners().toString());  ;
        });

    }
}