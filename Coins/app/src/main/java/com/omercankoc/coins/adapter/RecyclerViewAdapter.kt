package com.omercankoc.coins.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.omercankoc.coins.R
import com.omercankoc.coins.model.CoinModel
import kotlinx.android.synthetic.main.row_layout.view.*

class RecyclerViewAdapter(private val coinList : ArrayList<CoinModel>, private val listener : Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    private val colors : Array<String> = arrayOf("#F34638","#F98321","#1FE3DA","#A2E74A","#4A8FE7","#E74AB3","#E74A5F",)

    interface Listener {
        fun onItemClick(coinModel: CoinModel)
    }

    class RowHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(coinModel: CoinModel, colors : Array<String>, position: Int, listener : Listener){
            itemView.setOnClickListener { listener.onItemClick(coinModel) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 7]))
            itemView.textViewCurrency.text = coinModel.currency
            itemView.textViewPrice.text = coinModel.price
        }
    }

    // row_layout ile birbirine bagla.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    // Hangi item hangi veriyi gosterecek, belirt.
    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(coinList[position],colors,position,listener)
    }

    // Kac adet row olusturulacagini belirt.
    override fun getItemCount(): Int {
        return coinList.count()
    }
}
