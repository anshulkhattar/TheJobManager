package com.example.hp.thejobmanager.viewModel

import android.annotation.SuppressLint
import android.app.Service
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import com.example.hp.thejobmanager.MyLocation
import com.example.hp.thejobmanager.models.SupervisorJobs
import com.example.hp.thejobmanager.models.Worker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.android.gms.location.LocationResult




class WorkerJobViewModel:ViewModel {

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

    constructor(
        job: SupervisorJobs
    ) : super() {
        this.jprofile = job.jprofile
        this.jduration = job.jduration
        this.jpayment = job.jpayment
        this.jcreator = job.jcreator
        this.jrange = job.jrange
        this.jlocation = job.jlocation
        this.jdate = job.jdate
        this.jstatus = job.jstatus
        this.jlong=job.jlong
        this.jlat=job.jlat
    }

    var wlat:Double=0.0
    var wlong:Double=0.0
    var jlatitude:Double=0.0
    var jlongitude:Double=0.0
    var radius:Double=0.0

    val keyList = ArrayList<String>()


    var arrayListMutableLiveData= MutableLiveData<ArrayList<WorkerJobViewModel>>()

    var arrayList=ArrayList<WorkerJobViewModel>()



    fun getArrayList(): MutableLiveData<ArrayList<WorkerJobViewModel>> {

        var userListener=FirebaseDatabase.getInstance().getReference("Worker").child(FirebaseAuth.getInstance().currentUser!!.uid)
        userListener.addValueEventListener(object:ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {
                if(p0!!.exists()){
                    var worker=p0.getValue(Worker::class.java)

                    wlat=worker!!.ulat
                    wlong=worker!!.ulong
                }
            }
        })

        Log.d("1111","reached 1")
        val jobListener = FirebaseDatabase.getInstance().getReference("SJob")
        jobListener.addValueEventListener(object : ValueEventListener {


            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {

                    Log.d("222","reached 2")

                    for (jobSnapshot in dataSnapshot.children) {
                        keyList.add(jobSnapshot.child("jid").value as String)
                        Log.d("jid",keyList.toString())
                        var job: SupervisorJobs = jobSnapshot.getValue(SupervisorJobs::class.java)!!

                        jlatitude=job.jlat
                        jlongitude=job.jlong

                        radius=job.jrange.toDouble()


                        var dist = FloatArray(1)

                        Location.distanceBetween(
                            jlatitude,
                            jlongitude,
                            wlat,
                            wlong,
                            dist
                        )
                        if (dist[0] / 1000 < radius) {
                            //here your code or alert box for outside 1Km radius area

                            var jobs= WorkerJobViewModel(job)

                            arrayList!!.add(jobs)

                            Log.d("333","reached 3")

                            arrayListMutableLiveData.value=arrayList

                        }


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
    fun getKey(): ArrayList<String> {
        return keyList
    }

}