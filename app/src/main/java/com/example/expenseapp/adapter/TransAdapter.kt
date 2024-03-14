package com.example.expenseapp.adapter

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.expenseapp.databinding.RvTransationBinding
 import com.example.expenseapp.model.Trip

class TransAdapter(private var dataList: List<Trip>) : RecyclerView.Adapter<TransAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: RvTransationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvTransationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val trip: Trip = dataList[position]

       /* val bitmap = BitmapFactory.decodeByteArray(trip.cardImg, 0, trip.cardImg!!.size)
        holder.binding.imgCard.setImageBitmap(bitmap)*/

        holder.binding.name.text = trip.userName
        holder.binding.amountPay.text= trip.amount.toString()
        holder.binding.type.text = trip.paymentType
    }
}