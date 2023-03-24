package com.wmms.testtask3async.controller

import com.wmms.testtask3async.controller.response.ResponseBuilder
import com.wmms.testtask3async.controller.response.transfer.ListVersionTransfer
import com.wmms.testtask3async.exceptions.InvalidRequestBodyException
import com.wmms.testtask3async.form.NewElement
import com.wmms.testtask3async.form.NewValue
import com.wmms.testtask3async.form.OldElement
import com.wmms.testtask3async.manager.EntityManagerUtil
import com.wmms.testtask3async.service.PersistentListService
import com.wmms.testtask3async.service.PersistentListServiceImpl
import javax.ws.rs.*

@Path("/list/{id}")
@Consumes("application/json")
@Produces("application/json")
class PersistentListUpdateController {
    @GET
    fun getList(@PathParam("id") id: Long) = ResponseBuilder.buildResponse {
        return@buildResponse createPersistentListService().getList(id)
    }

    @POST
    fun addElement(@PathParam("id") id: Long, newElement: NewElement) = ResponseBuilder.buildResponse {
        if (newElement == null) {
            throw InvalidRequestBodyException()
        }
        return@buildResponse ListVersionTransfer(
            createPersistentListService().add(id, newElement.newElement!!))
    }

    @DELETE
    fun removeElement(@PathParam("id") id: Long, oldElement: OldElement) = ResponseBuilder.buildResponse {
        if (oldElement == null) {
            throw InvalidRequestBodyException()
        }
        return@buildResponse ListVersionTransfer(
            createPersistentListService().remove(id, oldElement.oldElement!!))
    }

    @PUT
    fun setElement(@PathParam("id") id: Long, newValue: NewValue) = ResponseBuilder.buildResponse {
        if (newValue == null) {
            throw InvalidRequestBodyException()
        }
        return@buildResponse ListVersionTransfer(
            createPersistentListService().set(id, newValue.oldValue!!, newValue.newValue!!))
    }

    @GET
    @Path("/delete/{oldElement}")
    fun removeElementWithPathValue(@PathParam("id") id: Long,
                                   @PathParam("oldElement") oldElement: Int) =
        ResponseBuilder.buildResponse {
            return@buildResponse ListVersionTransfer(createPersistentListService().remove(id, oldElement))
        }

    fun createPersistentListService(): PersistentListService {
        val listService = PersistentListServiceImpl()
        listService.entityManager = EntityManagerUtil.getEntityManager()
        return listService
    }
}