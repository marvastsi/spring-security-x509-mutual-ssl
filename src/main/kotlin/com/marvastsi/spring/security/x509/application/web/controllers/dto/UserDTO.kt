package com.marvastsi.spring.security.x509.application.web.controllers.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class UserDTO(val username: String)
