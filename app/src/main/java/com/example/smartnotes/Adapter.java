package com.example.smartnotes;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartnotes.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    List<String>titleList;
    List<String>contentList;
    public  Adapter(List<String> titleList,List<String>contentList){
        this.titleList=titleList;
        this.contentList=contentList;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_view_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
        holder.noteTitle.setText(titleList.get(position));
        holder.noteContent.setText(contentList.get(position));
        holder.view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),noteDetailsActivity.class);
                intent.putExtra("Title",titleList.get(position));
                intent.putExtra("Content",contentList.get(position));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteContent;
        View view2;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.txtTitles);
            noteContent=itemView.findViewById(R.id.txtContent);
            view2= itemView;
        }

    }
}
