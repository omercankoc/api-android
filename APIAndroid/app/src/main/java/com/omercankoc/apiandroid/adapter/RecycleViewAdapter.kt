package com.omercankoc.apiandroid.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.omercankoc.apiandroid.databinding.RecycleViewRowBinding
import com.omercankoc.apiandroid.model.Coin

class RecycleViewAdapter(private val coinList : ArrayList<Coin>) : RecyclerView.Adapter<RecycleViewAdapter.RecycleViewRowHolder>() {

    // Ilgili view'in tutucu nesnesi.
    class RecycleViewRowHolder(val binding : RecycleViewRowBinding) : RecyclerView.ViewHolder(binding.root){ }

    // View holder olusturuldugunda layout'un view'larini bagla.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewRowHolder {
        val binding = RecycleViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecycleViewRowHolder(binding)
    }

    // Baglandiktan sonra ilgili view'a ilgili data'yi aktar.
    override fun onBindViewHolder(holder: RecycleViewRowHolder, position: Int) {
        holder.binding.textViewCurrency.text = coinList[position].currency
        holder.binding.textViewPrice.text = coinList[position].price
    }

    // Olusturulacak recycler view sayisini elde et.
    override fun getItemCount(): Int {
        return coinList.size
    }
}