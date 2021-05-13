package com.omercankoc.coins

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    // API KEY : 14305767c0e2b4e57f6fd6bfc0b5bf283273415b
    // URL : curl "https://api.nomics.com/v1/prices?key=14305767c0e2b4e57f6fd6bfc0b5bf283273415b"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}