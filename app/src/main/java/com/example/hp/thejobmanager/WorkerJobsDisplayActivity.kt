package com.example.hp.thejobmanager

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.adapters.WorkerJobAdapter
import com.example.hp.thejobmanager.viewModel.WorkerJobViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

class WorkerJobsDisplayActivity : AppCompatActivity() {

    lateinit var jobAdapter:WorkerJobAdapter
    lateinit var gso: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_jobs_display)

        var logout:Button=findViewById(R.id.logout)

        logout.setOnClickListener { logout() }

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        var recyclerview=findViewById<RecyclerView>(R.id.recyclerview)



        var jobListViewModel: WorkerJobViewModel = ViewModelProviders.of(this).get(WorkerJobViewModel::class.java)

        var keys:ArrayList<String>
        keys=jobListViewModel.getKey()

        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            jobAdapter = WorkerJobAdapter(this@WorkerJobsDisplayActivity, jobListViewModel!!,keys)
            recyclerview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter
        })
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                jobAdapter.removeAt(viewHolder.adapterPosition)
                Toast.makeText(this@WorkerJobsDisplayActivity,"Job skipped",Toast.LENGTH_SHORT).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }
    fun logout(){
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient.signOut()

        Toast.makeText(this, "logged out", Toast.LENGTH_LONG).show()

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

}

