package com.expleo.task.Fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.expleo.task.Constants.Constants.Companion.APPLICATION_ID
import com.expleo.task.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_add_photo.*
import java.io.*



class CameraFragment(var callBack: CameraCallback? = null): BottomSheetDialogFragment() {

    private val CAMERA_REQUEST=221
    private val GALLERY_REQUEST=222
    private lateinit var imageUri:Uri


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_add_photo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        user_camera_group.setOnClickListener {
            openCamera()

        }

        open_gallery_group.setOnClickListener {
            openGallery()
        }


    }

    private fun openCamera(){

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(takePictureIntent.resolveActivity(activity?.packageManager!!) == null){
            Toast.makeText(context,"Error while taking pictur", Toast.LENGTH_LONG).show()
            return
        }

        try{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photo = File(requireContext().cacheDir, "Pic.jpg")
            if(photo.exists()){
                photo.delete()
                photo.createNewFile()
            }
            imageUri = FileProvider.getUriForFile(requireContext(), APPLICATION_ID + ".fileprovider", photo)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            startActivityForResult(intent, CAMERA_REQUEST)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun openGallery(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        val photo = File(getExternalStorageDirectory(), "Pic.jpg")
        if(photo.exists()){
            photo.delete()
            photo.createNewFile()
        }
        imageUri = FileProvider.getUriForFile(requireContext(), APPLICATION_ID + ".fileprovider", photo)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, data.data)
                       callBack?.onImageReceived(bitmap)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, imageUri)
                callBack?.onImageReceived(bitmap)
            } catch (e: IOException) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(activity, "Canceled", Toast.LENGTH_SHORT).show()
        }
        dismiss()
    }


    interface CameraCallback{
        fun onImageReceived(bitmap: Bitmap)
    }

}

