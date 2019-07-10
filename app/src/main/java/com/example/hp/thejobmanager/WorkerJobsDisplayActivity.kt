package com.example.hp.thejobmanager

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.ViewModelProviders
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
import android.util.Log
import android.widget.Toast
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.adapters.WorkerJobAdapter
import com.example.hp.thejobmanager.viewModel.WorkerJobViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class WorkerJobsDisplayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_jobs_display)


        var recyclerview=findViewById<RecyclerView>(R.id.recyclerview)



        var jobListViewModel: WorkerJobViewModel = ViewModelProviders.of(this).get(WorkerJobViewModel::class.java)

        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            var jobAdapter = WorkerJobAdapter(this@WorkerJobsDisplayActivity, jobListViewModel!!)
            recyclerview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter
        })
    }

}

