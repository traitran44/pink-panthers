package pinkpanthers.pinkshelters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.io.FilterReader;
import java.util.ArrayList;
import java.util.List;

import pinkpanthers.pinkshelters.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private List<Shelter> shelters;
    private List<String> filtered;
    private Db db;

    // data is passed into the constructor
    RecyclerAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvShelterName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setUpForFilter() {
        db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        shelters = db.getAllShelters();
        mData = new ArrayList<>();
        System.out.print(shelters.size());
        for (int i = 0; i < shelters.size(); i++) {
            mData.add(shelters.get(i).getShelterName());
        }
        filtered = mData;
    }

    public void filter(CharSequence charSequence) {
        setUpForFilter();
        String filter = charSequence.toString().toLowerCase();
        if (filter == null || filter.length() == 0) {
            filtered = mData;
        } else {
            filtered.clear();
            for (Shelter s: shelters) {
                if (s.getShelterName().toLowerCase().contains(filter)) {
                    filtered.add(s.getShelterName());
                }
            }
        }
        notifyDataSetChanged();
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}