package com.example.expenseapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityAddTripBinding
import com.example.expenseapp.model.Model
import com.github.dhaval2404.imagepicker.ImagePicker
import java.io.ByteArrayOutputStream

class AddTrip : AppCompatActivity() {

    private var imageBytes: ByteArray? = null // A nullable byte array to store the image bytes

    private lateinit var binding: ActivityAddTripBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTripBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.addTripImg1.setOnClickListener {
            PickImg()
        }

        binding.btnSaveTrip.setOnClickListener {

            val tripName = binding.tripName.text.toString()
            if(tripName.isNotEmpty() && imageBytes != null){

                val trip = Model(null,imageBytes,tripName)
                DbBuilder.getdb(this)?.modelDao()?.insertModel(trip)
                Toast.makeText(this, "Trip Save Successfully", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else if (imageBytes == null){
                Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Please enter a trip name ", Toast.LENGTH_LONG).show()
            }
        }

    }
  // step 1
    private fun PickImg(){
      ImagePicker.with(this)
          .crop()	    			//Crop image(Optional), Check Customization for more option
          .compress(1024)			//Final image size will be less than 1 MB(Optional)
          .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
          .galleryMimeTypes(
              mimeTypes = arrayOf(
              "image/png",
              "image/jpg",
              "image/jpeg"))
          .start()
    }
   // step 2
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val imgUri: Uri = data?.data!!
            // Use Uri object instead of File to avoid storage permissions
             binding.addTripImg1.setImageURI(imgUri)
            imageBytes = bitmapToByteArray(BitmapFactory.decodeFile(imgUri?.path))

        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }
   // step 3
    private fun bitmapToByteArray(bitmap: Bitmap?): ByteArray? {
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, stream)
        return stream.toByteArray()
    }

}