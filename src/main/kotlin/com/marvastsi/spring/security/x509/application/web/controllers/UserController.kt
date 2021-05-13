package com.marvastsi.spring.security.x509.application.web.controllers

import com.marvastsi.spring.security.x509.application.config.security.exceptions.BadRequestException
import com.marvastsi.spring.security.x509.application.config.security.exceptions.UnauthorizedException
import com.marvastsi.spring.security.x509.application.web.controllers.dto.LoginDTO
import com.marvastsi.spring.security.x509.application.web.controllers.dto.UserDTO
import com.marvastsi.spring.security.x509.domain.model.User
import com.marvastsi.spring.security.x509.domain.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.security.Principal


@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/details")
    fun user(principal: Principal): ResponseEntity<UserDTO> {
        val currentUser = (principal as Authentication).principal as UserDetails

        return ResponseEntity.ok(UserDTO(currentUser.username))
    }

    @PostMapping("/login")
    fun login(@Validated @RequestBody login: LoginDTO, err: Errors): ResponseEntity<String> {
        if (!err.hasErrors()) {
            val token = userService.login(login.username, login.password)
            if (token.isNotEmpty()) {
                return ResponseEntity.ok(token)
            }
            throw UnauthorizedException("Invalid credentials")
        }
        throw BadRequestException(err)
    }
}