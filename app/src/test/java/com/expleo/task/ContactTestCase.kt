package com.expleo.task

import androidx.test.platform.app.InstrumentationRegistry
import com.expleo.contactdemo.model.Contact
import com.expleo.task.TestCases.ContactTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ContactTestCase   {
    val contacts = ArrayList<Contact>()
    fun ArrayList<Contact>.contactList():ArrayList<Contact>{
        contacts.add(Contact("Gowtham","+919578944129"))
        contacts.add(Contact("Senthil","9578944128"))
        contacts.add(Contact("Jayant","9578944127"))
        contacts.add(Contact("Muthu","9578944126"))
        contacts.add(Contact("Thiru","9578944125"))
        contacts.add(Contact("Dhayalu","9578944124"))
        contacts.add(Contact("Siva","9578944123"))
        contacts.add(Contact("Manimekalai","9578944122"))
        return contacts
    }

    @Test
    fun validContryCode() {
        var number = "+919578944129"
        var result = ContactTest.getCountryCode(number, context = InstrumentationRegistry.getInstrumentation().context)
        assertEquals(result, false)

    }

    @Test
    fun validateSearch(){
        var number = "+99819578944129"
        var result = ContactTest.searchNumber(contacts.contactList(), number )
        assertEquals(result, true)
    }

    @Test
    fun emailValidation(){
        var email = "gowtham0605.gg@gmail.com"
        var result = ContactTest.validateEmailId(email )
        assertEquals(result, true)
    }
}