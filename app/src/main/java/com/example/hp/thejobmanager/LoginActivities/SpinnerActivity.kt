package com.example.hp.thejobmanager.LoginActivities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.Supervisor.SupervisorActivity
import com.example.hp.thejobmanager.WorkerActivities.WorkerJobsDisplayActivity
import com.example.hp.thejobmanager.WorkerActivity
import com.example.hp.thejobmanager.userDetailActivities.SupervisorDetailsActivity
import com.example.hp.thejobmanager.userDetailActivities.WorkerDetailsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SpinnerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spinner)


        var spinner: Spinner = this.findViewById(R.id.spinner)



        var typeOfProfile = arrayOf("None","Supervisor", "Worker")


        val adapter = ArrayAdapter(
            this, // Context
            R.layout.spinner_item, // Layout
            typeOfProfile // Array
        )

        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with adapter
        spinner.adapter = adapter


        var abc = 0



        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long) {
                // Display the selected item text on text view

                if(parent.getItemAtPosition(position)=="Supervisor") {

                    //Check if user exists in database and directly go to jobs screen
                    val ref = FirebaseDatabase.getInstance().getReference("Supervisor").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }


                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                goToCreateJobs()
                            }
                            else{
                                callSupervisor()
                            }
                        }


                    })


                }
                else if(parent.getItemAtPosition(position)=="Worker"){
                    val ref = FirebaseDatabase.getInstance().getReference("Worker").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    ref.addValueEventListener(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }


                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                goToWorkerJobs()
                            }
                            else{
                                callWorker()
                            }
                        }


                    })
                }
                else{

                }


            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }

        }

    }
    fun goToWorkerJobs(){
        val intent = Intent(this, WorkerActivity::class.java)
        startActivity(intent)
    }
    fun goToCreateJobs(){
        val intent = Intent(this, SupervisorActivity::class.java)
        startActivity(intent)
    }

    fun callSupervisor(){
        val intent = Intent(this, SupervisorDetailsActivity::class.java)
        startActivity(intent)
    }
    fun callWorker(){
        val intent = Intent(this, WorkerDetailsActivity::class.java)
        startActivity(intent)
    }
}

