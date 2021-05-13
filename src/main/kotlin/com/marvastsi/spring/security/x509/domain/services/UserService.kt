package com.marvastsi.spring.security.x509.domain.services

import com.marvastsi.spring.security.x509.domain.commons.utils.JwtUtil
import com.marvastsi.spring.security.x509.application.config.security.exceptions.UnauthorizedException
import com.marvastsi.spring.security.x509.domain.model.User

class UserService(
    private val jwtUtil: JwtUtil
) {
    fun findByLogin(login: String) : User? {
        val default = User("Alice", "qwert123", setOf("ROLE_USER"))
        return if (login == "Alice") default
        else null
    }

    fun login(username: String, password: String): String {
        val user = findByLogin(username)
        if (user != null
            && user.isEnabled
            && user.isAccountNonExpired
            && user.isAccountNonLocked
            && user.isCredentialsNonExpired
            && password == "qwert123"
        ) {
            return jwtUtil.encode(user.username)
        }

        throw UnauthorizedException(username)
    }
}