package com.example.expenseapp.database

import android.content.Context
import androidx.room.Room
import com.example.expenseapp.utils.dbName

object DbBuilder {

    private  var database:Database? = null

    fun getdb(context: Context):Database? {

        if (database == null){
            database= Room.databaseBuilder(context,
                Database::class.java, dbName)

                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
        return database



    }
}