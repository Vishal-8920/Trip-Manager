package com.example.expenseapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expenseapp.R
import com.example.expenseapp.adapter.Adapter
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbar)
        setTitle("All Trip")


        binding.floatingActionButton.setOnClickListener {
            val intent = Intent(this, AddTrip::class.java)
            startActivity(intent)
        }
        val modelList = DbBuilder.getdb(this)!!.modelDao().getModel()
        adapter = Adapter( modelList)
        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter
        adapter.notifyDataSetChanged()


    }

}


