package com.example.monedas.fragments


import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monedas.ActivityHelper
import com.example.monedas.R
import com.example.monedas.adapters.CoinListAdapter
import com.example.monedas.api.apiService
import com.example.monedas.database.DatabaseHandler
import com.example.monedas.models.Moneda
import com.example.monedas.models.RespuestaMoneda
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainFragment : Fragment() {

    private var dbHandler: DatabaseHandler? = null

    private lateinit var retrofit: Retrofit
    private lateinit var recyclerView: RecyclerView
    private lateinit var coinListAdapter: CoinListAdapter
    private lateinit var activityHelper: ActivityHelper
    private lateinit var customLayoutManager: GridLayoutManager
    lateinit var coinList: ArrayList<Moneda>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activityHelper = context as ActivityHelper
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activityHelper.getMInflater().inflate(R.menu.refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                obtenerDatos()
                activityHelper.showUpdatedMessage()
                return true
            }
        }
        return true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        dbHandler = DatabaseHandler(view.context)
        recyclerView = view.findViewById(R.id.rv_list)

        obtenerDatos()

        return view
    }

    val itemClickListener = fun(itemIndex: Int) {
        val mon = coinList[itemIndex]
        val infoFragment = InfoFragment.newInstance(mon)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentManager!!.beginTransaction().replace(R.id.MainFrameLayout, infoFragment).addToBackStack(null)
                .commit()
        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentManager!!.beginTransaction().replace(R.id.SecondFrameLayout, infoFragment).commit()
        }
    }

    private fun initRecyclerView(list: ArrayList<Moneda>) {
        customLayoutManager = activityHelper.getLayoutManager()
        coinListAdapter = CoinListAdapter(itemClickListener, list)
        recyclerView.apply {
            adapter = coinListAdapter
            setHasFixedSize(true)
            layoutManager = customLayoutManager
        }
    }

    private fun obtenerDatos() {
        retrofit = Retrofit.Builder()
            .baseUrl("https://taller2-a645b.firebaseio.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(apiService::class.java)
        val monedaRespuestaCall: Call<RespuestaMoneda> = service.obtenerListaMonedas()


        monedaRespuestaCall.enqueue(object : Callback<RespuestaMoneda> {
            override fun onFailure(call: Call<RespuestaMoneda>, t: Throwable) {
                Log.e("CUSTOM", t.message)
            }

            override fun onResponse(call: Call<RespuestaMoneda>, response: Response<RespuestaMoneda>) {
                if (response.isSuccessful) {
                    val monedaRespuesta = response.body()
                    dbHandler?.dropData()
                    monedaRespuesta?.moneda!!.forEach {
                        dbHandler?.addCoin(it)
                    }
                    coinList = dbHandler!!.getCoins()
                    initRecyclerView(coinList)
                }
                else Log.e("CUSTOM","Error")
            }
        })
    }

    fun handleApiResponse(call: Call<RespuestaMoneda>, response: Response<RespuestaMoneda>) {
        coinList = activityHelper.responseHelper(dbHandler!!, call, response)
        initRecyclerView(coinList)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }


}
