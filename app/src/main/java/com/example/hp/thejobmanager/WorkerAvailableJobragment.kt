package com.example.hp.thejobmanager


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hp.thejobmanager.adapters.WorkerJobAdapter
import com.example.hp.thejobmanager.viewModel.WorkerJobViewModel


class WorkerAvailableJobragment : Fragment() {

    lateinit var jobAdapter:WorkerJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_worker_available_jobragment, container, false)

        var recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)


        var jobListViewModel: WorkerJobViewModel = ViewModelProviders.of(this).get(WorkerJobViewModel::class.java)

        var keys: ArrayList<String>
        keys = jobListViewModel.getKey()

        jobListViewModel.getArrayList().observe(this, android.arch.lifecycle.Observer { jobListViewModel ->

            jobAdapter = WorkerJobAdapter(this.activity!!, jobListViewModel!!, keys)
            recyclerview.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.VERTICAL, false)

            recyclerview!!.adapter = jobAdapter
        })
        val swipeHandler = object : SwipeToDeleteCallback(this.activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                jobAdapter.removeAt(viewHolder.adapterPosition)
            }

        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerview)
        return view
    }


}
