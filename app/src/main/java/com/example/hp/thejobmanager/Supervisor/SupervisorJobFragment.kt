package com.example.hp.thejobmanager.Supervisor


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hp.thejobmanager.adapters.SupervisorJobAdapter
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel
import android.support.v7.widget.LinearLayoutManager
import com.example.hp.thejobmanager.R


class SupervisorJobFragment : Fragment() {

    lateinit var jobAdapter: SupervisorJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_supervisor_job, container, false)
        var recyclerview=view.findViewById<RecyclerView>(R.id.recyclerView)



        var jobListViewModel: SupervisorJobViewModel = ViewModelProviders.of(this).get(SupervisorJobViewModel::class.java)

        var keys:ArrayList<String>
        keys=jobListViewModel.getKey()



        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer {jobListViewModel->

            jobAdapter = SupervisorJobAdapter(this, jobListViewModel!!,keys)

            //recyclerview!!.layoutManager = LinearLayoutManager(this@JobListActivity)
            recyclerview!!.adapter = jobAdapter

            recyclerview.layoutManager= LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)
            
        })




        return view
    }


}
