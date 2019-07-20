package com.example.hp.thejobmanager.models

class Supervisor {
    var uname:String=""
    var uemail:String=""
    var uphone:String=""
    var uid:String=""

    constructor()

    constructor(
        uname:String,
        uemail:String,
        uphone:String,
        uid:String

    ) : super() {
        this.uname = uname
        this.uemail = uemail
        this.uphone = uphone
        this.uid = uid
    }

}