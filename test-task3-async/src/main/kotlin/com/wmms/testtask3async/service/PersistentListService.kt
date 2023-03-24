package com.wmms.testtask3async.service

interface PersistentListService: ListService<Long, Int>, PersistentService<Long> {
    fun createEmptyList(): Long
}