package com.wmms.task3.service

import com.wmms.task3.domain.Version
import com.wmms.task3.exceptions.ElementNotFoundException
import com.wmms.task3.exceptions.InvalidIdException
import com.wmms.task3.repository.PersistentListRepository
import org.springframework.stereotype.Service

@Service
class PersistentListServiceImpl(var persistentListRepository: PersistentListRepository): PersistentListService {
    override fun getList(id: Long): List<Int> {
        try {
            return persistentListRepository.getReferenceById(id).list!!
        } catch (e: NullPointerException) {
            throw InvalidIdException(id)
        }
    }

    override fun add(id: Long, value: Int): Long {
        val newList = makeNewListVersion(id)
        newList.add(value)
        return saveNewVersionAndGetId(newList)
    }

    override fun remove(id: Long, element: Int): Long {
        val newList = makeNewListVersion(id)
        if (newList.remove(element)) {
            return saveNewVersionAndGetId(newList)
        } else {
            throw ElementNotFoundException(element.toString())
        }
    }

    override fun set(id: Long, element: Int, value: Int): Long {
        val newList = makeNewListVersion(id)
        val elementIndex = newList.indexOf(element)
        if (elementIndex != -1) {
            newList[elementIndex] = value
            return saveNewVersionAndGetId(newList)
        } else {
            throw ElementNotFoundException(element.toString())
        }
    }

    override fun getVersions() = persistentListRepository.getAllIds()

    override fun createEmptyList() = saveNewVersionAndGetId(ArrayList())

    private fun saveNewVersionAndGetId(list: List<Int>) = saveNewVersion(list).id!!

    private fun saveNewVersion(list: List<Int>): Version {
        val newVersion = Version()
        newVersion.list = list
        return persistentListRepository.save(newVersion)
    }

    private fun makeNewListVersion(id: Long) = ArrayList(getList(id))
}