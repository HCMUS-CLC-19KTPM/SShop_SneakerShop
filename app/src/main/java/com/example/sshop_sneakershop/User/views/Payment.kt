package com.example.sshop_sneakershop.User.views

class Payment {
    var name: String = ""
    var type: String = ""
    var number: String = ""
    var since: String = ""

    constructor()

    constructor(name: String, type: String, number: String, since: String) {
        this.name = name
        this.type = type
        this.number = number
        this.since = since
    }

}