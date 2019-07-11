package com.example.hp.thejobmanager

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel
import com.google.firebase.database.FirebaseDatabase

class AddJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        var add:FloatingActionButton=this.findViewById(R.id.fab_add)
        var recyclerview=findViewById<RecyclerView>(R.id.recyclerView)

        add.setOnClickListener { val intent = Intent(this, NewJobActivity::class.java)
            startActivity(intent) }


        var jobListViewModel: SupervisorJobViewModel = ViewModelProviders.of(this).get(SupervisorJobViewModel::class.java)

        var keys:ArrayList<String>
        keys=jobListViewModel.getKey()

        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            var jobAdapter = SupervisorJobAdapter(this@AddJobActivity, jobListViewModel!!,keys)
            recyclerview.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter
        })


    }


}
