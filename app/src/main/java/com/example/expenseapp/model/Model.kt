package com.example.expenseapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Model(
    @PrimaryKey(autoGenerate = true)
    val id:Long? = null,
    val tripImage:ByteArray? = null,
    val tripName:String? = null
    /*val destination : List<Trip>? = null*/
)
