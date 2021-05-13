package com.omercankoc.coins.service

import android.telecom.Call
import retrofit2.http.GET
import com.omercankoc.coins.model.CoinModel as CoinModel

interface CoinAPI {

    // API metotlarina erismek icin gerekli fonksiyonlari tanimla.

    // GET:
    @GET("prices?key=14305767c0e2b4e57f6fd6bfc0b5bf283273415b")
    // Main Thread bloklamadan (Async) verileri indir.
    fun getData(): retrofit2.Call<List<CoinModel>>
    // POST:

    // UPDATE:

    // DELETE:

}