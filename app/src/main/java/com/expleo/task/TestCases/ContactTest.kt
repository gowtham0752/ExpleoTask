package com.expleo.task.TestCases


import com.expleo.contactdemo.model.Contact


object ContactTest {
    val TAG = ContactTest.javaClass.name

    fun searchNumber(contacts: ArrayList<Contact>, number:String):Boolean{
        var default = false
        for(checkNumber in contacts){
            var numeric = false
            numeric =  number.matches("[-+]?\\d+(\\.\\d+)?".toRegex())
            if (numeric){
                if (checkNumber.number!!.contains(number)){
                    default =  true
                    break
                }
                else
                    default = false
            }
            else{
                if (checkNumber.name!!.contains(number, true)){
                    default =  true
                    break
                }
                else
                    default = false
            }
        }
        return default
    }

    fun validateEmailId(email:String):Boolean{
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        if(email.matches(emailPattern))
          return  true
        else
          return false
    }
}