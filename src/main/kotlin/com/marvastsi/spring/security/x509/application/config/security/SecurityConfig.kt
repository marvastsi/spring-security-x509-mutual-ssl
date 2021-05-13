package com.marvastsi.spring.security.x509.application.config.security

import com.marvastsi.spring.security.x509.application.config.security.filters.JwtAuthenticationFilter
import com.marvastsi.spring.security.x509.domain.commons.utils.JwtUtil
import com.marvastsi.spring.security.x509.domain.model.User
import com.marvastsi.spring.security.x509.domain.services.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.apache.logging.log4j.Marker
import org.apache.logging.log4j.MarkerManager
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    @Value("#{'\${auth.x509clients}'.split(',')}") private val x509clients: List<String>,
    @Value("\${auth.secret}") private val secret: String,
    private val unauthorizedHandler: UnauthorizedHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.antMatcher("/need-ssl/**")
            .authorizeRequests()
            .antMatchers("/user/login").permitAll().and()
            .x509()
            .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
            .userDetailsService(userDetailsService()).and()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .anyRequest().authenticated()

        http.cors().and().csrf().disable()
        http.headers().disable()
        http.httpBasic().disable()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(userService(), JwtUtil(secret))
    }

    @Bean
    fun userService(): UserService {
        return UserService(JwtUtil(secret))
    }

    @Bean
    override fun userDetailsService(): UserDetailsService? {
        return UserDetailsService { username ->
            if (x509clients.contains(username)) {
                User(
                    username,
                    "",
                    setOf("ROLE_SYSTEM")
                )
            } else {
                logger.error(MarkerManager.Log4jMarker("SECURITY"),"Access Denied.")
                throw BadCredentialsException("Access Denied.")
            }
        }
    }

    val logger: Logger = LogManager.getLogger(SecurityConfig::class.java.name)
}