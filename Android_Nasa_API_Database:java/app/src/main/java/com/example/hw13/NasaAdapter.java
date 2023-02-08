package com.example.hw13;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class NasaAdapter extends RecyclerView.Adapter {
    NasaAdapterListener nasaAdapterListener;
    public interface NasaAdapterListener{
        public void click(int position);
    }
    NasaSQLiteHelper nasaSQLiteHelper;
    NasaAdapter(NasaSQLiteHelper nasaSQLiteHelper){
        this.nasaSQLiteHelper = nasaSQLiteHelper;
    }

    public class NasaViewHolder extends RecyclerView.ViewHolder{
            TextView dateView;
            ImageView imageView;
        public NasaViewHolder(@NonNull View itemView) {
            super(itemView);
            dateView = (TextView) itemView.findViewById(R.id.dateView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(view.getContext(), ClickDetailActivity.class);
                    intent.putExtra("pos", pos);
                    view.getContext().startActivity(intent);
                }
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nasa,parent,false);
        return new NasaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Nasa nasa = nasaSQLiteHelper.get(position);
        Bitmap bitmap = nasaSQLiteHelper.getBit(position);
        NasaViewHolder nasaViewHolder = (NasaViewHolder) holder;
        nasaViewHolder.imageView.setImageBitmap(bitmap);
        nasaViewHolder.dateView.setText(nasa.date);
    }

    @Override
    public int getItemCount() {
        return nasaSQLiteHelper.getCount();
    }
}
