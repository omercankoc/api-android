package com.omercankoc.apiandroid.service

import com.omercankoc.apiandroid.model.Coin
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CoinAPI {
    // GET
    // https://raw.githubusercontent.com/ -> BASE_URL
    // omercankoc/api-android/main/coins.json -> KEY_URL
    @GET("omercankoc/api-android/main/coins.json")
    fun getData() : Call<List<Coin>> // using Retrofit
    // fun getData() : Observable<List<Coin>> // using RxJava
    // suspend fun getData() : Response<List<Coin>> // using Coroutine
}