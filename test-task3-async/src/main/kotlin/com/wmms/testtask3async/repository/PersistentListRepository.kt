package com.wmms.testtask3async.repository

import com.wmms.testtask3async.domain.PersistentList
import com.wmms.testtask3async.exceptions.InvalidIdException
import javax.persistence.EntityManager
import javax.transaction.Transactional

class PersistentListRepository {
    companion object {
        @Transactional
        public fun findPersistentListById(id: Long, entityManager: EntityManager): PersistentList =
            entityManager.find(PersistentList::class.java, id) ?: throw InvalidIdException(id)


        @Transactional
        public fun getAllIds(entityManager: EntityManager) =
            entityManager.createQuery("SELECT e.id FROM PersistentList e")
                .resultList as List<Long>

        @Transactional
        public fun addPersistentList(persistentList: PersistentList, entityManager: EntityManager) {
            entityManager.persist(persistentList)
        }
    }
}