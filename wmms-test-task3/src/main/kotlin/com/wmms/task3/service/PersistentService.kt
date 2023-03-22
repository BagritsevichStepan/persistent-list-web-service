package com.wmms.task3.service

interface PersistentService<T> {
    fun getVersions(): List<T>
}