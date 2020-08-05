package com.example.myapplication.RecyclerAndFilter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.myapplication.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
        implements Filterable
{

    ArrayList<DataModel> dataModelList;
    ArrayList<DataModel> filterList;
    private Context mContext;
    CustomFilter filter;
//    private MyAdapterListener listener;

    public MyAdapter(ArrayList<DataModel> modelList, Context context) {
        dataModelList = modelList;
        mContext = context;
//        this.listener = listener;
        this.filterList = modelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        // Return a new view holder

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bindData(dataModelList.get(position), mContext);
        holder.materialButton.setTag(holder.getId());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @Override
    public Filter getFilter() {
            if(filter==null)
            {
                filter=new CustomFilter(filterList,this);
            }
            return filter;
        }
    }

