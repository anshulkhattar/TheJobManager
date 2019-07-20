package com.example.hp.thejobmanager.Supervisor

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hp.thejobmanager.LoginActivities.LoginActivity
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.models.Supervisor
import com.example.hp.thejobmanager.models.Worker
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class SupervisorProfileFragment : Fragment() {

    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_supervisor_profile, container, false)



        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this.activity!!, gso)

        val auth=FirebaseAuth.getInstance().currentUser!!

        val image=view.findViewById<ImageView>(R.id.image)
        val name=view.findViewById<TextView>(R.id.name)
        val email=view.findViewById<TextView>(R.id.email)
        val phn=view.findViewById<TextView>(R.id.phn)

        var logout: Button =view.findViewById(R.id.logout)

        logout.setOnClickListener { logout() }

        name.text=auth.displayName
        Picasso.with(this.activity).load(auth.photoUrl).into(image)
        email.text=auth.email

        var userListener= FirebaseDatabase.getInstance().getReference("Supervisor").child(FirebaseAuth.getInstance().currentUser!!.uid)
        userListener.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    var supervisor:Supervisor= p0.getValue(Supervisor::class.java)!!

                    phn.text=supervisor.uphone
                }
            }
        })

        return view
    
    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient.signOut()

        Toast.makeText(this.activity, "logged out", Toast.LENGTH_LONG).show()

        val intent = Intent(this.activity, LoginActivity::class.java)
        startActivity(intent)
    }

}


