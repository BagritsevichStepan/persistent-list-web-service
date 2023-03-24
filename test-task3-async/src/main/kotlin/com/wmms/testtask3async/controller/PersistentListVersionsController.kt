package com.wmms.testtask3async.controller

import com.wmms.testtask3async.controller.response.ResponseBuilder
import com.wmms.testtask3async.controller.response.transfer.ListVersionTransfer
import com.wmms.testtask3async.controller.response.transfer.VersionsTransfer
import com.wmms.testtask3async.manager.EntityManagerUtil
import com.wmms.testtask3async.service.PersistentListService
import com.wmms.testtask3async.service.PersistentListServiceImpl
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/lists")
@Consumes("application/json")
@Produces("application/json")
class PersistentListVersionsController {
    @GET
    fun getVersions() = ResponseBuilder.buildResponse {
        return@buildResponse VersionsTransfer(createPersistentListService().getVersions())
    }

    @GET
    @Path("/add/empty")
    fun newVersion() = ResponseBuilder.buildResponse {
        return@buildResponse ListVersionTransfer(createPersistentListService().createEmptyList())
    }

    fun createPersistentListService(): PersistentListService {
        val listService = PersistentListServiceImpl()
        listService.entityManager = EntityManagerUtil.getEntityManager()
        return listService
    }
}