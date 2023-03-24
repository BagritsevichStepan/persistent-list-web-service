package com.wmms.testtask3async.service

import com.wmms.testtask3async.domain.PersistentList
import com.wmms.testtask3async.exceptions.ElementNotFoundException
import com.wmms.testtask3async.repository.PersistentListRepository
import java.util.function.Supplier
import javax.persistence.EntityManager

class PersistentListServiceImpl: PersistentListService {
    lateinit var entityManager: EntityManager

    override fun getList(id: Long) = performTransaction {
        return@performTransaction getAndCopyPersistentList(id)
    }

    override fun add(id: Long, value: Int): Long = performTransaction {
        var list = getAndCopyPersistentList(id)
        list.add(value)
        return@performTransaction addPersistentListAndGetId(list)
    }

    override fun remove(id: Long, element: Int): Long = performTransaction {
        var list = getAndCopyPersistentList(id)
        if (!list.remove(element)) {
            throw ElementNotFoundException(element.toString())
        }
        return@performTransaction addPersistentListAndGetId(list)
    }

    override fun set(id: Long, element: Int, value: Int): Long = performTransaction {
        var list = getAndCopyPersistentList(id)
        if (!list.contains(element)) {
            throw ElementNotFoundException(element.toString())
        }
        list[list.indexOf(element)] = value
        return@performTransaction addPersistentListAndGetId(list)
    }

    override fun createEmptyList(): Long = performTransaction {
        return@performTransaction addPersistentListAndGetId(ArrayList())
    }

    override fun getVersions(): List<Long> = performTransaction {
        return@performTransaction PersistentListRepository.getAllIds(entityManager)
    }

    private fun getAndCopyPersistentList(id: Long) = copyList(
        PersistentListRepository.findPersistentListById(id, entityManager).listVersion!!
    )

    private fun copyList(list: MutableList<Int>) = ArrayList(list)

    private fun addPersistentListAndGetId(list: MutableList<Int>): Long {
        val persistentList = PersistentList()
        persistentList.listVersion = list
        PersistentListRepository.addPersistentList(persistentList, entityManager)
        return persistentList.id!!
    }

    private fun <T> performTransaction(transaction: Supplier<T>): T {
        entityManager.transaction.begin()
        val result = transaction.get()
        entityManager.transaction.commit()
        entityManager.close()
        return result
    }

    private fun beginTransaction() {
        entityManager.transaction.begin()
    }

    private fun commitTransaction() {
        entityManager.transaction.commit()
    }

    private fun close() {
        entityManager.close()
    }
}