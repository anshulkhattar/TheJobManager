package com.example.hp.thejobmanager.userDetailActivities

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.hp.thejobmanager.R
import com.example.hp.thejobmanager.WorkerActivities.WorkerJobsDisplayActivity
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.example.hp.thejobmanager.models.Worker
import com.google.android.gms.location.FusedLocationProviderClient


class WorkerDetailsActivity : AppCompatActivity() {

    /**
     * Provides the entry point to the Fused Location Provider API.
     */
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    /**
     * Represents a geographical location.
     */
    protected var mLastLocation: Location? = null

    var wlat:Double=0.0
    var wlong:Double=0.0


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_worker_details)
        var submitButton: Button = findViewById(R.id.submit)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        submitButton.setOnClickListener {


            var mName = findViewById<EditText>(R.id.name)
            var mEmail = findViewById<EditText>(R.id.email)
            var mPhone = findViewById<EditText>(R.id.phnNO)
            var mProfile = findViewById<EditText>(R.id.profile)
            var name: String = mName.text.toString()
            var profile: String = mProfile.text.toString()
            var emailid: String = mEmail.text.toString()
            var phnnum: String = mPhone.text.toString()
            var id=FirebaseAuth.getInstance().currentUser!!.uid


            if (!name.isEmpty() && !emailid.isEmpty() && !phnnum.isEmpty() && !profile.isEmpty()) {
                var worker = Worker(name, emailid, phnnum, profile, wlat, wlong,id)
                FirebaseDatabase.getInstance().getReference("Worker")
                    .child(FirebaseAuth.getInstance().currentUser!!.uid)
                    .setValue(worker)


                Toast.makeText(this, "Welcome", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, WorkerJobsDisplayActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show()

            }

        }
    }

    public override fun onStart() {
        super.onStart()

        if (!checkPermissions()) {
            Toast.makeText(this,"Please allow location permissions",Toast.LENGTH_SHORT).show()
        } else {
            getLastLocation()
        }
    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     *
     *
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        mFusedLocationClient!!.lastLocation
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && task.result != null) {
                    mLastLocation = task.result

                    wlat=mLastLocation!!.latitude
                    wlong=mLastLocation!!.longitude


                } else {
                    Log.w(TAG, "getLastLocation:exception", task.exception)
                    showMessage("no location detected")
                }
            }
    }

    /**
     * Shows a [] using `text`.
     * @param text The Snackbar text.
     */
    private fun showMessage(text: String) {

        Toast.makeText(this, text, Toast.LENGTH_LONG).show()

    }

    /**
     * Shows a [].
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * *
     * @param actionStringId   The text of the action item.
     * *
     * @param listener         The listener associated with the Snackbar action.
     */


    /**
     * Return the current state of the permissions needed.
     */
    private fun checkPermissions(): Boolean {
        val permissionState = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return permissionState == PackageManager.PERMISSION_GRANTED
    }



    /**
     * Callback received when a permissions request has been completed.
     */


    companion object {

        private val TAG = "LocationProvider"

        private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
    }
}
