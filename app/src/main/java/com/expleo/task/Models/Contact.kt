package com.expleo.contactdemo.model

//data class Contact(var name: String?, var number : String?) {
//
//    init {
//        this.name = name
//        this.number = number
//    }
//}

class Contact(name:String, number:String) {
    var name: String? = null
    var number: String? = null
    init {
        this.name = name
        this.number = number
    }

}