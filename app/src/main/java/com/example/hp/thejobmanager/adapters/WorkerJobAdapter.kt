package com.example.hp.thejobmanager.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.databinding.WorkerJobCardBinding
import com.example.hp.thejobmanager.viewModel.WorkerJobViewModel

class WorkerJobAdapter(private val context: Context, private val arrayList: ArrayList<WorkerJobViewModel>):
    RecyclerView.Adapter<WorkerJobAdapter.customView>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customView {

        val layoutInflater= LayoutInflater.from(parent.context)

        val jobdisplaydesignBinding: WorkerJobCardBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.workerjobcard,parent,false)

        return customView(jobdisplaydesignBinding)
    }

    override fun getItemCount(): Int {


        return arrayList.size
    }

    override fun onBindViewHolder(holder: customView, position: Int) {

        val jobListViewModel: WorkerJobViewModel =arrayList[position]
        holder.bind(jobListViewModel)
    }


    class customView(val jobdisplaydesignBinding: WorkerJobCardBinding): RecyclerView.ViewHolder(jobdisplaydesignBinding.root){

        fun bind(jobListViewModel: WorkerJobViewModel){



            this.jobdisplaydesignBinding.workerjobmodel=jobListViewModel
            jobdisplaydesignBinding.executePendingBindings()
        }

    }
}