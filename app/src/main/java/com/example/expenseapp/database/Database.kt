package com.example.expenseapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expenseapp.dao.ModelDao
import com.example.expenseapp.dao.TripDao
import com.example.expenseapp.model.Model
import com.example.expenseapp.model.Trip

@Database(entities = [Model::class,Trip::class] , version = 3)
abstract class Database: RoomDatabase() {
    abstract  fun modelDao(): ModelDao
    abstract fun tripDao(): TripDao
}