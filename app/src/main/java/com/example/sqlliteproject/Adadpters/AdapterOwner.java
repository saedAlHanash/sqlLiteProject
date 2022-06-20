package com.example.sqlliteproject.Adadpters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlliteproject.DataBases.Models.Owner;
import com.example.sqlliteproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
created on 20/06/2022 - 08:08 م
to project sqlLiteProject
*/
public class AdapterOwner extends RecyclerView.Adapter<AdapterOwner.Holder> {

    Activity activity;
    ArrayList<Owner> list;

    OnItemClicked listener;

    public AdapterOwner(Activity activity, ArrayList<Owner> list) {
        this.activity = activity;
        this.list = list;
    }

    /**
     * set new Item List to the adapter and refreshing all item
     *
     * @param list list of new Item {@link #list}
     */
    public void setAndRefresh(ArrayList<Owner> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner, parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        holder.id.setText("id : " + getItem(position).id);
        holder.name.setText(getItem(position).name);
        holder.description.setText(getItem(position).description);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.id)
        TextView id;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.description)
        TextView description;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(v -> {
                if (listener == null)
                    return;

                listener.onClicked(getAdapterPosition(), list);
            });
        }
    }

    /**
     * set and init listener to take an action when an item is pressed
     *
     * @param listener Object form interface @{@link OnItemClicked}
     */
    public void setOnItemClicked(OnItemClicked listener) {
        this.listener = listener;
    }

    public interface OnItemClicked {
        /**
         * to call it when item clicked
         *
         * @param position index item in adapter and list
         * @param list     items list of Owner
         */
        void onClicked(int position, ArrayList<Owner> list);
    }

    /**
     * get an item form {@link #list}
     *
     * @param i position adapter == (index in list)
     * @return object Owner from list {@link #list}
     */
    public Owner getItem(int i) {
        // return item in index i
        return this.list.get(i);
    }

    /**
     * delete an item form {@link #list}
     *
     * @param i position adapter == (index in list)
     */
    public void deleteItem(int i) {
        // delete item with index i in the list
        this.list.remove(i);
        this.notifyItemRemoved(i);
    }

    /**
     * editing an item form {@link #list}
     *
     * @param i    @param i position adapter == (index in list)
     * @param item new Item data
     */
    public void editItem(int i, Owner item) {
        // replace item in index i with new item
        this.list.set(i, item);
        this.notifyItemChanged(i);
    }

    /**
     * adding an item to begin {@link #list}
     *
     * @param item new Item to add it to the list
     */
    public void insertItem(Owner item) {
        // add item in begin of list
        this.list.add( item);
    }


}
