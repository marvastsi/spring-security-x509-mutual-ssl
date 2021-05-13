package com.marvastsi.spring.security.x509.application.config.security

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class UnauthorizedHandler : AuthenticationEntryPoint, Serializable {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized, you don't have permission to access.");
    }
    companion object {
        private const val serialVersionUID = -525876930972866247L
    }

}