package com.marvastsi.spring.security.x509.application.web.controllers

import com.marvastsi.spring.security.x509.application.web.controllers.dto.UserDTO
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/need-ssl/user")
class NeedSslController {
    @PreAuthorize("hasAuthority('ROLE_SYSTEM')")
    @GetMapping("/details")
    fun user(principal: Principal): ResponseEntity<UserDTO> {
        val currentUser = (principal as Authentication).principal as UserDetails
        return ResponseEntity.ok(UserDTO(currentUser.username))
    }
}