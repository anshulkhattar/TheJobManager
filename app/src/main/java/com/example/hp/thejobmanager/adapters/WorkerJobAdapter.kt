package com.example.hp.thejobmanager.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.databinding.WorkerJobCardBinding
import com.example.hp.thejobmanager.viewModel.WorkerJobViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WorkerJobAdapter(private val context: Context, private val arrayList: ArrayList<WorkerJobViewModel>,private val keyList:ArrayList<String>):
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

        holder.jobdisplaydesignBinding.accept.setOnClickListener {
            Log.d("p11", "position is$position")

            var key=keyList[position]

            Log.d("k11",key)

            var ref= FirebaseDatabase.getInstance().getReference("SJob").orderByChild("jid").equalTo(key)
            Log.d("ref11",ref.toString())

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("d44", dataSnapshot.toString())
                    if (dataSnapshot.exists()) {
                        Log.d("d33", "reached here")
                        for (jobSnapshot in dataSnapshot.children) {
                            Log.d("d22", jobSnapshot.toString())
                            jobSnapshot.ref.child("jstatus").setValue("accepted")

                            holder.jobdisplaydesignBinding.accept.visibility=GONE
                            holder.jobdisplaydesignBinding.reject.visibility= GONE
                            holder.jobdisplaydesignBinding.statustextView.visibility= VISIBLE
                            holder.jobdisplaydesignBinding.statustextView.text = "Accepted"
                            holder.jobdisplaydesignBinding.statustextView.setTextColor(Color.parseColor("#ff669900"))
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("f111", "onCancelled", databaseError.toException())
                }
            })
            Log.d("key111", keyList[position])




            Log.d("d11","del pressed")
        }

        holder.jobdisplaydesignBinding.reject.setOnClickListener {
            Log.d("p11", "position is$position")

            var key=keyList[position]

            Log.d("k11",key)

            var ref= FirebaseDatabase.getInstance().getReference("SJob").orderByChild("jid").equalTo(key)
            Log.d("ref11",ref.toString())

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("d44", dataSnapshot.toString())
                    if (dataSnapshot.exists()) {
                        Log.d("d33", "reached here")
                        for (jobSnapshot in dataSnapshot.children) {
                            Log.d("d22", jobSnapshot.toString())


                            holder.jobdisplaydesignBinding.accept.visibility=GONE
                            holder.jobdisplaydesignBinding.reject.visibility= GONE
                            holder.jobdisplaydesignBinding.statustextView.visibility= VISIBLE
                            holder.jobdisplaydesignBinding.statustextView.text = "Rejected"
                            holder.jobdisplaydesignBinding.statustextView.setTextColor(Color.parseColor("#ffff4444"))
                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("f111", "onCancelled", databaseError.toException())
                }
            })
            Log.d("key111", keyList[position])




            Log.d("d11","del pressed")
        }


    }


    class customView(val jobdisplaydesignBinding: WorkerJobCardBinding): RecyclerView.ViewHolder(jobdisplaydesignBinding.root){

        fun bind(jobListViewModel: WorkerJobViewModel){



            this.jobdisplaydesignBinding.workerjobmodel=jobListViewModel
            jobdisplaydesignBinding.executePendingBindings()
        }

    }

    fun removeAt(position: Int) {
        arrayList.removeAt(position)
        notifyItemRemoved(position)
    }
}
