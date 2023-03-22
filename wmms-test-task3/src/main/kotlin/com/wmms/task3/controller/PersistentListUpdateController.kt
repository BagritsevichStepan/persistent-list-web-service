package com.wmms.task3.controller

import com.wmms.task3.form.NewElement
import com.wmms.task3.form.NewValue
import com.wmms.task3.form.OldElement
import com.wmms.task3.controller.transfer.ListVersionTransfer
import com.wmms.task3.service.PersistentListService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@Controller
@RequestMapping("/list/{id}")
class PersistentListUpdateController(persistentListService: PersistentListService): PersistentListController(persistentListService) {
    @GetMapping
    @ResponseBody
    fun getList(@PathVariable id: Long) = persistentListService.getList(id)

    @PostMapping
    @ResponseBody
    fun addElement(@PathVariable id: Long, @RequestBody newElement: NewElement) =
        ListVersionTransfer(persistentListService.add(id, newElement.newElement))

    @DeleteMapping
    @ResponseBody
    fun removeElement(@PathVariable id: Long, @RequestBody oldElement: OldElement) =
        ListVersionTransfer(persistentListService.remove(id, oldElement.oldElement))

    @PutMapping
    @ResponseBody
    fun setElement(@PathVariable id: Long, @RequestBody newValue: NewValue) =
        ListVersionTransfer(persistentListService.set(id, newValue.oldValue, newValue.newValue))

    @GetMapping("/delete/{oldElement}")
    @ResponseBody
    fun removeElementWithPathValue(@PathVariable id: Long, @PathVariable oldElement: Int) =
        ListVersionTransfer(persistentListService.remove(id, oldElement))
}