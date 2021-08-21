package com.omercankoc.apiandroid.model

import com.google.gson.annotations.SerializedName

data class Coin(
    //@SerializedName("currency")
    var currency : String,
    //@SerializedName("price")
    var price : String
)