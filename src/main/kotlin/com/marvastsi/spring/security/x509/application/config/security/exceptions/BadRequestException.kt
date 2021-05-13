package com.marvastsi.spring.security.x509.application.config.security.exceptions

import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.stream.Collectors

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class BadRequestException : RuntimeException {
    constructor(
        entityName: String,
        err: Errors
    ) : super("Errors on fields " + handleErrorFields(err.fieldErrors) + " on entity " + entityName)

    constructor(err: Errors) : super(err.allErrors.stream().map { it.defaultMessage }
        .collect(Collectors.joining(","))) {
    }

    companion object {
        private const val serialVersionUID = 1L
        private fun handleErrorFields(errors: List<FieldError>): String {
            var data = ""
            for (err in errors) {
                data += ("(Field=" + err.field + " on object " + err.objectName.substring(0, 1).toUpperCase()
                        + err.objectName.substring(1) + "), ")
            }
            return data.substring(0, data.length - 2)
        }
    }
}