package com.example.hp.thejobmanager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.hp.thejobmanager.LoginActivities.LoginActivity
import com.example.hp.thejobmanager.adapters.WorkerJobAdapter
import com.example.hp.thejobmanager.models.SupervisorJobs
import com.example.hp.thejobmanager.userDetailActivities.Supervisor
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel
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
        phn.text="8529637415"


        //*FirebaseDatabase.getInstance().getReference("Supervisor").orderByChild("uemail").equalTo(auth.email)
           /* .addValueEventListener(object : ValueEventListener {


                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {

                        for (userSnapshot in dataSnapshot.children) {
                            var user: Supervisor = userSnapshot.getValue(Supervisor::class.java)!!

                            phn.text=user.uphone

                        }
                    }

                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })*/

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


