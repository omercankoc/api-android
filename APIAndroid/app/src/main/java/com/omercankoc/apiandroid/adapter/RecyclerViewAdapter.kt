package com.omercankoc.apiandroid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.omercankoc.apiandroid.databinding.RecyclerViewRowBinding
import com.omercankoc.apiandroid.model.Coin

class RecyclerViewAdapter(private val coinList : ArrayList<Coin>) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewRowHolder>() {

    // Ilgili view'in tutucu nesnesi.
    class RecyclerViewRowHolder(val binding : RecyclerViewRowBinding) : RecyclerView.ViewHolder(binding.root){ }

    // View holder olusturuldugunda layout'un view'larini bagla.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewRowHolder {
        val binding = RecyclerViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecyclerViewRowHolder(binding)
    }

    // Baglandiktan sonra ilgili view'a ilgili data'yi aktar.
    override fun onBindViewHolder(holder: RecyclerViewRowHolder, position: Int) {
        holder.binding.textViewCurrency.text = coinList[position].currency
        holder.binding.textViewPrice.text = coinList[position].price
    }

    // Olusturulacak recycler view sayisini elde et.
    override fun getItemCount(): Int {
        return coinList.size
    }
}