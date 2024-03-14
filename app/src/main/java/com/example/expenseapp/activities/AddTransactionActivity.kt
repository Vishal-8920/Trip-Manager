package com.example.expenseapp.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.expenseapp.R
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityAddTransactionBinding
import com.example.expenseapp.model.Trip

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private lateinit var userName: String
    private var amount:Double = 0.0
    private lateinit var paymentType:String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar2)
        setTitle("Add Transaction")

       binding.paymentMethod.setOnClickListener {
          dialog()
       }


        // db version 1
        /*val bundle = intent.extras
        if (bundle != null) {
            val tripImage = bundle.getByteArray("tripImage")
            val tripName = bundle.getString("tripName")
            val bitmap = BitmapFactory.decodeByteArray(tripImage, 0, tripImage!!.size)
            binding.tripImg .setImageBitmap(bitmap)
            binding.tripN.text = tripName
         }*/
        binding.saveTransaction.setOnClickListener {

            /*val tripImage = bundle!!.getByteArray("tripImage")
            val tripName = bundle.getString("tripName")
             binding.tripN.text = tripName*/
            userName = binding.nameNote.text.toString()
            amount = binding.amount.text.toString().toDouble()
            paymentType = binding.paymentType.text.toString()

            val trip = Trip(null, userName, amount, paymentType)
             DbBuilder.getdb(this)!!.tripDao().insertTrip(trip)
            Toast.makeText(this, "Data is inserted", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, TransactionHistoryActivity::class.java)
            startActivity(intent)
            finish()
        }



    }

    private fun dialog(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_box_payment_method)
        dialog.show()

    }
}