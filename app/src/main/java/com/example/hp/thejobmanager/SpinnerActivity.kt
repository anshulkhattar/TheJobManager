package com.example.hp.thejobmanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*

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
                    callSupervisor()
                }
                else if(parent.getItemAtPosition(position)=="Worker"){
                    callWorker()
                }
                else{

                }


            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }

        }

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

