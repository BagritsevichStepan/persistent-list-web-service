package com.wmms.task3.repository

import com.wmms.task3.domain.Version
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PersistentListRepository: JpaRepository<Version, Long> {
    @Query(value = "select p.id from Version p")
    fun getAllIds(): List<Long>
}