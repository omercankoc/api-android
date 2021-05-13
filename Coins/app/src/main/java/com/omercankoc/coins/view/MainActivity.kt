package com.omercankoc.coins.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.omercankoc.coins.R
import com.omercankoc.coins.adapter.RecyclerViewAdapter
import com.omercankoc.coins.model.CoinModel
import com.omercankoc.coins.service.CoinAPI
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {

    // API KEY : 14305767c0e2b4e57f6fd6bfc0b5bf283273415b
    // URL : curl "https://api.nomics.com/v1/prices?key=14305767c0e2b4e57f6fd6bfc0b5bf283273415b"

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var coinModels : ArrayList<CoinModel>? = null
    private var recyclerViewAdapter : RecyclerViewAdapter? = null

    // Disposable : Tek kullanimlik istekler.
    private var compositeDisposable : CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()

        // Recycle View tanimla.
        val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        downloadData()
    }

    private fun downloadData(){
        // Retrofit Object olustur.
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // RxJava ile...
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(CoinAPI::class.java)

        compositeDisposable?.add(retrofit.getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

        /* Retrofit ile...
        // Service ile Retrofit'i birbirine bagla.
        val service = retrofit.create(CoinAPI::class.java)
        val call = service.getData()
        // Async sekilde verileri al.
        call.enqueue(object : Callback<List<CoinModel>> {
            // Cevap gelir ise...
            override fun onResponse(
                call: Call<List<CoinModel>>,
                response: Response<List<CoinModel>>
            ) {
                // Cevap basarili ise...
                if(response.isSuccessful){
                    // Response bos gelmedi ise...
                    response.body()?.let {
                        coinModels = ArrayList(it)

                        /*
                        for (coinModel : CoinModel in coinModels!!){
                            println(coinModel.currency)
                            println(coinModel.price)
                        } */

                        coinModels?.let {
                            recyclerViewAdapter = RecyclerViewAdapter(coinModels!!,this@MainActivity)
                            recyclerView.adapter = recyclerViewAdapter
                        }

                    }
                }
            }

            // Hata var ise...
            override fun onFailure(call: Call<List<CoinModel>>, t: Throwable) {
                t.printStackTrace()
            }
        }) */
    }

    // Bos degil ise, RxJava ile Async sekilde verileri al.
    private fun handleResponse(coinList: List<CoinModel>){
        coinModels = ArrayList(coinList)

        coinModels?.let {
            recyclerViewAdapter = RecyclerViewAdapter(it,this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(coinModel: CoinModel) {
        Toast.makeText(this,"Clicked : ${coinModel.currency}",Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        // Temizle...
        compositeDisposable?.clear()
        super.onDestroy()
    }
}