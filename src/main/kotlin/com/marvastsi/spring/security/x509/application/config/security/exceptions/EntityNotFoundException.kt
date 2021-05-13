package com.marvastsi.spring.security.x509.application.config.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class EntityNotFoundException(exception: String?) : NullPointerException(exception) {
    companion object {
        private const val serialVersionUID = 2L
    }
}