package com.simplyany.medico

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Signup.newInstance] factory method to
 * create an instance of this fragment.
 */
class Signup : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var dbRef:DatabaseReference
    lateinit var emailsignup : EditText
    lateinit var passwordsignup:EditText
    lateinit var name:EditText
    lateinit var btnsignup:Button
    lateinit var progressBar: ProgressBar
    lateinit var RelativeLayout:RelativeLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_signup, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        emailsignup=view.findViewById(R.id.et_email)
        passwordsignup=view.findViewById(R.id.et_password)
        btnsignup=view.findViewById(R.id.btn_signup)
        name=view.findViewById(R.id.et_name)
        RelativeLayout=view.findViewById(R.id.parentRelative)
        progressBar=view.findViewById(R.id.progressbar)
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.BLACK));
        RelativeLayout.visibility=View.INVISIBLE
        progressBar.visibility=View.INVISIBLE
        btnsignup.setOnClickListener(){
            val email = emailsignup.text.toString()
            val pass = passwordsignup.text.toString()
            val nam=name.text.toString()
            val nam2=email.replaceAfter("@","")
            if (email.isNotEmpty() && pass.isNotEmpty() && nam.isNotEmpty()) {
                RelativeLayout.visibility=View.VISIBLE
                progressBar.visibility=View.VISIBLE
                btnsignup.visibility=View.INVISIBLE
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {

                    if (it.isSuccessful) {


                        firebaseAuth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                            if (it.isSuccessful){
                                val db=dbRef.push().key!!
                                dbRef.child(nam2).setValue(nam).addOnCompleteListener {

                                }.addOnFailureListener{
                                    Toast.makeText(activity, "Error", Toast.LENGTH_LONG).show()
                                }

                                RelativeLayout.visibility=View.INVISIBLE
                                progressBar.visibility=View.INVISIBLE
                                btnsignup.visibility=View.VISIBLE
                                Toast.makeText(activity, "Verification link sent to your Email", Toast.LENGTH_LONG).show()


                            }
                            else{
                                RelativeLayout.visibility=View.INVISIBLE
                                progressBar.visibility=View.INVISIBLE
                                btnsignup.visibility=View.VISIBLE

                                Toast.makeText(activity, "Sign Up failed. Try Again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        RelativeLayout.visibility=View.INVISIBLE
                        progressBar.visibility=View.INVISIBLE
                        btnsignup.visibility=View.VISIBLE

                        Toast.makeText(activity, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                RelativeLayout.visibility=View.INVISIBLE
                progressBar.visibility=View.INVISIBLE
                btnsignup.visibility=View.VISIBLE

                Toast.makeText(activity, "Empty fills are not allowed!", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Signup.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Signup().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}