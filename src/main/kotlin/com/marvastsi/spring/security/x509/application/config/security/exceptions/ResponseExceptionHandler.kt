package com.marvastsi.spring.security.x509.application.config.security.exceptions

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class ResponseExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException::class, EntityNotFoundException::class)
    fun handlerEntityNotFound(exception: EntityNotFoundException): ResponseEntity<ApiResponse> {
        val status = HttpStatus.NOT_FOUND
        val err = ApiResponse(
            status = status.value(),
            error = status.toString(),
            message = exception.message
        )
        return ResponseEntity<ApiResponse>(err, status)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handlerBadRequest(exception: BadRequestException): ResponseEntity<ApiResponse> {
        val status = HttpStatus.BAD_REQUEST
        val err = ApiResponse(
            status = status.value(),
            error = status.toString(),
            message = exception.message
        )
        return ResponseEntity<ApiResponse>(err, status)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class, ExpiredTokenException::class, BadCredentialsException::class)
    fun handlerUnauthorizedLogin(exception: UnauthorizedException): ResponseEntity<ApiResponse> {
        val status = HttpStatus.UNAUTHORIZED
        val err = ApiResponse(
            status = status.value(),
            error = status.toString(),
            message = exception.message
        )
        return ResponseEntity<ApiResponse>(err, status)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(status)
            .headers(headers)
            .body(ex.message)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        return super.handleExceptionInternal(
            ex,
            ex.message,
            headers,
            status,
            request
        )
    }

}