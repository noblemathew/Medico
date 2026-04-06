package com.simplyany.medico

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val context: Context, private var medlist: ArrayList<Medicines>): RecyclerView.Adapter<MyAdapter.viewholder>(),Filterable{


    private var medlistfilteredlist: ArrayList<Medicines> = arrayListOf()

    fun setData(medlist: ArrayList<Medicines>){
        this.medlist=medlist
        this.medlistfilteredlist=medlist
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
            val itemView=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
            return viewholder(itemView)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentitem =medlist[position]
        holder.name.text=currentitem.name
        holder.desc.text=currentitem.desc
        Picasso.get().load(currentitem.image).into(holder.image)
        holder.price.text=currentitem.price
        holder.llContent.setOnClickListener {
            val intent = Intent(context, CheckoutActivity::class.java)
            intent.putExtra("Med_name", currentitem.name)
            intent.putExtra("Med_Image",currentitem.image)
            intent.putExtra("Med_price",currentitem.price)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
       return medlist.size
    }

    class viewholder(itemview : View): RecyclerView.ViewHolder(itemview){
        val name : TextView=itemview.findViewById(R.id.medname);
        val desc : TextView=itemview.findViewById(R.id.meddesc);
        val image : ImageView=itemview.findViewById(R.id.imgmed);
        val price : TextView=itemview.findViewById(R.id.medprice);
        val llContent: LinearLayout = itemview.findViewById(R.id.llContent)

    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults=FilterResults();
                if(p0 == null || p0.length<0){
                    filterResults.count = medlistfilteredlist.size
                    filterResults.values=medlistfilteredlist
                }else{
                     var searchchr=p0.toString().toLowerCase()
                    val itemModal= ArrayList<Medicines>()
                    for(item in medlist){
                        if(item.name.toString().toLowerCase().contains(searchchr) || item.desc.toString().toLowerCase().contains(searchchr)){
                            itemModal.add(item)
                    }

                    }
                    filterResults.count=itemModal.size
                    filterResults.values=itemModal
                }
                return filterResults;
            }

            override fun publishResults(p0: CharSequence?,filterResults: FilterResults?) {
                medlist=filterResults!!.values as ArrayList<Medicines>
                notifyDataSetChanged()

            }

        }
    }


}