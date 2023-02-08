package com.example.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter {
    ItemAdapterListener itemAdapterListener;
    public interface ItemAdapterListener{
        public void click(int position);
    }
    ItemSQLiteHelper itemSQLiteHelper;
    ItemAdapter(ItemSQLiteHelper itemSQLiteHelper){
        this.itemSQLiteHelper = itemSQLiteHelper;
    }
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView dateView;
        ImageView imageView;
        TextView nameView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            nameView = (TextView) itemView.findViewById(R.id.nameView);
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
                .inflate(R.layout.item,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = itemSQLiteHelper.get(position);
        Bitmap bitmap = itemSQLiteHelper.getBit(position);
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

        itemViewHolder.imageView.setImageBitmap(bitmap);
        itemViewHolder.dateView.setText(item.date);
        itemViewHolder.nameView.setText(item.name);
    }

    @Override
    public int getItemCount() {
        return itemSQLiteHelper.getCount();
    }

}
