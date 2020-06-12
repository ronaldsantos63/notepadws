package com.ronaldsantos.notepadws.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class NoteNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(NoteNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun noteNotFoundHandler(ex: NoteNotFoundException): String {
        return ex.message!!
    }
}