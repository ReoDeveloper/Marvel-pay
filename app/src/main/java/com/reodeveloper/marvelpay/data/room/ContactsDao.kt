package com.reodeveloper.marvelpay.data.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface  ContactsDao {
    @Query("SELECT * from contactsTable")
    fun getAll(): List<DbContact>

    @Insert(onConflict = REPLACE)
    fun insert(item: DbContact)
}