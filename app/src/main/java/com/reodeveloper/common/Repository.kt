package com.reodeveloper.common

import com.reodeveloper.marvelpay.data.Specification

open class Repository<T>(val datasource: DataSource<T>) {

    open fun getAll(): List<T> {
        return datasource.getAll()
    }

    open fun queryList(specification: Specification): List<T> {
        return datasource.queryList(specification)
    }

    open fun queryItem(specification: Specification): T {
        return datasource.queryItem(specification)
    }

}