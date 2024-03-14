package com.example.expenseapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.expenseapp.R
import com.example.expenseapp.adapter.TransAdapter
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityTransactionHistoryBinding

class TransactionHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionHistoryBinding
    private lateinit var adapter: TransAdapter
    var flag = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTransactionHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.materialToolbar)
        setTitle("Transaction History")

        // here we are check intent apne saath kuch la raha h kya
        if (intent.hasExtra("FLAG")){
            flag =intent.getIntExtra("FLAG",-1)
        }
        if (flag==1){

        }

        binding.floatingActionButton2.setOnClickListener {
            startActivity(Intent(this,AddTransactionActivity::class.java))
        }

        val transList = DbBuilder.getdb(this)!!.tripDao().getTrip()
        adapter = TransAdapter(transList)
        binding.rvH.layoutManager = LinearLayoutManager(this)
        binding.rvH.adapter = adapter
        adapter.notifyDataSetChanged()

    }
}