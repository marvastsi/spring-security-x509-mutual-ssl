package com.marvastsi.spring.security.x509.application.config.security.filters

import com.marvastsi.spring.security.x509.domain.commons.utils.JwtUtil
import com.marvastsi.spring.security.x509.domain.services.UserService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val userService: UserService,
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = extractToken(request.getHeader(HttpHeaders.AUTHORIZATION))
        if (token != null && token.isNotEmpty()) {
            val claims = jwtUtil.decode(token)
            if (claims !== null && Date().before(claims.expiration)) {
                val user = userService.findByLogin(claims.subject)
                if (user != null) {
                    val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                    auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = auth
                }
            }
        }
        filterChain.doFilter(request, response)
    }

    private fun extractToken(header: String?) = header?.replace(TOKEN_PREFIX, "")

    companion object {
        private const val TOKEN_PREFIX = "Bearer "
    }
}