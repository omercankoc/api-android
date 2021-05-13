package com.omercankoc.coins.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.omercankoc.coins.R
import com.omercankoc.coins.model.CoinModel
import com.omercankoc.coins.service.CoinAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    // API KEY : 14305767c0e2b4e57f6fd6bfc0b5bf283273415b
    // URL : curl "https://api.nomics.com/v1/prices?key=14305767c0e2b4e57f6fd6bfc0b5bf283273415b"

    private val BASE_URL = "https://api.nomics.com/v1/"
    private var coinModels : ArrayList<CoinModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadData()
    }

    private fun downloadData(){
        // Retrofit Object olustur.
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

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

                        for (coinModel : CoinModel in coinModels!!){
                            println(coinModel.currency)
                            println(coinModel.price)
                        }
                    }
                }
            }

            // Hata var ise...
            override fun onFailure(call: Call<List<CoinModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
}