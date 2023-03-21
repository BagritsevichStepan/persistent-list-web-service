package com.wmms.task3.service

import com.wmms.task3.domain.Version

interface PersistentListService: ListService<Long, Int>, PersistentService<Long> {
    fun createEmptyList(): Version
}