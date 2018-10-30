package com.reodeveloper.marvelpay.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(DbContact::class), version = 1)
abstract class MarvelDatabase : RoomDatabase() {

    abstract fun contactsDao(): ContactsDao

    companion object {
        private var INSTANCE: MarvelDatabase? = null
        private val DB_NAME = "database"

        fun getInstance(context: Context): MarvelDatabase {
            if (INSTANCE == null) {
                synchronized(MarvelDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        MarvelDatabase::class.java,
                        DB_NAME)
                        .build()
                }
            }
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }
}