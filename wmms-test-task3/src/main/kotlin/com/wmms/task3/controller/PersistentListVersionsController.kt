package com.wmms.task3.controller

import com.wmms.task3.controller.transfer.VersionsTransfer
import com.wmms.task3.service.PersistentListService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/lists")
class PersistentListVersionsController(persistentListService: PersistentListService): PersistentListController(persistentListService) {
    @GetMapping
    @ResponseBody
    fun getVersions() = VersionsTransfer(persistentListService.getVersions())

    @GetMapping("/add/empty")
    @ResponseBody
    fun newVersion() = persistentListService.createEmptyList()
}