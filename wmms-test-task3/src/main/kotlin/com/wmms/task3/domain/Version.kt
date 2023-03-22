package com.wmms.task3.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull

@Entity
@Table
data class Version(
    @Id
    @GeneratedValue
    var id: Long? = null,

    @NotNull
    @ElementCollection
    @Column
    var list: List<Int>? = null
)