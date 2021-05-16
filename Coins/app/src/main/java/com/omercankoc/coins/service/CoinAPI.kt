package com.omercankoc.coins.service

import android.telecom.Call
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import java.util.*
import com.omercankoc.coins.model.CoinModel as CoinModel

interface CoinAPI {

    // API metotlarina erismek icin gerekli fonksiyonlari tanimla.

    // GET:
    @GET("prices?key=14305767c0e2b4e57f6fd6bfc0b5bf283273415b")

    // Coroutines ile verileri indir.
    suspend fun getData() : Response<List<CoinModel>>

    //RxJava ile verileri indir.
    //fun getData() : Observable<List<CoinModel>>

    // Main Thread bloklamadan (Async) verileri indir.
    //fun getData(): retrofit2.Call<List<CoinModel>>

    // POST:

    // UPDATE:

    // DELETE:
}