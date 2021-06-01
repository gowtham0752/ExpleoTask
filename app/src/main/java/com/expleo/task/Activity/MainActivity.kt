package com.expleo.task.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.expleo.task.R
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val PERMISSION_REQUEST_CODE = 200

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
                if(checkPermission()){
                    openQR()
                }else{
                    requestPermission()
                }
            }
            R.id.cantact_card ->{
                startActivity(Intent(this, ContactActivity::class.java))
            }
            R.id.profile_card ->{
              startActivity(Intent(this, ProfilePictureActivity::class.java))
            }
        }
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

    private fun openQR(){
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        integrator.setPrompt("Scan a barcode")
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(false)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                openQR()
            } else {
                Toast.makeText(applicationContext, "Permission not granted", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}