package com.omercankoc.coins.model

// Data sinifinda model olustur.
data class CoinModel(
    //@SerializedName("currency")
    val currency : String,
    //@SerializedName("price")
    val price : String
    )