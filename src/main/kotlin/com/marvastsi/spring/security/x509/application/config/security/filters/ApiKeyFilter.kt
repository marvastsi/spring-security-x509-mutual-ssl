package com.marvastsi.spring.security.x509.application.config.security.filters

import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter
import java.security.cert.X509Certificate
import javax.servlet.http.HttpServletRequest

class ApiKeyFilter(
    private val principalRequestHeader: String = "x-api-key"
) : X509AuthenticationFilter() {

    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any {
        return request.getHeader(principalRequestHeader)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any {
        val certificates = request.getAttribute(X509_ATTR) as Array<X509Certificate>
        return if (certificates.isNotEmpty()) {
            certificates[0].subjectDN
        } else super.getPreAuthenticatedCredentials(request)
    }

    companion object {
        private const val X509_ATTR = "javax.servlet.request.X509Certificate"
    }
}