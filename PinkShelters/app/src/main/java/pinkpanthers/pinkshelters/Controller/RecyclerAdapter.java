package pinkpanthers.pinkshelters.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pinkpanthers.pinkshelters.R;

/**
 * to set up an adapter for recycler view
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private final List<String> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    /**
     * data is passed into the constructor
     * Update class data and layoutInflater context
     *
     * @param context the current context in the view
     * @param data    data available in the view
     */
    @SuppressWarnings("AssignmentOrReturnOfFieldWithMutableType")
    RecyclerAdapter(Context context, List<String> data) {
        mInflater = LayoutInflater.from(context);
        mData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String animal = mData.get(position);
        holder.myTextView.setText(animal);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * stores and recycles views as they are scrolled off screen
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tvShelterName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }




//    /**
//     * stores and recycles views as they are scrolled off screen
//     */
//    public class ViewHolderAccount extends RecyclerView.ViewHolder implements View.OnClickListener {
//        final TextView myTextViewAccount;
//
//        ViewHolderAccount(View itemView) {
//            super(itemView);
//            myTextViewAccount = itemView.findViewById(R.id.tvShelterName);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) {
//                mClickListener.onItemClick(view, getAdapterPosition());
//            }
//        }
//    }

    /**
     * convenience method for getting data at click position
     *
     * @param id the id of the item
     * @return the data that with that id
     */
    String getItem(int id) {
        return mData.get(id);
    }

    /**
     * allows clicks events to be caught
     *
     * @param itemClickListener the item that was chosen
     */
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * parent activity will implement this method to respond to click events
     */
    public interface ItemClickListener {
        void onItemClick(@SuppressWarnings("unused") View view, int position);
    }
}