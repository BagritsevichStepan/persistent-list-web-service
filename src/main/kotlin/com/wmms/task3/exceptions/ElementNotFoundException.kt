package com.wmms.task3.exceptions

class ElementNotFoundException(element: String): Exception("Element $element not found.")