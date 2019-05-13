package com.example.monedas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.monedas.database.DatabaseHandler
import com.example.monedas.fragments.MainFragment
import com.example.monedas.models.Moneda
import com.example.monedas.models.RespuestaMoneda
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity(),ActivityHelper{



    private lateinit var mainFragment: MainFragment
    private lateinit var  mainFrameLayout: FrameLayout

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.refresh,menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.refresh -> {
                Log.d("CUSTOM","clicked from mainActivity")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
//        Log.d("CUSTOM","cayo aqui")
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
    override fun responseHelper(dbHandler: DatabaseHandler, call: Call<RespuestaMoneda>, response: Response<RespuestaMoneda>):ArrayList<Moneda> {
        var monedaRespuesta = response.body()
        var coinList = ArrayList<Moneda>()
        if (response.isSuccessful) {
            dbHandler.dropData()
            monedaRespuesta?.moneda!!.forEach {
                dbHandler.addCoin(it)
            }

            coinList = dbHandler.getCoins()
        }
        return coinList
    }
    override fun getMInflater(): MenuInflater {
        return menuInflater
    }
    override fun showUpdatedMessage() {
        Toast.makeText(this, "Monedas actualizadas", Toast.LENGTH_SHORT).show()

    }
}
