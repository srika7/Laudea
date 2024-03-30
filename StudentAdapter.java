package com.example.laudea;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    private Context context;
    private List<StudentClass> StudentClassList;

    public StudentAdapter(Context context, List<StudentClass> StudentClassList) {
        this.context = context;
        this.StudentClassList = StudentClassList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Glide.with(context).load(StudentClassList.get(position).getDataImage()).into(holder.recImage);
        holder.recTitle.setText(StudentClassList.get(position).getDataTitle());
        holder.recDesc.setText(StudentClassList.get(position).getDataDesc());
        holder.recLang.setText(StudentClassList.get(position).getDataLang());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StudentdetailActivity.class);
                intent.putExtra("Image",StudentClassList.get(holder.getAdapterPosition()).getDataImage());
                intent.putExtra("Description",StudentClassList.get(holder.getAdapterPosition()).getDataDesc());
                intent.putExtra("Title", StudentClassList.get(holder.getAdapterPosition()).getDataTitle());
                intent.putExtra("Key",StudentClassList.get(holder.getAdapterPosition()).getKey());
                intent.putExtra("Language", StudentClassList.get(holder.getAdapterPosition()).getDataLang());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return StudentClassList.size();
    }

    public void searchDataList(ArrayList<StudentClass> searchList){
        StudentClassList = searchList;
        notifyDataSetChanged();
    }
}

class StudentViewHolder extends RecyclerView.ViewHolder{

    ImageView recImage;
    TextView recTitle, recDesc, recLang;
    CardView recCard;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);

        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        recLang = itemView.findViewById(R.id.update);
        recTitle = itemView.findViewById(R.id.recTitle);
    }
}