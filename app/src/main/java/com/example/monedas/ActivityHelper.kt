package com.example.monedas

import android.view.MenuInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.monedas.database.DatabaseHandler
import com.example.monedas.models.Moneda
import com.example.monedas.models.RespuestaMoneda
import retrofit2.Call
import retrofit2.Response

interface ActivityHelper {
    fun getLayoutManager():GridLayoutManager
    fun responseHelper(dbHandler: DatabaseHandler, call: Call<RespuestaMoneda>, response: Response<RespuestaMoneda>):ArrayList<Moneda>
    fun getMInflater():MenuInflater
    fun showUpdatedMessage()
}