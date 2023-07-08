package com.example.evento;

import android.media.Image;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<ModelClass> userList;
    public Adapter(List<ModelClass>userList){this.userList=userList;}

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        int res = userList.get(position).getImgView1();
        String name = userList.get(position).getTxtView1();
        String msg = userList.get(position).getTxtView2();

        holder.setData(res,name,msg);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.EventImg);
            textView1 = itemView.findViewById(R.id.EventName);
            textView2 = itemView.findViewById(R.id.EventDetails);

        }

        public void setData(int res, String name, String msg) {
            imageView.setImageResource(res);
            textView1.setText(name);
            textView2.setText(msg);

        }
    }
}
