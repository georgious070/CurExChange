package com.examle.curexchange.ui.home;

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
        holder.firstCurrencyName.setText(currenciesList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClicked();
            }
        });
    }

    @Override
    public int getItemCount() {
        return currenciesList.size();
    }

    public interface OnRecyclerItemClickedListener {

        void onClicked();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView firstCurrencyName;

        public ViewHolder(View itemView) {
            super(itemView);
            firstCurrencyName = itemView.findViewById(R.id.first_currency_name);
        }
    }
}
