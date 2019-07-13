package com.example.hp.thejobmanager.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.databinding.SupervisorJobCardBinding
import com.example.hp.thejobmanager.viewModel.SupervisorJobViewModel
import com.example.hp.thejobmanager.SupervisorActivities.AddJobActivity
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener


class SupervisorJobAdapter(private val context: Context, private val arrayList: ArrayList<SupervisorJobViewModel>,private val keyList:ArrayList<String>):
    RecyclerView.Adapter<SupervisorJobAdapter.customView>() {

    private val activity : AddJobActivity = context as AddJobActivity


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): customView {


        val layoutInflater= LayoutInflater.from(parent.context)

        val jobdisplaydesignBinding: SupervisorJobCardBinding = DataBindingUtil.inflate(layoutInflater,
            R.layout.supervisorjobcard,parent,false)



        return customView(jobdisplaydesignBinding)
    }


    override fun getItemCount(): Int {


        return arrayList.size
    }

    override fun onBindViewHolder(holder: customView, position: Int) {

        val jobListViewModel:SupervisorJobViewModel=arrayList[position]
        holder.bind(jobListViewModel)


        holder.jobdisplaydesignBinding.delete.setOnClickListener {

            Log.d("p11", "position is$position")

            var key=keyList[position]

            Log.d("k11",key)

           var ref=FirebaseDatabase.getInstance().getReference("SJob").orderByChild("jid").equalTo(key)
            Log.d("ref11",ref.toString())

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("d44", dataSnapshot.toString())
                    if (dataSnapshot.exists()) {
                        Log.d("d33", "reached here")
                        for (jobSnapshot in dataSnapshot.children) {
                            Log.d("d22", jobSnapshot.toString())
                            jobSnapshot.ref.setValue(null)

                            arrayList.removeAt(position)

                            notifyDataSetChanged()
                            //notifyItemRangeChanged(position,itemCount-1)

                        }
                    }
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("f111", "onCancelled", databaseError.toException())
                }
            })
            Log.d("key111", keyList[position])
            //jobListViewModel.keyList.removeAt(position)




            Log.d("d11","del pressed")
        }

    }

    class customView(val jobdisplaydesignBinding: SupervisorJobCardBinding):RecyclerView.ViewHolder(jobdisplaydesignBinding.root){

        fun bind(jobListViewModel: SupervisorJobViewModel){

            this.jobdisplaydesignBinding.supervisorjobmodel=jobListViewModel
            jobdisplaydesignBinding.executePendingBindings()
        }





    }




    }