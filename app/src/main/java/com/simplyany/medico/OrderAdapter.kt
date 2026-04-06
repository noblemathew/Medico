package com.simplyany.medico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class OrderAdapter(val context: Context,private var orderlist: ArrayList<Orders>):RecyclerView.Adapter<OrderAdapter.viewholder>() {
    class viewholder(itemview : View): RecyclerView.ViewHolder(itemview){
        val name : TextView =itemview.findViewById(R.id.medname);
        val desc : TextView =itemview.findViewById(R.id.meddesc);
        val image : ImageView =itemview.findViewById(R.id.imgmed);
        val price : TextView =itemview.findViewById(R.id.medprice);

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val itemView= LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return viewholder(itemView)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentitem=orderlist[position]
        holder.name.text=currentitem.name
        holder.desc.text=currentitem.desc
        Picasso.get().load(currentitem.image).into(holder.image)
        holder.price.text=currentitem.price

    }

    override fun getItemCount(): Int {
        return orderlist.size
    }
}
