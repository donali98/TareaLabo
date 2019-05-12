package com.example.monedas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.monedas.R
import com.example.monedas.models.Moneda
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_model.view.*
 class CoinListAdapter(val clickListener: (Int) -> Unit,val data :ArrayList<Moneda>): RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.card_model, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val moneda:Moneda = data.get(position)

        holder.bind(moneda,clickListener,position)
    }

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        lateinit var logoM:ImageView
        lateinit var nombreM:TextView
        lateinit var contenedorM:LinearLayout

        fun bind(item:Moneda,clickListener:(Int)->Unit,position:Int){
            with(itemView){
                logoM = findViewById(R.id.logoMoneda)
                nombreM = findViewById(R.id.nombreMoneda)
                contenedorM = findViewById(R.id.contenedorMoneda)

                nombreM.text = item.nombre

                Glide.with(logoM.context)
                    .load(item.logo)
                    .centerCrop()
                    .into(logoM)

/*                Picasso.get()
                    .load(item.logo)
                    .resize(50,50)
                    .centerCrop()
                    .into(logoM)*/

                this.setOnClickListener { clickListener(position) }
            }
        }
    }

}