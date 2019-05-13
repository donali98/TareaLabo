package com.example.monedas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : AppCompatActivity(),ActivityHelper{

    private lateinit var mainFragment:MainFragment
    private lateinit var  mainFrameLayout: FrameLayout

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.refresh -> {return true}
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("CUSTOM","cayo aqui")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainFragment = MainFragment.newInstance()
        mainFrameLayout = findViewById(R.id.MainFrameLayout)
        if(mainFrameLayout.visibility == View.VISIBLE)
            supportFragmentManager.beginTransaction().replace(R.id.MainFrameLayout, mainFragment).commit()
        else
            supportFragmentManager.beginTransaction().add(R.id.MainFrameLayout, mainFragment).commit()

    }
    override fun getLayoutManager(): GridLayoutManager {
        return GridLayoutManager(this,2)
    }
}
