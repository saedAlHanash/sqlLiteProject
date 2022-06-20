package com.example.sqlliteproject.DataBases;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sqlliteproject.DataBases.Models.Material;
import com.example.sqlliteproject.DataBases.Models.Outlay;
import com.example.sqlliteproject.DataBases.Models.OutlayJoin;
import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.DataBases.Tabels.TableMaterial;
import com.example.sqlliteproject.DataBases.Tabels.TableOutlay;
import com.example.sqlliteproject.DataBases.Tabels.TableOutlayOwner;

import java.util.ArrayList;


public class DataBaseAccess {

    SQLiteDatabase dataBase;
    SQLiteOpenHelper openHelper;


    private static DataBaseAccess instance;

    private DataBaseAccess(Context context) {
        this.openHelper = new MyDataBase(context);
    }

    public static DataBaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void openWritable() {
        dataBase = this.openHelper.getWritableDatabase();
    }

    public void openReadable() {
        dataBase = this.openHelper.getReadableDatabase();
    }

    public void close() {
        if (this.dataBase != null)
            this.dataBase.close();
    }

    //region insert
    public boolean insertMaterial(Material material) {
        openWritable();
        ContentValues values = addMaterialHelper(material);
        long result = dataBase.insert(TableMaterial.TABLE_NAME, null, values);
        close();
        return result != -1;
    }

    public boolean insertOwner(Owner owner) {
        openWritable();
        ContentValues values = addOwnerHelper(owner);
        long result = dataBase.insert(TableOutlayOwner.TABLE_NAME, null, values);
        close();
        return result != -1;
    }

    public boolean insertOutlay(Outlay outlay) {
        openWritable();
        ContentValues values = addOutlayHelper(outlay);
        long result = dataBase.insert(TableOutlay.TABLE_NAME, null, values);
        close();
        return result != -1;
    }


    //endregion

    //region update
    public boolean updateMaterial(Material material) {
        openWritable();

        ContentValues values = addMaterialHelper(material);
        String[] args = {String.valueOf(material.id)};

        int result = dataBase.update(TableMaterial.TABLE_NAME, values,
                TableMaterial.ID + "=?", args);
        close();

        return result == 1;
    }

    public boolean updateOwner(Owner owner) {
        openWritable();

        ContentValues values = addOwnerHelper(owner);
        String[] args = {String.valueOf(owner.id)};

        int result = dataBase.update(TableOutlayOwner.TABLE_NAME, values,
                TableOutlayOwner.ID + "=?", args);
        close();

        return result != -1;
    }

    public boolean updateOutlay(Outlay outlay) {
        openWritable();

        ContentValues values = addOutlayHelper(outlay);
        String[] args = {String.valueOf(outlay.id)};

        int result = dataBase.update(TableOutlay.TABLE_NAME, values,
                TableOutlay.ID + "=?", args);
        close();

        return result != -1;
    }

    public boolean updateOutlay(OutlayJoin outlayJoin) {
        Outlay outlay = new Outlay(outlayJoin);

        openWritable();

        ContentValues values = addOutlayHelper(outlay);
        String[] args = {String.valueOf(outlay.id)};

        int result = dataBase.update(TableOutlay.TABLE_NAME, values,
                TableOutlay.ID + "=?", args);
        close();

        return result != -1;
    }

    //endregion

    //region delete
    public boolean deleteMaterial(Material material) {
        openWritable();
        String[] args = {material.id + ""};

        int result = dataBase.delete(TableMaterial.TABLE_NAME,
                TableMaterial.ID + "=?", args);
        close();
        return result == 1;
    }

    public boolean deleteOwner(Owner owner) {
        openWritable();
        String[] args = {owner.id + ""};

        int result = dataBase.delete(TableOutlayOwner.TABLE_NAME,
                TableOutlayOwner.ID + "=?", args);
        close();
        return result == 1;
    }

    public boolean deleteOutlay(int outlayId) {
        openWritable();
        String[] args = {outlayId + ""};

        int result = dataBase.delete(TableOutlay.TABLE_NAME,
                TableOutlay.ID + "=?", args);
        close();
        return result == 1;
    }


    //endregion

    //region getAll
    @SuppressLint("Range")
    public ArrayList<Material> getAlMaterial() {
        openReadable();
        ArrayList<Material> materials = new ArrayList<>();
        Cursor cursor = null;

        cursor = dataBase.rawQuery("SELECT * FROM " + TableMaterial.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Material material = new Material();
                material.id = cursor.getInt(cursor.getColumnIndex(TableMaterial.ID));
                int s = cursor.getInt(cursor.getColumnIndex(TableMaterial.IS_SERVICE));

                if (s == 0)
                    material.isService = false;
                else
                    material.isService = true;

                material.description = cursor.getString(cursor.getColumnIndex(TableMaterial.DESCRIPTION));
                material.name = cursor.getString(cursor.getColumnIndex(TableMaterial.NAME));
                materials.add(material);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return materials;
    }

    @SuppressLint("Range")
    public ArrayList<Owner> getAllOwners() {
        openReadable();
        ArrayList<Owner> owners = new ArrayList<>();
        Cursor cursor = null;

        cursor = dataBase.rawQuery("SELECT * FROM " + TableOutlayOwner.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Owner owner = new Owner();
                owner.id = cursor.getInt(cursor.getColumnIndex(TableOutlayOwner.ID));
                owner.description = cursor.getString(cursor.getColumnIndex(TableOutlayOwner.DESCRIPTION));
                owner.name = cursor.getString(cursor.getColumnIndex(TableOutlayOwner.NAME));
                owners.add(owner);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return owners;
    }

    @SuppressLint("Range")
    public ArrayList<Outlay> getAllOutlay() {
        openReadable();
        ArrayList<Outlay> outlays = new ArrayList<>();

        Cursor cursor = null;

        cursor = dataBase.rawQuery("SELECT * FROM " + TableOutlay.TABLE_NAME, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay();
                outlay.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                outlay.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                outlay.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                outlay.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                outlay.description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                outlay.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                outlay.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                outlay.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                outlays.add(outlay);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<OutlayJoin> getAllOutlayJoin() {
        openReadable();
        ArrayList<OutlayJoin> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery(
                "SELECT " + TableMaterial.TABLE_NAME + "." + TableMaterial.NAME + "[materialName]" +
                        " , " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.NAME + "[ownerName]" +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.PRICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DESCRIPTION +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DAY +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.YEAR +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MATERIAL_ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.OUTLAY_OWNER_ID +

                        " FROM " + TableOutlay.TABLE_NAME +

                        " INNER JOIN " + TableMaterial.TABLE_NAME +
                        " ON " + TableMaterial.TABLE_NAME + "." + TableMaterial.ID + " = " + TableOutlay.MATERIAL_ID +

                        " INNER JOIN " + TableOutlayOwner.TABLE_NAME +
                        " ON " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.ID + " = " + TableOutlay.OUTLAY_OWNER_ID

                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OutlayJoin join = new OutlayJoin();
                join.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                join.outlay_description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                join.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                join.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                join.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                join.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                join.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                join.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                join.material_name = cursor.getString(cursor.getColumnIndex("materialName"));
                join.owner_name = cursor.getString(cursor.getColumnIndex("ownerName"));
                outlays.add(join);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return outlays;
    }


    //endregion


    @SuppressLint("Range")
    public ArrayList<OutlayJoin> getMonthJoin(int month) {
        openReadable();
        ArrayList<OutlayJoin> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery(
                "SELECT " + TableMaterial.TABLE_NAME + "." + TableMaterial.NAME + "[materialName]" +
                        " , " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.NAME + "[ownerName]" +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.PRICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DESCRIPTION +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DAY +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.YEAR +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MATERIAL_ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.OUTLAY_OWNER_ID +

                        " FROM " + TableOutlay.TABLE_NAME +

                        " INNER JOIN " + TableMaterial.TABLE_NAME +
                        " ON " + TableMaterial.TABLE_NAME + "." + TableMaterial.ID + " = " + TableOutlay.MATERIAL_ID +

                        " INNER JOIN " + TableOutlayOwner.TABLE_NAME +
                        " ON " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.ID + " = " + TableOutlay.OUTLAY_OWNER_ID +

                        " WHERE " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH + "=" + month

                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OutlayJoin join = new OutlayJoin();
                join.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                join.outlay_description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                join.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                join.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                join.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                join.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                join.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                join.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                join.material_name = cursor.getString(cursor.getColumnIndex("materialName"));
                join.owner_name = cursor.getString(cursor.getColumnIndex("ownerName"));
                outlays.add(join);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<OutlayJoin> getYearJoin(int year) {
        openReadable();
        ArrayList<OutlayJoin> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery(
                "SELECT " + TableMaterial.TABLE_NAME + "." + TableMaterial.NAME + "[materialName]" +
                        " , " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.NAME + "[ownerName]" +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.PRICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DESCRIPTION +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DAY +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.YEAR +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MATERIAL_ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.OUTLAY_OWNER_ID +

                        " FROM " + TableOutlay.TABLE_NAME +

                        " INNER JOIN " + TableMaterial.TABLE_NAME +
                        " ON " + TableMaterial.TABLE_NAME + "." + TableMaterial.ID + " = " + TableOutlay.MATERIAL_ID +

                        " INNER JOIN " + TableOutlayOwner.TABLE_NAME +
                        " ON " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.ID + " = " + TableOutlay.OUTLAY_OWNER_ID +

                        " WHERE " + TableOutlay.YEAR + "=" + year

                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OutlayJoin join = new OutlayJoin();
                join.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                join.outlay_description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                join.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                join.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                join.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                join.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                join.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                join.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                join.material_name = cursor.getString(cursor.getColumnIndex("materialName"));
                join.owner_name = cursor.getString(cursor.getColumnIndex("ownerName"));
                outlays.add(join);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<OutlayJoin> getOwnerOutlay(int ownerId) {
        openReadable();
        ArrayList<OutlayJoin> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery(
                "SELECT " + TableMaterial.TABLE_NAME + "." + TableMaterial.NAME + "[materialName]" +
                        " , " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.NAME + "[ownerName]" +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.PRICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DESCRIPTION +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DAY +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.YEAR +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MATERIAL_ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.OUTLAY_OWNER_ID +

                        " FROM " + TableOutlay.TABLE_NAME +

                        " INNER JOIN " + TableMaterial.TABLE_NAME +
                        " ON " + TableMaterial.TABLE_NAME + "." + TableMaterial.ID + " = " + TableOutlay.MATERIAL_ID +

                        " INNER JOIN " + TableOutlayOwner.TABLE_NAME +
                        " ON " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.ID + " = " + TableOutlay.OUTLAY_OWNER_ID +

                        " WHERE " + TableOutlay.OUTLAY_OWNER_ID + "=" + ownerId

                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OutlayJoin join = new OutlayJoin();
                join.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                join.outlay_description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                join.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                join.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                join.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                join.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                join.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                join.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                join.material_name = cursor.getString(cursor.getColumnIndex("materialName"));
                join.owner_name = cursor.getString(cursor.getColumnIndex("ownerName"));
                outlays.add(join);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<OutlayJoin> getMaterialOutlay(int isServic) {
        openReadable();
        ArrayList<OutlayJoin> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery(
                "SELECT " + TableMaterial.TABLE_NAME + "." + TableMaterial.NAME + "[materialName]" +
                        " , " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.NAME + "[ownerName]" +
                        " , " + TableMaterial.TABLE_NAME + "." + TableMaterial.IS_SERVICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.PRICE +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DESCRIPTION +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.DAY +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MONTH +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.YEAR +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.MATERIAL_ID +
                        " , " + TableOutlay.TABLE_NAME + "." + TableOutlay.OUTLAY_OWNER_ID +

                        " FROM " + TableOutlay.TABLE_NAME +

                        " INNER JOIN " + TableMaterial.TABLE_NAME +
                        " ON " + TableMaterial.TABLE_NAME + "." + TableMaterial.ID + " = " + TableOutlay.MATERIAL_ID +

                        " INNER JOIN " + TableOutlayOwner.TABLE_NAME +
                        " ON " + TableOutlayOwner.TABLE_NAME + "." + TableOutlayOwner.ID + " = " + TableOutlay.OUTLAY_OWNER_ID +

                        " WHERE " + TableMaterial.IS_SERVICE + " = " + isServic

                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                OutlayJoin join = new OutlayJoin();
                join.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                join.outlay_description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                join.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                join.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                join.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                join.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                join.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                join.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                join.material_name = cursor.getString(cursor.getColumnIndex("materialName"));
                join.owner_name = cursor.getString(cursor.getColumnIndex("ownerName"));
                outlays.add(join);
            } while (cursor.moveToNext());
            cursor.close();
        }
        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<Outlay> getMonthOutlay(int month) {
        openReadable();
        ArrayList<Outlay> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery("SELECT * FROM " + TableOutlay.TABLE_NAME +
                        " WHERE " + TableOutlay.MONTH + "=" + month
                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay();
                outlay.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                outlay.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                outlay.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                outlay.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                outlay.description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                outlay.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                outlay.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                outlay.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                outlays.add(outlay);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return outlays;
    }

    @SuppressLint("Range")
    public ArrayList<Outlay> getYearOutlay(int year) {
        openReadable();
        ArrayList<Outlay> outlays = new ArrayList<>();
        Cursor cursor;

        cursor = dataBase.rawQuery("SELECT * FROM " + TableOutlay.TABLE_NAME +
                        " WHERE " + TableOutlay.YEAR + "=" + year
                , null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Outlay outlay = new Outlay();
                outlay.id = cursor.getInt(cursor.getColumnIndex(TableOutlay.ID));
                outlay.materialId = cursor.getInt(cursor.getColumnIndex(TableOutlay.MATERIAL_ID));
                outlay.ownerId = cursor.getInt(cursor.getColumnIndex(TableOutlay.OUTLAY_OWNER_ID));
                outlay.month = cursor.getInt(cursor.getColumnIndex(TableOutlay.MONTH));
                outlay.description = cursor.getString(cursor.getColumnIndex(TableOutlay.DESCRIPTION));
                outlay.price = cursor.getFloat(cursor.getColumnIndex(TableOutlay.PRICE));
                outlay.day = cursor.getInt(cursor.getColumnIndex(TableOutlay.DAY));
                outlay.year = cursor.getInt(cursor.getColumnIndex(TableOutlay.YEAR));
                outlays.add(outlay);
            } while (cursor.moveToNext());
            cursor.close();
        }

        close();
        return outlays;
    }


    //region insertHelper
    private ContentValues addMaterialHelper(Material material) {
        ContentValues values = new ContentValues();

        values.put(TableMaterial.NAME, material.name);
        values.put(TableMaterial.DESCRIPTION, material.description);

        if (material.isService)
            values.put(TableMaterial.IS_SERVICE, 1);
        else
            values.put(TableMaterial.IS_SERVICE, 0);

        return values;
    }

    private ContentValues addOwnerHelper(Owner owner) {
        ContentValues values = new ContentValues();
        values.put(TableOutlayOwner.NAME, owner.name);
        values.put(TableOutlayOwner.DESCRIPTION, owner.description);

        return values;
    }

    private ContentValues addOutlayHelper(Outlay outlay) {
        ContentValues values = new ContentValues();

        values.put(TableOutlay.MATERIAL_ID, outlay.materialId);
        values.put(TableOutlay.OUTLAY_OWNER_ID, outlay.ownerId);
        values.put(TableOutlay.PRICE, outlay.price);
        values.put(TableOutlay.DAY, outlay.day);
        values.put(TableOutlay.MONTH, outlay.month);
        values.put(TableOutlay.YEAR, outlay.year);
        values.put(TableOutlay.DESCRIPTION, outlay.description);

        return values;
    }

    //endregion

}
