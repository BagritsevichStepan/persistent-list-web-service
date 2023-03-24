package com.wmms.testtask3async.controller

import com.wmms.testtask3async.controller.response.ResponseBuilder
import com.wmms.testtask3async.controller.response.transfer.ListVersionTransfer
import com.wmms.testtask3async.exceptions.InvalidRequestBodyException
import com.wmms.testtask3async.form.NewElement
import com.wmms.testtask3async.form.NewValue
import com.wmms.testtask3async.form.OldElement
import com.wmms.testtask3async.service.PersistentListService
import com.wmms.testtask3async.service.PersistentListServiceImpl
import com.wmms.testtask3async.util.EntityManagerUtil

open class PersistentListUpdateController {
    companion object {
        fun getList(id: Long) = ResponseBuilder.buildResponse {
            return@buildResponse createPersistentListService().getList(id)
        }

        fun addElement(id: Long, newElement: NewElement) = ResponseBuilder.buildResponse {
            if (newElement == null) {
                throw InvalidRequestBodyException()
            }
            return@buildResponse ListVersionTransfer(
                createPersistentListService().add(id, newElement.newElement!!)
            )
        }

        fun removeElement(id: Long, oldElement: OldElement) = ResponseBuilder.buildResponse {
            if (oldElement == null) {
                throw InvalidRequestBodyException()
            }
            return@buildResponse ListVersionTransfer(
                createPersistentListService().remove(id, oldElement.oldElement!!)
            )
        }

        fun setElement(id: Long, newValue: NewValue) = ResponseBuilder.buildResponse {
            if (newValue == null) {
                throw InvalidRequestBodyException()
            }
            return@buildResponse ListVersionTransfer(
                createPersistentListService().set(id, newValue.oldValue!!, newValue.newValue!!)
            )
        }

        fun removeElementWithPathValue(id: Long, oldElement: Int) = ResponseBuilder.buildResponse {
            return@buildResponse ListVersionTransfer(createPersistentListService().remove(id, oldElement))
        }

        fun createPersistentListService(): PersistentListService {
            val listService = PersistentListServiceImpl()
            listService.entityManager = EntityManagerUtil.getEntityManager()
            return listService
        }
    }
}