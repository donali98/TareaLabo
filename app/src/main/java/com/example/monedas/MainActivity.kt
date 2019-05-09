package com.example.monedas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(){

    private lateinit var mainFragment:MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.MainFrameLayout, mainFragment).commit()
    }
}
