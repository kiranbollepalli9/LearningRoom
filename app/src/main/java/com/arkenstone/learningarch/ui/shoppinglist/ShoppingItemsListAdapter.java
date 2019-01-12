package com.arkenstone.learningarch.ui.shoppinglist;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arkenstone.learningarch.R;
import com.arkenstone.learningarch.data.ShoppingItem;

import java.util.List;

public class ShoppingItemsListAdapter extends RecyclerView.Adapter<ShoppingItemsListAdapter.WordViewHolder> {

    class WordViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private WordViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.shoppingitem_titleView);
        }
    }

    private final LayoutInflater mInflater;
    private List<ShoppingItem> mShoppingItems; // Cached copy of words

    ShoppingItemsListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.layout_shopping_item, parent, false);
        return new WordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        if (mShoppingItems != null) {
            ShoppingItem current = mShoppingItems.get(position);
            holder.wordItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No ShoppingItem");
        }
    }

    void setData(List<ShoppingItem> words) {
        mShoppingItems = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mShoppingItems has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mShoppingItems != null)
            return mShoppingItems.size();
        else return 0;
    }
}