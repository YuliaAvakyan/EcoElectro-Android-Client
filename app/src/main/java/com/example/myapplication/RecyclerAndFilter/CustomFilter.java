package com.example.myapplication.RecyclerAndFilter;

import android.widget.Filter;

import java.util.ArrayList;

public class CustomFilter extends Filter {
    MyAdapter adapter;
    ArrayList<DataModel> filterList;

    public CustomFilter(ArrayList<DataModel> filterList, MyAdapter adapter)
    {
        this.adapter=adapter;
        this.filterList=filterList;
    }
    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();
        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            ArrayList<DataModel> filteredList = new ArrayList<>();
            for (int i=0; i < filterList.size(); i++)
            {
                //CHECK
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint) ||
                        filterList.get(i).getSubTitle().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredList.add(filterList.get(i));
                }
            }
            results.count=filteredList.size();
            results.values=filteredList;
        }else
        {
            results.count=filterList.size();
            results.values=filterList;
        }
        return results;
    }
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.dataModelList= (ArrayList<DataModel>) results.values;
        //REFRESH
        adapter.notifyDataSetChanged();
    }
}