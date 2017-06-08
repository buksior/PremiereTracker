package com.example.karolinar.premieretracker;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Paulina on 08.06.2017.
 */

public class SearchTextAdapter extends ArrayAdapter<String> {

    Context context;
    int resource, textViewResourceId;
    String[] items;

    public SearchTextAdapter(Context context, int resource, String[] items) {
        super(context, resource, items);
        this.context = context;
        this.resource = resource;
        this.items = items;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = new HashSet<String>(Arrays.asList(items));
                filterResults.count = items.length;
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            notifyDataSetChanged();
        }
    };
}
