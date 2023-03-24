package com.wmms.testtask3async.controller.async

import com.wmms.testtask3async.controller.PersistentListVersionsController
import com.wmms.testtask3async.util.ConcurrencyUtil
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended

@Path("/async/lists")
@Consumes("application/json")
@Produces("application/json")
class PersistentListVersionsAsyncController {
    @GET
    fun getVersions(@Suspended asyncResponse: AsyncResponse) = submit {
        asyncResponse.resume(PersistentListVersionsController.getVersions())
    }

    @GET
    @Path("/add/empty")
    fun newVersion(@Suspended asyncResponse: AsyncResponse) = submit {
        asyncResponse.resume(PersistentListVersionsController.newVersion())
    }

    fun submit(task: Runnable) {
        ConcurrencyUtil.submit(task)
    }
}