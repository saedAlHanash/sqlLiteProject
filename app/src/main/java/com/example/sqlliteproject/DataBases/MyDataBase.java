package com.example.sqlliteproject.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.sqlliteproject.DataBases.Tabels.TableMaterial;
import com.example.sqlliteproject.DataBases.Tabels.TableOutlay;
import com.example.sqlliteproject.DataBases.Tabels.TableOutlayOwner;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MyDataBase extends SQLiteAssetHelper {
    public static final String DATA_BASE_NAME = "project.db";

    public static final int DATA_BASE_version = 1;
    Context context;

    public MyDataBase(@Nullable Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_version);
        this.context = context;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TableMaterial.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableOutlay.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TableOutlayOwner.TABLE_NAME);

        onCreate(db);
    }


}
