package com.expleo.task.Activity

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.expleo.task.Fragments.CameraFragment
import com.expleo.task.R
import kotlinx.android.synthetic.main.activity_profile_picture.*

class ProfilePictureActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 200
    private val PERMISSION_REQUEST_CODE_STORAGE = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_picture)

        profile_card.setOnClickListener {
           uploadProfile()
        }
    }

    private fun uploadProfile(){
        if(checkPermission()){
            if(checkPermissionStorage()){
                showCamera()
            }else{
                requestPermissionStorage()
            }
        }else{
            requestPermission()
        }
    }

    private fun checkPermissionStorage(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED)
        {
            return  false
        } else true
    }

    private fun requestPermissionStorage() {
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE_STORAGE)
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED)
        {
            return false
        } else true
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf<String>(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
    }

    private fun showCamera(){

        val callBack=object: CameraFragment.CameraCallback {
            override fun onImageReceived(bitmap: Bitmap) {
                Glide.with(applicationContext).applyDefaultRequestOptions(RequestOptions().error(R.drawable.profile_pic_defalut))
                    .load(bitmap)
                    .into(profile_image)
            }
        }

        val cameraDialog= CameraFragment(callBack)
        cameraDialog.show(supportFragmentManager,"show_camera")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
                    when (requestCode) {
                        PERMISSION_REQUEST_CODE -> if (grantResults.size> 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                            uploadProfile()

                        } else {
                            Toast.makeText(applicationContext,"Permission not granted", Toast.LENGTH_LONG).show()
            }

            PERMISSION_REQUEST_CODE_STORAGE -> if (grantResults.size> 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                uploadProfile()

            } else {
                Toast.makeText(applicationContext,"Permission not granted", Toast.LENGTH_LONG).show()
            }
        }
    }
}