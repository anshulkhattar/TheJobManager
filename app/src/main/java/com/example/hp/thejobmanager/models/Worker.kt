package com.example.hp.thejobmanager.models

class Worker {

    var uname:String=""
    var uemail:String=""
    var uphone:String=""
    var uprofile:String=""
    var ulat:Double=0.0
    var ulong:Double = 0.0

    constructor()

    constructor(
        uname:String,
        uemail:String,
        uphone:String,
        uprofile:String,
        ulat:Double,
        ulong:Double

    ) : super() {
        this.uname = uname
        this.uemail = uemail
        this.uphone = uphone
        this.ulat = ulat
        this.ulong = ulong
    }

}