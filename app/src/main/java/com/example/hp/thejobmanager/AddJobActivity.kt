package com.example.hp.thejobmanager

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel

class AddJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        var add:FloatingActionButton=findViewById(R.id.fab_add)
        var recyclerview=findViewById<RecyclerView>(R.id.recyclerView)

        add.setOnClickListener { val intent = Intent(this, NewJobActivity::class.java)
            startActivity(intent) }

        var jobListViewModel: SupervisorJobViewModel = ViewModelProviders.of(this).get(SupervisorJobViewModel::class.java)

        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            var jobAdapter = SupervisorJobAdapter(this@AddJobActivity, jobListViewModel!!)
            recyclerview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter
        })
    }


}