package com.example.expenseapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trip(
    // schema of table
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,  // id stand for serial no
    /*var cardImg :ByteArray?=null,
    var tripName : String?,*/
    var userName : String?,
    var amount :Double,
    var paymentType: String,

)



