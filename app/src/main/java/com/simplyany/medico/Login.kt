package com.simplyany.medico

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var textView: TextView
    lateinit var emaillogin: EditText
    lateinit var passwordlogin: EditText
    lateinit var submitlogin: Button
    lateinit var dbRef: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
    lateinit var progressBar: ProgressBar
    lateinit var RelativeLayout: RelativeLayout

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
        val view=inflater.inflate(R.layout.fragment_login, container, false)
        textView=view.findViewById(R.id.forget)
        emaillogin=view.findViewById(R.id.et_email)
        passwordlogin=view.findViewById(R.id.et_password)
        submitlogin=view.findViewById(R.id.btn_login)
        firebaseAuth = FirebaseAuth.getInstance()
        dbRef= FirebaseDatabase.getInstance().getReference("Users")
        RelativeLayout=view.findViewById(R.id.parentRelativelogin)
        progressBar=view.findViewById(R.id.progressbarlogin)
        progressBar.progressTintList = ColorStateList.valueOf(Color.BLACK)
        RelativeLayout.visibility=View.INVISIBLE
        progressBar.visibility=View.INVISIBLE
        textView.setOnClickListener {
            val intent = Intent (activity, ForgetPassword::class.java)
            activity?.startActivity(intent)
        }
        submitlogin.setOnClickListener {
            val email = emaillogin.text.toString()
            val pass = passwordlogin.text.toString()
            val nam2=email.replaceAfter("@","")
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                if (checkEmail(email)) {
                    RelativeLayout.visibility=View.VISIBLE
                    progressBar.visibility=View.VISIBLE
                    submitlogin.visibility=View.INVISIBLE

                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {

                            val user = Firebase.auth.currentUser
                            if (user != null) {
                                if(user.isEmailVerified){
                                    RelativeLayout.visibility=View.INVISIBLE
                                    progressBar.visibility=View.INVISIBLE
                                    submitlogin.visibility=View.VISIBLE
                                    val intent = Intent (activity, MainActivity::class.java)
                                   dbRef.child(nam2).get().addOnSuccessListener {
                                          if(it.exists()){
                                              val name=it.value.toString()
                                              intent.putExtra("id", "Hello $name !")
                                              activity?.startActivity(intent)
                                          }
                                    }.addOnFailureListener{
                                       Toast.makeText(
                                           activity,
                                           "Error",
                                           Toast.LENGTH_SHORT
                                       ).show()
                                    }

                                }
                                else{
                                    RelativeLayout.visibility=View.INVISIBLE
                                    progressBar.visibility=View.INVISIBLE
                                    submitlogin.visibility=View.VISIBLE

                                    Toast.makeText(
                                        activity,
                                        "Please Verify your Email.If not found please check your spam folder too",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                            }

                        } else {
                            RelativeLayout.visibility=View.INVISIBLE
                            progressBar.visibility=View.INVISIBLE
                            submitlogin.visibility=View.VISIBLE

                            try {
                                throw it.exception!!
                            } catch (e: FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(
                                    activity,
                                    "Incorrect Password",
                                    Toast.LENGTH_SHORT
                                ).show()


                            }
                            catch (e: FirebaseAuthInvalidUserException){
                                Toast.makeText(
                                    activity,
                                    "User not found",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        }
                    }
                } else {
                    RelativeLayout.visibility=View.INVISIBLE
                    progressBar.visibility=View.INVISIBLE
                    submitlogin.visibility=View.VISIBLE

                    Toast.makeText(activity, "Invalid Email", Toast.LENGTH_SHORT).show()
                }
            } else {
                RelativeLayout.visibility=View.INVISIBLE
                progressBar.visibility=View.INVISIBLE
                submitlogin.visibility=View.VISIBLE

                Toast.makeText(activity, "Empty fills are not allowed!", Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    private fun checkEmail(email: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Login.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}