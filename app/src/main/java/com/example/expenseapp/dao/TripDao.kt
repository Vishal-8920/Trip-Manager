package com.example.expenseapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expenseapp.model.Trip

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.ABORT )
    fun insertTrip(trip : Trip)

     @Query("SELECT * FROM TRIP") // this query give us all the data
    fun getTrip():List<Trip>

    @Query("SELECT * FROM TRIP WHERE id= :id1") // and this query give us particular 1 data
    fun getTripById(id1:Int):Trip


    @Update
    fun updateTrip(trip: Trip)

     @Delete
    fun deleteTrip(trip: Trip)

}