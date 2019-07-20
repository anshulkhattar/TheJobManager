package com.example.hp.thejobmanager.Supervisor


import android.location.Geocoder
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hp.thejobmanager.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class AddNewJobFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View= inflater.inflate(R.layout.fragment_add_new_job, container, false)

        var addButton: Button =view.findViewById(R.id.add)

        addButton.setOnClickListener { addJob() }

        return view
    }
    fun addJob() {
        var mProfile = view!!.findViewById<EditText>(R.id.profile)
        var mDuration = view!!.findViewById<EditText>(R.id.duration)
        var mLocation = view!!.findViewById<EditText>(R.id.location)
        var mRange = view!!.findViewById<EditText>(R.id.range)
        var mPayment = view!!.findViewById<EditText>(R.id.payment)
        var mDate = view!!.findViewById<EditText>(R.id.date)

        var profile: String = mProfile.text.toString()
        var duration: String = mDuration.text.toString()
        var location: String = mLocation.text.toString()
        var range: String = mRange.text.toString()
        var payment: String = mPayment.text.toString()
        var date: String = mDate.text.toString()


        val geocoder = Geocoder(this.activity!!, Locale.getDefault())
        val addresses = geocoder.getFromLocationName(location, 1)
        val address = addresses[0]
        val longitude = address.longitude
        val latitude = address.latitude




        if (!profile.isEmpty() && !duration.isEmpty() && !location.isEmpty() && !range.isEmpty() && !payment.isEmpty() && !date.isEmpty()) {
            var currUser = FirebaseAuth.getInstance().currentUser!!.uid
            var jobStatus: String = "New"
            var ref = FirebaseDatabase.getInstance().getReference("SJob")
            var id = ref.push().key
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

            val t = this.fragmentManager!!.beginTransaction()
            val mFrag = SupervisorJobFragment()
            t.replace(R.id.fragmentcontainer, mFrag)
            t.commit()

            Toast.makeText(this.activity, "Job Added", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this.activity, "Enter all the details", Toast.LENGTH_SHORT).show()

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