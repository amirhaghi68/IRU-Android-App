package com.example.IRUAndroidApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.IRUAndroidApp.R;
import com.example.IRUAndroidApp.models.Schedule;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.itemsAdapterViewHolder> {
    Context context;
    List<Schedule> schedule;
    String location;
    public String monthName;

    public ItemsAdapter(Context context,List<Schedule> schedule) {
        this.context = context;
        this.schedule = schedule;
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
        position = holder.getAdapterPosition();
        Schedule scheduleItem = schedule.get(position);
        location = "کلاس"+" ("+schedule.get(position).getClassNumber()+") "+schedule.get(position).getLocation();
        holder.professorName.setText(schedule.get(position).getProfessorName());
        holder.courseName.setText(schedule.get(position).getCourseName());
        holder.tvLocation.setText(location);
        //holder.tvCancel.setText(schedule.get(position).getType());
        holder.tvTime.setText(schedule.get(position).getTime());


        if (scheduleItem.getType().equals("جبرانی")) {
            holder.tvCancel.setBackgroundColor(context.getResources().getColor(R.color.green));
        } else {
            holder.tvCancel.setBackgroundColor(context.getResources().getColor(android.R.color.holo_red_dark));
        }
        holder.tvCancel.setText(scheduleItem.getType());






    }

    @Override
    public int getItemCount() {
        return schedule.size();
    }

    public class itemsAdapterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llParent;
        public TextView tvCancel;
        TextView tvTime;
        TextView professorName;
        TextView tvLocation;
        TextView courseName;
        TextView tvDate;


        public itemsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            llParent = itemView.findViewById(R.id.ll_parent);
            tvCancel = itemView.findViewById(R.id.tv_cancel);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvLocation = itemView.findViewById(R.id.tv_location);
            courseName = itemView.findViewById(R.id.course_name);
            professorName = itemView.findViewById(R.id.professor_name);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }


}
