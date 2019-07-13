package com.example.hp.thejobmanager.SupervisorActivities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.location.Geocoder
import com.example.hp.thejobmanager.R
import java.util.*


class NewJobActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_job)

        var addButton: Button =findViewById(R.id.add)

        addButton.setOnClickListener { addJob() }
    }
    fun addJob(){
        var mProfile = findViewById<EditText>(R.id.profile)
        var mDuration = findViewById<EditText>(R.id.duration)
        var mLocation = findViewById<EditText>(R.id.location)
        var mRange = findViewById<EditText>(R.id.range)
        var mPayment = findViewById<EditText>(R.id.payment)
        var mDate = findViewById<EditText>(R.id.date)

        var profile: String = mProfile.text.toString()
        var duration: String = mDuration.text.toString()
        var location: String = mLocation.text.toString()
        var range: String = mRange.text.toString()
        var payment: String = mPayment.text.toString()
        var date: String = mDate.text.toString()



        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(location, 1)
        val address = addresses[0]
        val longitude = address.longitude
        val latitude = address.latitude




        if (!profile.isEmpty() && !duration.isEmpty() && !location.isEmpty() && !range.isEmpty() && !payment.isEmpty() && !date.isEmpty()) {
            var currUser=FirebaseAuth.getInstance().currentUser!!.uid
            var jobStatus:String="New"
            var ref=FirebaseDatabase.getInstance().getReference("SJob")
            var id=ref.push().key
            var job = Job(
                profile,
                duration,
                location,
                range,
                payment,
                date,
                currUser,
                jobStatus,
                latitude,
                longitude,
                id
            )
            ref.child(id).setValue(job)


            Toast.makeText(this, "Job Added", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, AddJobActivity::class.java)
            startActivity(intent)
        }

        else{
            Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show()

        }
    }
}
data class Job(
    var jProfile:String,
    var jDuration:String,
    var jLocation:String,
    var jRange:String,
    var jPayment:String,
    var jDate:String,
    var jCreator:String,
    var jStatus:String,
    var jlat:Double,
    var jlong: Double,
    var jid:String
)
