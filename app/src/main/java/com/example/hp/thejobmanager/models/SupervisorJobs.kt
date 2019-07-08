package com.example.hp.thejobmanager.models

class SupervisorJobs {
    var jprofile:String=""
    var jduration:String=""
    var jpayment:String=""
    var jcreator:String=""
    var jrange:String=""
    var jlocation:String=""
    var jdate:String=""
    var jstatus:String=""

    constructor()
    constructor(
        jprofile: String,
        jduration: String,
        jpayment: String,
        jcreator: String,
        jrange: String,
        jlocation: String,
        jdate: String,
        jstatus: String
    ) : super() {
        this.jprofile = jprofile
        this.jduration = jduration
        this.jpayment = jpayment
        this.jcreator = jcreator
        this.jrange = jrange
        this.jlocation = jlocation
        this.jdate = jdate
        this.jstatus = jstatus
    }
}