package com.simplyany.medico


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.*
import com.google.firebase.database.FirebaseDatabase


class ProfileFragment : Fragment() {
    private lateinit var dbref: DatabaseReference
    private lateinit var medrecyclerview: RecyclerView
    private lateinit var medarraylist:ArrayList<Orders>

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        medrecyclerview=view.findViewById(R.id.recyclerDashboard2)
        medrecyclerview.layoutManager= LinearLayoutManager(context)
        medrecyclerview.setHasFixedSize(true)
        medarraylist= arrayListOf<Orders>()
        getmedData()
        return view
    }
    private fun getmedData() {
        dbref= FirebaseDatabase.getInstance().getReference("History")
        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = Firebase.auth.currentUser
                    if (user != null) {
                        val username=user.email!!.replaceAfter("@","")
                        for (i in snapshot.child(username).children) {
                            val user2 = i.getValue(Orders::class.java)
                            medarraylist.add(user2!!)
                        }
                    }
                }

                medrecyclerview.adapter = context?.let { OrderAdapter(it, medarraylist) }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })





        }
}
