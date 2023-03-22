package com.wmms.task3.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class InvalidIdException(id: Long): ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid ID: $id.")