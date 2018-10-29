package com.reodeveloper.common

import com.reodeveloper.marvelpay.data.Specification


interface DataSource<T> {
    fun getAll(): List<T>
    fun queryList(specification: Specification): List<T>
    fun queryItem(specification: Specification): T
}