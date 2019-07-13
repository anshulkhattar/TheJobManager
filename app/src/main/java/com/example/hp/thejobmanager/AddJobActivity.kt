package com.example.hp.thejobmanager

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.widget.Button
import android.widget.Toast
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddJobActivity : AppCompatActivity() {

    lateinit var jobAdapter:SupervisorJobAdapter
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        var add:FloatingActionButton=this.findViewById(R.id.fab_add)
        var recyclerview=findViewById<RecyclerView>(R.id.recyclerView)

        var logout:Button=findViewById(R.id.logout)

        logout.setOnClickListener { logout() }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        add.setOnClickListener { val intent = Intent(this, NewJobActivity::class.java)
            startActivity(intent) }


        var jobListViewModel: SupervisorJobViewModel = ViewModelProviders.of(this).get(SupervisorJobViewModel::class.java)

        var keys:ArrayList<String>
        keys=jobListViewModel.getKey()



        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            jobAdapter = SupervisorJobAdapter(this@AddJobActivity, jobListViewModel!!,keys)
            recyclerview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter
        })

    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient.signOut()

        Toast.makeText(this, "logged out", Toast.LENGTH_LONG).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}
