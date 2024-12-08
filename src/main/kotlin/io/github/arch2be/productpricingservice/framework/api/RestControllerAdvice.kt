package io.github.arch2be.productpricingservice.framework.api

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class RestControllerAdvice: ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        return ResponseEntity.badRequest().body(ex.message)
    }
}