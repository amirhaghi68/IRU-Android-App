package com.example.IRUAndroidApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IRUAndroidApp.R;
import com.example.IRUAndroidApp.models.CalendarDayModel;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {

    private Context context;
    private ArrayList<CalendarDayModel> data;

    public CalendarAdapter(Context context, ArrayList<CalendarDayModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_day_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalendarDayModel model = data.get(position);

        holder.dayTextView.setText(String.valueOf(model.day));
        holder.dayNameTextView.setText(model.dayName);

        if (model.isCurrentMonth) {
            holder.itemView.setEnabled(true);
        } else {
            holder.itemView.setEnabled(false);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayTextView;
        TextView dayNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayTextView = itemView.findViewById(R.id.dayTextView);
            dayNameTextView = itemView.findViewById(R.id.dayNameTextView);
        }
    }
}

