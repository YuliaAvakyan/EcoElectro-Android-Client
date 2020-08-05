package com.example.myapplication.RecyclerAndFilter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView cardImageView;
    public TextView titleTextView;
    public TextView subTitleTextView;
    public MaterialButton materialButton;
    ItemClickListener itemClickListener;
    private int itemId;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        cardImageView = itemView.findViewById(R.id.imageView);
        titleTextView = itemView.findViewById(R.id.card_title);
        subTitleTextView = itemView.findViewById(R.id.card_subtitle);
        materialButton = itemView.findViewById(R.id.action_button_1);
        itemView.setOnClickListener(this);

    }

    public void bindData(DataModel dataModel, Context context) {
        cardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.cardlogo));
        titleTextView.setText(dataModel.getTitle());
        subTitleTextView.setText(dataModel.getSubTitle());
        itemId = dataModel.getItemId();
    }

    public int getId() {
        return itemId;
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());

    }
    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
