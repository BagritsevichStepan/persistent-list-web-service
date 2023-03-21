package com.wmms.task3.exceptions

class InvalidIdException(id: Long): Exception("Invalid ID: $id.") {
}