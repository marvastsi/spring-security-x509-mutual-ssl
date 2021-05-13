package com.marvastsi.spring.security.x509.application.config.security.exceptions

import java.time.LocalDateTime

data class ApiResponse(
    private val timestamp: LocalDateTime = LocalDateTime.now(),
    private val status: Int?,
    private val error: String?,
    private val message: String?
)