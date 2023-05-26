package com.example.IRUAndroidApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IRUAndroidApp.R;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsAdapterViewHolder> {
    Context context;
    public ItemsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public itemsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item_cancel,null,false);
        return new itemsAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.itemsAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class itemsAdapterViewHolder extends RecyclerView.ViewHolder {
        public itemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
