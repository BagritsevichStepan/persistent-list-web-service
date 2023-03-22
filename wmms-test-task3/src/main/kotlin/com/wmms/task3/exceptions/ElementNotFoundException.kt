package com.wmms.task3.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class ElementNotFoundException(element: String): ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Element $element not found.")