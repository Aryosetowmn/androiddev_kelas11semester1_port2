package com.example.ptsganjil202111rpl2aryoseto6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Klub_Adapter extends RecyclerView.Adapter<Klub_Adapter.ViewHolder> {

    private final ArrayList<Model_Klub> modelKlub_list;
    private final Callback callback;

    public interface Callback {
        void onClick(int position);
    }

    public Klub_Adapter(ArrayList<Model_Klub> modelKlubList, Callback callback) {
        this.modelKlub_list = modelKlubList;
        this.callback = callback;
    }

    @NonNull
    @Override
    public Klub_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Klub_Adapter.ViewHolder holder, int position) {
        Model_Klub item = modelKlub_list.get(position);

        holder.nama.setText(item.getNama());
        holder.tahun.setText(item.getTahun());

        // Deskripsi dari API panjang banget -> kalau mau lebih ringan, dipotong
        String deskripsi = item.getDeskripsi();
        if (deskripsi != null && deskripsi.length() > 140) {
            deskripsi = deskripsi.substring(0, 140) + "...";
        }
        holder.deskripsi.setText(deskripsi != null ? deskripsi : "");

        String imageUrl = item.getImage();

        // IMPORTANT: jangan pernah Picasso.load("") atau load(null)
        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            holder.image.setImageResource(R.mipmap.ic_launcher); // placeholder default dulu
        } else {
            Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.mipmap.ic_launcher) // tampilkan saat loading
                    .error(R.mipmap.ic_launcher)       // tampilkan kalau gagal
                    .fit()
                    .centerCrop()
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return (modelKlub_list != null) ? modelKlub_list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final TextView nama;
        private final TextView tahun;
        private final TextView deskripsi;
        private final CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_image_klub);
            nama = itemView.findViewById(R.id.tv_nama);
            tahun = itemView.findViewById(R.id.tv_tahun);
            deskripsi = itemView.findViewById(R.id.tv_deskipsi);
            cardView = itemView.findViewById(R.id.cv_cardview);

            cardView.setOnClickListener(v -> {
                if (callback != null && getBindingAdapterPosition() != RecyclerView.NO_POSITION) {
                    callback.onClick(getBindingAdapterPosition());
                }
            });
        }
    }
}