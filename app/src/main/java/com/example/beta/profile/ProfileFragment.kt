package com.example.beta.profile

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.beta.R
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment : Fragment() {

    companion object {
        private val REQUEST_IMAGE_CAPTURE = 10
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }

    lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.inspiration).setOnClickListener {
            val profileToMainFeed =
                ProfileFragmentDirections.actionProfileFragmentToMainFeedFragment()
            findNavController().navigate(profileToMainFeed)
        }

        view.findViewById<ImageView>(R.id.settings).setOnClickListener {
            val profileToSettings =
                ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
            findNavController().navigate(profileToSettings)
        }

        view.findViewById<Button>(R.id.captureImage).setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }

        view.findViewById<Button>(R.id.uploadImage).setOnClickListener{
            uploadImage()
        }

        view.findViewById<Button>(R.id.selectImageFromGallery).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (context?.let { it1 ->
                        ContextCompat.checkSelfPermission(
                            it1,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    } ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //Permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE)

                }

                view.findViewById<ImageView>(R.id.profileImage).setOnClickListener {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK_CODE)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (context?.let { it1 ->
                                ContextCompat.checkSelfPermission(
                                    it1,
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                            } ==
                            PackageManager.PERMISSION_DENIED
                        ) {
                            //Permission denied
                            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                            //show popup to request runtime permission
                            requestPermissions(permissions, PERMISSION_CODE)
                        }
                    }
                }
            }

        }
    }

    private fun uploadImage() {
        val progressDialog = ProgressDialog(activity)
        progressDialog.setMessage(("Uploading File . . ."))
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formattter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        val fileName = formattter.format((now))

        val storageReference = FirebaseStorage.getInstance().getReference("image/$fileName")
        storageReference.putFile(ImageUri).
                addOnSuccessListener{
                    view?.findViewById<ImageView>(R.id.imageForAlbum)?.setImageURI(null)
                    Toast.makeText(activity, "Successfully uploaded", Toast.LENGTH_SHORT).show()
                    if(progressDialog.isShowing)progressDialog.dismiss()

                }.addOnFailureListener{

            Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show()
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val bmp = data?.extras?.get("data") as Bitmap
            view?.findViewById<ImageView>(R.id.imageForAlbum)
                ?.setImageBitmap(bmp)
        }
        if (requestCode == IMAGE_PICK_CODE) {
            view?.findViewById<ImageView>(R.id.imageForAlbum)
                ?.setImageURI(data?.data)

            ImageUri = data?.data!!
            view?.findViewById<ImageView>(R.id.imageForAlbum)?.setImageURI(ImageUri)


        }
    }

}
