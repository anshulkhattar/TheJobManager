package com.example.hp.thejobmanager.userDetailActivities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.SupervisorActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SupervisorDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supervisor_details)

        var submitButton: Button = this.findViewById(R.id.submit)

        submitButton.setOnClickListener {

            var mName = findViewById<EditText>(R.id.name)
            var mEmail = findViewById<EditText>(R.id.email)
            var mPhone = findViewById<EditText>(R.id.phnNO)
            var name: String = mName.text.toString()
            var emailid: String = mEmail.text.toString()
            var phnnum: String = mPhone.text.toString()


            if (!name.isEmpty() && !emailid.isEmpty() && !phnnum.isEmpty()) {
                var supervisor = Supervisor(name, emailid, phnnum)
                FirebaseDatabase.getInstance().getReference("Supervisor")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .setValue(supervisor)


                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SupervisorActivity::class.java)
                startActivity(intent)
            }

            else{
                Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show()

            }

        }

    }
}
data class Supervisor(val uname:String,val uemail:String,val uphone:String)
