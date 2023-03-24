package com.wmms.testtask3async.controller.sync

import com.wmms.testtask3async.controller.PersistentListUpdateController
import com.wmms.testtask3async.form.NewElement
import com.wmms.testtask3async.form.NewValue
import com.wmms.testtask3async.form.OldElement
import javax.ws.rs.*

@Path("/list/{id}")
@Consumes("application/json")
@Produces("application/json")
class PersistentListUpdateSyncController {
    @GET
    fun getList(@PathParam("id") id: Long) = PersistentListUpdateController.getList(id)

    @POST
    fun addElement(@PathParam("id") id: Long, newElement: NewElement) =
        PersistentListUpdateController.addElement(id, newElement)

    @DELETE
    fun removeElement(@PathParam("id") id: Long, oldElement: OldElement) =
        PersistentListUpdateController.removeElement(id, oldElement)

    @PUT
    fun setElement(@PathParam("id") id: Long, newValue: NewValue) =
        PersistentListUpdateController.setElement(id, newValue)

    @GET
    @Path("/delete/{oldElement}")
    fun removeElementWithPathValue(@PathParam("id") id: Long,
                                   @PathParam("oldElement") oldElement: Int) =
        PersistentListUpdateController.removeElementWithPathValue(id, oldElement)
}