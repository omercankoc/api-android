### Model (Public)
```kotlin
data class Coin(
    var currency : String,
    var price : String
)
```

### Service
```kotlin
@GET("omercankoc/api-android/main/coins.json")
fun getData() : Call<List<Coin>> // using Retrofit
// fun getData() : Observable<List<Coin>> // using RxJava
// suspend fun getData() : Response<List<Coin>> // using Coroutine
```

### Recycler View (Public)
```kotlin
class RecycleViewAdapter(private val coinList : ArrayList<Coin>) : RecyclerView.Adapter<RecycleViewAdapter.RecycleViewRowHolder>() {

    // The holder object of the corresponding view.
    class RecycleViewRowHolder(val binding : RecycleViewRowBinding) : RecyclerView.ViewHolder(binding.root){ }

    // When the view holder is created, bind the layout's views.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewRowHolder {
        val binding = RecycleViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecycleViewRowHolder(binding)
    }

    // After bind, transfer the data to view...
    override fun onBindViewHolder(holder: RecycleViewRowHolder, position: Int) {
        holder.binding.textViewCurrency.text = coinList[position].currency
        holder.binding.textViewPrice.text = coinList[position].price
    }

    // Get the size of the recyclerview to be created.
    override fun getItemCount(): Int {
        return coinList.size
    }
}
```

## Public Activity
```kotlin
private lateinit var binding : ActivityMainBinding
private lateinit var recycleViewAdapter : RecycleViewAdapter

private val BASE_URL = "https://raw.githubusercontent.com/"
private var coinModel : ArrayList<Coin>? = null

// RxJava
// private var compositeDisposable : CompositeDisposable? = null

// Coroutine
// private var job : Job? = null


override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    // RxJava
    // compositeDisposable = CompositeDisposable()

    binding.recyclerView.layoutManager = LinearLayoutManager(this)

    loadDataUsingRetrofit() // Retrofit
     // loadDataUsingRxJava() // RxJava
     // loadDataUsingCoroutine() // Coroutine
    }

    override fun onDestroy() {
        super.onDestroy()
        // compositeDisposable!!.clear() // RxJava
        // job!!.cancel() // Coroutine
    }
```

## Get JSON data using Retrofit
```kotlin
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
}
```

## Get JSON data using RxJava
```kotlin
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
}
```

## Get JSON data using Coroutine
```kotlin
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
```
