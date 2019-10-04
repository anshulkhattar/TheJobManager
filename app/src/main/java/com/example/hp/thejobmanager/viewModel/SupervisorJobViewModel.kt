package com.example.hp.thejobmanager.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.hp.thejobmanager.models.SupervisorJobs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SupervisorJobViewModel:ViewModel {

    var jprofile:String=""
    var jduration:String=""
    var jpayment:String=""
    var jcreator:String=""
    var jrange:String=""
    var jlocation:String=""
    var jdate:String=""
    var jstatus:String=""
    var jlat:Double=0.0
    var jlong:Double=0.0
    var jid:String=""

    constructor()

    constructor(job: SupervisorJobs) : super() {
        this.jprofile = job.jprofile
        this.jduration = job.jduration
        this.jpayment = job.jpayment
        this.jcreator = job.jcreator
        this.jrange = job.jrange
        this.jlocation = job.jlocation
        this.jdate = job.jdate
        this.jstatus = job.jstatus
        this.jlat=jlat
        this.jlong=jlong
        this.jid=jid
    }

    val keyList = ArrayList<String>()
    val items = ArrayList<SupervisorJobs>()

    var arrayListMutableLiveData= MutableLiveData<ArrayList<SupervisorJobViewModel>>()

    var arrayList=ArrayList<SupervisorJobViewModel>()

    var ref= FirebaseDatabase.getInstance().getReference("SJob")!!

    fun getArrayList():MutableLiveData<ArrayList<SupervisorJobViewModel>>{

        Log.d("1111","reached 1")

        val jobListener = ref.orderByChild("jcreator").equalTo(FirebaseAuth.getInstance().currentUser!!.uid)
        jobListener.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {


                    Log.d("222","reached 2")

                    arrayList.clear()

                    for (jobSnapshot in dataSnapshot.children) {
                        keyList.add(jobSnapshot.child("jid").value as String)
                        Log.d("jid",keyList.toString())
                        items.add(jobSnapshot.getValue(SupervisorJobs::class.java)!!)

                        var job: SupervisorJobs = jobSnapshot.getValue(SupervisorJobs::class.java)!!

                        var jobs= SupervisorJobViewModel(job)

                        arrayList!!.add(jobs)

                        Log.d("333","reached 3")

                        arrayListMutableLiveData.value=arrayList


                    }
                }

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        Log.d("1111","reached 1")


        return arrayListMutableLiveData
    }
    fun getKey(): ArrayList<String> {
        return keyList
    }
}
