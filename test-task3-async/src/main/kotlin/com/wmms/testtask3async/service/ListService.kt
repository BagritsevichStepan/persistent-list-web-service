package com.wmms.testtask3async.service

interface ListService<T, V> {
    fun getList(list: T): List<V>
    fun add(list: T, value: V): T
    fun remove(list: T, element: V): T
    fun set(list: T, element: V, value: V): T
}