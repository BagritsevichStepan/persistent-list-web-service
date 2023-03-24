package com.wmms.testtask3async.controller

import com.wmms.testtask3async.controller.response.ResponseBuilder
import com.wmms.testtask3async.controller.response.transfer.ListVersionTransfer
import com.wmms.testtask3async.controller.response.transfer.VersionsTransfer
import com.wmms.testtask3async.service.PersistentListService
import com.wmms.testtask3async.service.PersistentListServiceImpl
import com.wmms.testtask3async.util.EntityManagerUtil

open class PersistentListVersionsController {
    companion object {
        fun getVersions() = ResponseBuilder.buildResponse {
            return@buildResponse VersionsTransfer(createPersistentListService().getVersions())
        }

        fun newVersion() = ResponseBuilder.buildResponse {
            return@buildResponse ListVersionTransfer(createPersistentListService().createEmptyList())
        }

        fun createPersistentListService(): PersistentListService {
            val listService = PersistentListServiceImpl()
            listService.entityManager = EntityManagerUtil.getEntityManager()
            return listService
        }
    }
}