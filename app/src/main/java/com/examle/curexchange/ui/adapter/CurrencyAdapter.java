package com.examle.curexchange.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.examle.curexchange.R;

import java.util.List;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private final List<String> currenciesList;
    private OnRecyclerItemClickedListener listener;

    public CurrencyAdapter(List<String> currencyList, OnRecyclerItemClickedListener listener) {
        this.currenciesList = currencyList;
        this.listener = listener;
    }

    public void setData(List<String> currenciesList) {
        this.currenciesList.clear();
        this.currenciesList.addAll(currenciesList);
        notifyDataSetChanged();
    }

    @Override
    public CurrencyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.currency_item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CurrencyAdapter.ViewHolder holder, int position) {
        holder.currencyNameTextView.setText(currenciesList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked(holder.currencyNameTextView.getText().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return currenciesList.size();
    }

    public interface OnRecyclerItemClickedListener {

        void onClicked(String name);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView currencyNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            currencyNameTextView = itemView.findViewById(R.id.currency_name);
        }
    }
}
