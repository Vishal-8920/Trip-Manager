package com.example.expenseapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.expenseapp.R
import com.example.expenseapp.adapter.Adapter
import com.example.expenseapp.database.DbBuilder
import com.example.expenseapp.databinding.ActivityMainBinding
import com.example.expenseapp.model.Model


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var adapter: Adapter
    private var modelList = mutableListOf<Model>()

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
         modelList = DbBuilder.getdb(this)!!.modelDao().getModel()
        adapter = Adapter( modelList){model ->
            showDeleteDialoge(model)
        }



        binding.rv.layoutManager = GridLayoutManager(this, 2)
        binding.rv.adapter = adapter
        adapter.notifyDataSetChanged()


    }

    private fun showDeleteDialoge(model: Model) {

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Conform Delete Trip")
        dialog.setMessage(" Are you sure want to delete this trip ")
        dialog.setPositiveButton("yes"){ dialog, which ->
            val modeDoa = DbBuilder.getdb(this)!!.modelDao() ?: return@setPositiveButton
            modeDoa.deleteById(model.id!!)
            modelList.remove(model)
            adapter.notifyDataSetChanged()
            Toast.makeText(this," Delete",Toast.LENGTH_SHORT).show()
        }
        
        dialog.setNegativeButton("No"){ dialog, which ->
            dialog.dismiss()
        }
        dialog.show()

    }
    override fun onBackPressed(){
        finishAffinity()
        super.onBackPressed()
    }
}


