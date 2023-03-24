package com.wmms.testtask3async.domain

import java.io.Serializable
import javax.persistence.*

@Entity
@Table
open class PersistentList: Serializable {
    @Id
    @GeneratedValue
    var id: Long? = null

    @ElementCollection
    @Column(nullable = false)
    var listVersion: MutableList<Int>? = null
}