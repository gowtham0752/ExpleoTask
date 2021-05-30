package com.expleo.task.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.expleo.task.Fragments.CameraFragment
import com.expleo.task.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

   fun init() {
        qr_card.setOnClickListener(this)
        cantact_card.setOnClickListener(this)
        profile_card.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val id = v?.id
        when(id){
            R.id.qr_card ->{

            }
            R.id.cantact_card ->{

            }
            R.id.profile_card ->{
              startActivity(Intent(this, ProfilePictureActivity::class.java))
            }
        }
    }


}