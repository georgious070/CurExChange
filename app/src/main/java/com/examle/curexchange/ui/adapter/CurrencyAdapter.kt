package com.examle.curexchange.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.examle.curexchange.R
import kotlinx.android.synthetic.main.currency_item_view.view.*

class CurrencyAdapter(private val currencyList: MutableList<String>,
                      private val listener: OnRecyclerItemClickListener)
    : RecyclerView.Adapter<CurrencyAdapter.ViewHolder>() {

    fun setData(currencyList: MutableList<String>) {
        this.currencyList.clear()
        this.currencyList.addAll(currencyList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.currency_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currencyList[position], listener)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    interface OnRecyclerItemClickListener {
        fun onClicked(name: String)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(currencyName: String, listener: OnRecyclerItemClickListener) = with(itemView) {
            itemView.currency_name.text = currencyName
            itemView.setOnClickListener { listener.onClicked(currencyName) }
        }
    }
}