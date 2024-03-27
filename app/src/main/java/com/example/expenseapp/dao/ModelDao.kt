package com.example.expenseapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expenseapp.model.Model


@Dao
interface ModelDao {

    //
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertModel(model: Model)

    // read
    @Query("SELECT * FROM Model") // this query give us all the data
    fun getModel(): MutableList<Model>

    @Query("SELECT * FROM Model WHERE id= :id1") // and this query give us particular 1 data
    fun getModel(id1: Int): Model

    // update
    @Update
    fun updateTrip(model: Model)

    // delete
    @Delete
    fun deleteTrip(model: Model)

    @Query("DELETE  FROM Model WHERE id= :id1") // and this query give us particular 1 data
    fun deleteById(id1: Long)

}
