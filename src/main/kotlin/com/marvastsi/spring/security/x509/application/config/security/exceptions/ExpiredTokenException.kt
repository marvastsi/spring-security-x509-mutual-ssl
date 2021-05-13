package com.marvastsi.spring.security.x509.application.config.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class ExpiredTokenException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)

    companion object {
        private const val serialVersionUID = 4L
    }
}