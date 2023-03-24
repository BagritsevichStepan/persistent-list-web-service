package com.wmms.testtask3async.controller.async

import com.wmms.testtask3async.controller.PersistentListUpdateController
import com.wmms.testtask3async.form.NewElement
import com.wmms.testtask3async.form.NewValue
import com.wmms.testtask3async.form.OldElement
import com.wmms.testtask3async.util.ConcurrencyUtil
import javax.ws.rs.*
import javax.ws.rs.container.AsyncResponse
import javax.ws.rs.container.Suspended

@Path("/async/list/{id}")
@Consumes("application/json")
@Produces("application/json")
class PersistentListUpdateAsyncController {
    @GET
    fun getList(@PathParam("id") id: Long, @Suspended asyncResponse: AsyncResponse) = submit {
            asyncResponse.resume(PersistentListUpdateController.getList(id))
    }

    @POST
    fun addElement(@PathParam("id") id: Long, newElement: NewElement,
                   @Suspended asyncResponse: AsyncResponse) = submit {
            asyncResponse.resume(PersistentListUpdateController.addElement(id, newElement))
    }

    @DELETE
    fun removeElement(@PathParam("id") id: Long, oldElement: OldElement,
                      @Suspended asyncResponse: AsyncResponse) = submit {
            asyncResponse.resume(PersistentListUpdateController.removeElement(id, oldElement))
    }

    @PUT
    fun setElement(@PathParam("id") id: Long, newValue: NewValue,
                   @Suspended asyncResponse: AsyncResponse) = submit {
            asyncResponse.resume(PersistentListUpdateController.setElement(id, newValue))
    }

    @GET
    @Path("/delete/{oldElement}")
    fun removeElementWithPathValue(@PathParam("id") id: Long,
                                   @PathParam("oldElement") oldElement: Int,
                                   @Suspended asyncResponse: AsyncResponse) = submit {
            asyncResponse.resume(
                PersistentListUpdateController.removeElementWithPathValue(id, oldElement))
    }

    fun submit(task: Runnable) {
        ConcurrencyUtil.submit(task)
    }
}