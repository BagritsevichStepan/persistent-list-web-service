package com.wmms.testtask3async.controller.response

import com.wmms.testtask3async.exceptions.ElementNotFoundException
import com.wmms.testtask3async.exceptions.InvalidIdException
import com.wmms.testtask3async.exceptions.InvalidRequestBodyException
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import java.util.function.Supplier

class ResponseBuilder {
    companion object {
        private val EXCEPTION_STATUS_CODE = mapOf(
            InvalidRequestBodyException::class to Response.Status.BAD_REQUEST,
            InvalidIdException::class to Response.Status.NOT_FOUND,
            ElementNotFoundException::class to Response.Status.NOT_ACCEPTABLE,
        )

        public fun <T> buildResponse(response: Supplier<T>): Response {
            return try {
                Response.ok(MediaType.APPLICATION_JSON)
                    .entity(response.get())
                    .build()
            } catch (e: Exception) {
                buildNotOkResponse(e)
            }
        }

        private fun buildNotOkResponse(e: Exception): Response {
            return Response.status(getExceptionStatusCode(e).statusCode, e.message).build();
        }

        private fun getExceptionStatusCode(e: Exception): Response.Status {
            return EXCEPTION_STATUS_CODE.getOrDefault(e::class, Response.Status.INTERNAL_SERVER_ERROR)
        }
    }
}