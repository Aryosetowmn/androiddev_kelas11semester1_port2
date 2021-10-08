package com.example.ptsganjil202111rpl2aryoseto6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Klub_Adapter extends RecyclerView.Adapter<Klub_Adapter.ViewHolder> {
    ArrayList<Model_Klub> modelKlub_list;
    Callback callback;

    public interface Callback{
        void onClick(int position);
    }

    public Klub_Adapter(ArrayList<Model_Klub> mahasiswaArrayList, Callback callback) {
        this.modelKlub_list = mahasiswaArrayList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Klub_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Klub_Adapter.ViewHolder holder, int position) {

        holder.nama.setText(modelKlub_list.get(position).getNama());
        holder.tahun.setText(modelKlub_list.get(position).getTahun());
        holder.deskripsi.setText(modelKlub_list.get(position).getDeskripsi());
        Picasso.get()
                .load(modelKlub_list.get(position).getImage())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return (modelKlub_list != null)? modelKlub_list.size():0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView nama;
        private TextView tahun;
        private TextView deskripsi;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_image_klub);
            nama = itemView.findViewById(R.id.tv_nama);
            tahun = itemView.findViewById(R.id.tv_tahun);
            deskripsi = itemView.findViewById(R.id.tv_deskipsi);
            cardView = itemView.findViewById(R.id.cv_cardview);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClick(getLayoutPosition());
                }
            });
        }
    }
}
