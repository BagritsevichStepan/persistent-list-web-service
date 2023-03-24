package com.wmms.testtask3async.service

interface PersistentService<T> {
    fun getVersions(): List<T>
}