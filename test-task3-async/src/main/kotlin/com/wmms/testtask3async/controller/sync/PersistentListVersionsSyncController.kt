package com.wmms.testtask3async.controller.sync

import com.wmms.testtask3async.controller.PersistentListVersionsController
import com.wmms.testtask3async.controller.response.ResponseBuilder
import com.wmms.testtask3async.controller.response.transfer.ListVersionTransfer
import com.wmms.testtask3async.controller.response.transfer.VersionsTransfer
import com.wmms.testtask3async.util.EntityManagerUtil
import com.wmms.testtask3async.service.PersistentListService
import com.wmms.testtask3async.service.PersistentListServiceImpl
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/lists")
@Consumes("application/json")
@Produces("application/json")
class PersistentListVersionsSyncController {
    @GET
    fun getVersions() = PersistentListVersionsController.getVersions()

    @GET
    @Path("/add/empty")
    fun newVersion() = PersistentListVersionsController.newVersion()
}