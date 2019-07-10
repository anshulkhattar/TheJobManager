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
    }

    var arrayListMutableLiveData= MutableLiveData<ArrayList<SupervisorJobViewModel>>()

    var arrayList=ArrayList<SupervisorJobViewModel>()

    fun getArrayList():MutableLiveData<ArrayList<SupervisorJobViewModel>>{

        Log.d("1111","reached 1")
        val jobListener = FirebaseDatabase.getInstance().getReference("SJob")
            .orderByChild("jcreator").equalTo(FirebaseAuth.getInstance().currentUser!!.uid)
        jobListener.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    Log.d("222","reached 2")

                    for (jobSnapshot in dataSnapshot.children) {
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


        /*val job1=SupervisorJobs("Cleaner","2hrs","100","me","5km","asdfg","08/07/19","new")
        val job2=SupervisorJobs("hair dresser","2hrs","100","me","5km","asdfg","08/07/19","new")
        Log.d("2222","reached 2")
        val jobListViewModel1= SupervisorJobViewModel(job1)
        val jobListViewModel2 =SupervisorJobViewModel(job2)
        Log.d("3333","reached 3")
        arrayList!!.add(jobListViewModel1)
        arrayList!!.add(jobListViewModel2)
        arrayListMutableLiveData.value=arrayList*/


        return arrayListMutableLiveData
    }

}