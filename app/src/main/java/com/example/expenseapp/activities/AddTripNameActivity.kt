package com.example.expenseapp.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expenseapp.R
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityAddTripNameBinding
import com.example.expenseapp.model.Model
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream
// ony for storage permission
const val STORAGE_PERMISSION_CODE = 2
const val PICK_IMAGE_REQUEST_CODE = 3


class AddTripNameActivity : AppCompatActivity() {

    private var imageBytes: ByteArray? = null // A nullable byte array to store the image bytes

    private lateinit var binding: ActivityAddTripNameBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTripNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        binding.addTripImg.setOnClickListener {
             // Step 1
            if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                pickImage()

            } else {
                requestStoragePermission()

            }

        }
        binding.btnSave.setOnClickListener {

            val tName = binding.tripName.text.toString()
            if (tName.isNotEmpty() && imageBytes != null) {
                val trip = Model(null, imageBytes, tName)
                val dataList = DbBuilder.getdb(this)!!.modelDao().insertModel(trip)
                Toast.makeText(this, "     Data   saved successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, TransactionHistoryActivity::class.java)
                startActivity(intent)
                finish()

            } else if (imageBytes == null) {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter a trip name ", Toast.LENGTH_LONG).show()
            }


        }

    }
  //  step 2
    private fun pickImage() {
        ImagePicker.with(this)
            .crop()
            .compress(128)
            .galleryMimeTypes(mimeTypes = arrayOf("image/png", "image/jpg", "image/jpeg"))
            .start(PICK_IMAGE_REQUEST_CODE)
    }

    private fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Storage permission is required", Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE)
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri = data?.data
            binding.addTripImg.setImageURI(imageUri)
            imageBytes = bitmapToByteArray(BitmapFactory.decodeFile(imageUri?.path))

        }
        else if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Failed to pick image", Toast.LENGTH_SHORT).show()
        }
    }
    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        return stream.toByteArray()
    }

}


