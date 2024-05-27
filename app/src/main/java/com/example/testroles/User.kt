package com.example.testroles

class User {
    private var uid : String = ""
    private var username : String = ""
    private var email : String = ""
    private var smth : String = ""

    constructor()

    constructor(
        uid: String,
        username: String,
        email: String,
        smth : String,
    ) {
        this.uid = uid
        this.username = username
        this.email = email
        this.smth = smth

    }

    //getters y setters
    fun getUid() : String?{
        return uid
    }

    fun setUid(uid : String){
        this.uid = uid
    }

    fun getUsername() : String?{
        return username
    }

    fun setUsername(username: String){
        this.username = username
    }

    fun getEmail() : String?{
        return email
    }

    fun setEmail(email : String){
        this.email = email
    }

    fun getSmth () : String? {
        return smth
    }

    fun setSmth (smth: String){
        this.smth = smth
    }


}