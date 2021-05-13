package com.marvastsi.spring.security.x509.domain.commons.utils

import java.util.Calendar
import java.util.Date
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm

class JwtUtil(
    private val secret: String
) {
    fun encode(subject: String): String {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DATE, +1)
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(date)
            .setExpiration(calendar.time)
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun decode(token: String): Claims? {
        return try {
            Jwts.parser()
                .setSigningKey(secret.toByteArray())
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }
}