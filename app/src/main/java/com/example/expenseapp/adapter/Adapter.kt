package com.example.expenseapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.expenseapp.activities.AddTransactionActivity
import com.example.expenseapp.activities.TransactionHistoryActivity
import com.example.expenseapp.databinding.RvItemBinding
import com.example.expenseapp.model.Model


class Adapter(private var dataList: List<Model>, private val onTripDelete: (model: Model) -> Unit) :
    RecyclerView.Adapter<Adapter.MyViewHolder>() {

    class MyViewHolder(var binding: RvItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val model: Model = dataList[position]
        // here we are convert image to bitmap
        val bitmap = BitmapFactory.decodeByteArray(model.tripImage, 0, model.tripImage!!.size)

        holder.binding.tripImage.setImageBitmap(bitmap)
        holder.binding.addTripName.text = model.tripName
        holder.itemView.setOnClickListener {


            val intent = Intent(holder.itemView.context, TransactionHistoryActivity::class.java)
            // intent.putExtras(bundle)
            holder.itemView.context.startActivity(intent)


        }

        holder.itemView.setOnLongClickListener {
            showDeleteDialoge(model, holder.itemView.context)
            true
        }
    }

    private fun showDeleteDialoge(model: Model, context: Context?) {
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle("Delete Trip")
        builder.setMessage("Are you sure want to delete this trip")
        builder.setPositiveButton("Yes") { dialog, which ->
            onTripDelete(model)

        }
        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()

        }
        builder.show()


    }
}








