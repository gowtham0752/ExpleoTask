package com.expleo.task.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.expleo.task.R
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        init()

//        val crashButton = Button(this)
//        crashButton.text = "Crash!"
//        crashButton.setOnClickListener {
//            throw RuntimeException("Test Crash") // Force a crash
//        }
//        addContentView(crashButton, ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT))
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
                if (checkPermissionForCamera()){
                    openQR()
                }else{
                    requestPermissionForCamera()
                }
            }
            R.id.cantact_card ->{

            }
            R.id.profile_card ->{
              startActivity(Intent(this, ProfilePictureActivity::class.java))
            }
        }
    }

    fun openQR(){
        Log.d("QR","---------------->QR Started")
        val integrator = IntentIntegrator(this)
        Log.d("QR","---------------->QR Initialized")
        //integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES)
        integrator.setPrompt("Scan a barcode")
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(false)
        integrator.setOrientationLocked(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()

    }

    fun checkPermissionForCamera(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED )
        } else false
    }

    fun requestPermissionForCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA),
                100
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(applicationContext,"No data found", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }


}