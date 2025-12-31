package br.com.manafood.manafoodproduct.bdd.config

import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.beans.factory.annotation.Autowired

/**
 * Configuração do contexto Spring para Cucumber
 * Habilita integração entre Cucumber e Spring Boot
 */
@CucumberContextConfiguration
class CucumberSpringConfiguration : AbstractBddTest() {

    @LocalServerPort
    protected var port: Int = 0

    @Autowired
    protected lateinit var restTemplate: TestRestTemplate

    protected fun getBaseUrl(): String = "http://localhost:$port"
}

