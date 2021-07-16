package com.expleo.androidkotlintutorial.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.expleo.contactdemo.model.Contact
import com.expleo.task.R

class ContactAdapter(var listItems : ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var txtName = view.findViewById<TextView>(R.id.txtName)
        var txtNumber = view.findViewById<TextView>(R.id.txtNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context).inflate(R.layout.cell_contact, parent, false)

        return MyViewHolder(layoutInflater)
    }

    override fun getItemCount(): Int {

        Log.d("Size", "" + listItems.size)

        return listItems.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        var contact : Contact = listItems[position]
        holder.txtName.text = contact.name
        holder.txtNumber.text = contact.number
    }

}