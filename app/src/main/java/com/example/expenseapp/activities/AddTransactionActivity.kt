package com.example.expenseapp.activities

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expenseapp.R
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityAddTransactionBinding
import com.example.expenseapp.model.Trip
import java.io.ByteArrayOutputStream

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var userName: String
    private var amount:Double = 0.0
    private lateinit var paymentType:String
    private   var paymentMethodImage: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        setUpListeners()

        enableEdgeToEdge()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar2)
        setTitle("Add Transaction")



        binding.saveTransaction.setOnClickListener {

            userName = binding.nameNote.text.toString()
            amount = binding.amount.text.toString().toDouble()
            paymentType = binding.paymentType.text.toString()
            val tripImage = paymentMethodImage

            val trip = Trip(null,tripImage, userName, amount, paymentType)
             DbBuilder.getdb(this)!!.tripDao().insertTrip(trip)
            Toast.makeText(this, "Add Transaction successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    private fun setUpListeners() {
        binding.paymentMethodImg.setOnClickListener {
           dialog()

        }

    }

    private fun dialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box_payment_method)

        val cashImage = dialog.findViewById<ImageView>(R.id.cash)
        val paytmImage = dialog.findViewById<ImageView>(R.id.paytm)
        val phonePeImage = dialog.findViewById<ImageView>(R.id.phonePe)
        val gPayImage = dialog.findViewById<ImageView>(R.id.gPay)

        cashImage.setOnClickListener {

            setPaymentMethodImage(R.drawable.image,dialog)


        }
        paytmImage.setOnClickListener {
            setPaymentMethodImage(R.drawable.paytm,dialog)
        }
        phonePeImage.setOnClickListener {
            setPaymentMethodImage(R.drawable.phonepe,dialog)
        }
        gPayImage.setOnClickListener {
            setPaymentMethodImage(R.drawable.gpay,dialog)
        }

        dialog.show()

    }

   
    private fun setPaymentMethodImage(drawableId:Int,dialog: Dialog){
        val bitmap = BitmapFactory.decodeResource(resources,drawableId)
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
        paymentMethodImage = stream.toByteArray()
        binding.paymentMethodImg.setImageBitmap(bitmap)
        dialog.dismiss()
    }
}