package com.reodeveloper.marvelpay.data.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "contactsTable")
data class DbContact(@PrimaryKey val name: String, val phone: String, val image: String, val selected:Boolean)