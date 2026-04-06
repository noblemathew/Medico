package com.simplyany.medico



import android.annotation.SuppressLint
import android.content.Context

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class DashboardFragment : Fragment() {
private lateinit var dbref:DatabaseReference
private lateinit var medrecyclerview:RecyclerView
private lateinit var medarraylist:ArrayList<Medicines>
private lateinit var searchView: SearchView
 var itemAdapter: MyAdapter?=null;
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val myTextView = view.findViewById<View>(R.id.text1) as TextView
        val bundle = arguments
        val message = bundle!!.getString("mText")
        myTextView.text = message
        searchView=view.findViewById(R.id.search_bar)


        medrecyclerview=view.findViewById(R.id.recyclerDashboard)
        medrecyclerview.layoutManager=LinearLayoutManager(context)
        medrecyclerview.setHasFixedSize(true)
        medarraylist= arrayListOf<Medicines>()
        getmedData()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(filterString: String?): Boolean {
                medrecyclerview.adapter= context?.let { MyAdapter(it,medarraylist) }
                (medrecyclerview.adapter as MyAdapter?)?.filter?.filter(filterString)
                return true
            }

            override fun onQueryTextChange(filterString: String?): Boolean {
                medrecyclerview.adapter= context?.let { MyAdapter(it,medarraylist) }
                (medrecyclerview.adapter as MyAdapter?)?.filter?.filter(filterString)
                return true
            }

        })


        setHasOptionsMenu(true)
        return view


    }

    private fun getmedData() {
        dbref=FirebaseDatabase.getInstance().getReference("Medicines")
        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for ( i in snapshot.children){
                        val user=i.getValue(Medicines::class.java)
                        medarraylist.add(user!!)
                    }
                    medrecyclerview.adapter= context?.let { MyAdapter(it,medarraylist) }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }


}
