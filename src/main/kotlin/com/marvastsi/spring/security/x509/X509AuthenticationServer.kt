package com.marvastsi.spring.security.x509

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class X509AuthenticationServer

fun main(args: Array<String>) {
	runApplication<X509AuthenticationServer>(*args)
}
