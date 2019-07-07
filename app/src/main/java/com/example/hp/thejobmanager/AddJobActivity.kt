package com.example.hp.thejobmanager

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button

class AddJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_job)

        var add:FloatingActionButton=findViewById(R.id.fab_add)

        add.setOnClickListener { val intent = Intent(this, NewJobActivity::class.java)
            startActivity(intent) }
    }
}
