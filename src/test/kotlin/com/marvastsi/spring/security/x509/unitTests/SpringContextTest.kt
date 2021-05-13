package com.marvastsi.spring.security.x509.unitTests

import com.marvastsi.spring.security.x509.X509AuthenticationServer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [X509AuthenticationServer::class])
class SpringContextTest {
//    @Test
    fun whenSpringContextIsBootstrapped_thenNoExceptions() {
    }
}