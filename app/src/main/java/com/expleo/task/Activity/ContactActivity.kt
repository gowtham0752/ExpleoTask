package com.expleo.task.Activity

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expleo.androidkotlintutorial.adapter.ContactAdapter
import com.expleo.contactdemo.model.Contact
import com.expleo.task.R


class ContactActivity : AppCompatActivity() {

    var recyclerView: RecyclerView? = null

    var listContacts = ArrayList<Contact>()
    var adapter: ContactAdapter? = null
    var cursor: Cursor? = null
    var name: String? = null
    var phonenumber:String? = null
    val RequestPermissionCode = 1
    var sharedPreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        recyclerView = findViewById(R.id.recycleContact)
        recyclerView!!.layoutManager = LinearLayoutManager(this)

        sharedPreferences = this.getSharedPreferences("contacts", Context.MODE_PRIVATE)
    }

    override fun onResume() {
        super.onResume()

        var allowed = sharedPreferences!!.getBoolean("allowed", false)
        if (allowed) {
            getContactList()
        } else {
            enableRuntimePermission()
        }
    }


    fun enableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.READ_CONTACTS
            )
        ) {
            Toast.makeText(
                this,
                "CONTACTS permission allows us to Access CONTACTS app",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.READ_CONTACTS
                ), RequestPermissionCode
            )
        }
    }

    fun getContactList(){

        listContacts = ArrayList<Contact>()
        cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val emails = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null)
        while (emails!!.moveToNext()) {
            val email: String = emails!!.getString(emails!!.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS))
            Log.d("MailId","---------------------->${email }")
            var contacts = Contact(email,"")
            listContacts.add(contacts)
        }
        while (cursor!!.moveToNext()) {
            name =
                cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME ))
            phonenumber =
                cursor!!.getString(cursor!!.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            var contact = Contact(name = name!!, phonenumber!!)
            listContacts.add(contact)
        }
        cursor!!.close()
        adapter = ContactAdapter(listContacts)
        recyclerView!!.adapter = adapter
    }

    // callback methods

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RequestPermissionCode -> if (grantResults.size > 0 && grantResults.get(0) === PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Permission Granted, Now your application can access CONTACTS.",
                    Toast.LENGTH_LONG
                ).show()

                var editor = sharedPreferences!!.edit()
                editor.putBoolean("allowed", true)
                editor.commit()
                getContactList()
            } else {
                Toast.makeText(
                    this,
                    "Permission Canceled, Now your application cannot access CONTACTS.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }



}
