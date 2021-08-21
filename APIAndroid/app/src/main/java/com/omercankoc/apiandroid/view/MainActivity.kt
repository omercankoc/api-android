package com.omercankoc.apiandroid.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.omercankoc.apiandroid.adapter.RecycleViewAdapter
import com.omercankoc.apiandroid.databinding.ActivityMainBinding
import com.omercankoc.apiandroid.model.Coin
import com.omercankoc.apiandroid.service.CoinAPI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var recycleViewAdapter : RecycleViewAdapter

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var coinModel : ArrayList<Coin>? = null

    // RxJava
    //private var compositeDisposable : CompositeDisposable? = null

    // Coroutine
    private var job : Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //RxJava
        //compositeDisposable = CompositeDisposable()

        // Itemleri dikey konumda goruntule.
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // loadDataUsingRetrofit() // Retrofit
        // loadDataUsingRxJava() // RxJava
        loadDataUsingCoroutine() // Coroutine
    }

    override fun onDestroy() {
        super.onDestroy()
        // compositeDisposable!!.clear() // RxJava
        job!!.cancel() // Coroutine
    }

    /* USING RETROFIT
    private fun loadDataUsingRetrofit(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CoinAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<Coin>>{
            override fun onResponse(call: Call<List<Coin>>, response: Response<List<Coin>>) {
                if(response.isSuccessful){
                    response.body()?.let {
                        coinModel = ArrayList(it)
                        coinModel?.let {
                            recycleViewAdapter = RecycleViewAdapter(coinModel!!)
                            binding.recyclerView.adapter = recycleViewAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    } */

    /* USING RXJAVA
    private fun loadDataUsingRxJava(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CoinAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(coinList : List<Coin>){
        coinModel = ArrayList(coinList)
        coinModel?.let {
            recycleViewAdapter = RecycleViewAdapter(coinModel!!)
            binding.recyclerView.adapter = recycleViewAdapter
        }
    } */

    private fun loadDataUsingCoroutine(){
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.getData()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    response.body()?.let {
                        coinModel = ArrayList(it)
                        coinModel?.let {
                            recycleViewAdapter = RecycleViewAdapter(it)
                            binding.recyclerView.adapter = recycleViewAdapter
                        }
                    }
                }
            }
        }
    }
}