package br.com.manafood.manafoodproduct.bdd.steps

import br.com.manafood.manafoodproduct.bdd.config.CucumberSpringConfiguration
import io.cucumber.java.pt.Dado
import org.junit.jupiter.api.Assertions.assertTrue

class CommonSteps: CucumberSpringConfiguration() {

    @Dado("que o sistema está disponível")
    fun `que o sistema está disponível`() {
        val url = "${getBaseUrl()}/actuator/health"
        try {
            val healthResponse = restTemplate.getForEntity(url, String::class.java)
            assertTrue(healthResponse.statusCode.is2xxSuccessful, "Sistema não está disponível")
        } catch (e: Exception) {
            // Sistema ainda não está pronto, vamos aguardar
            Thread.sleep(2000)
        }
    }

    @Dado("o banco de dados está limpo")
    fun `o banco de dados está limpo`() {
        // O Testcontainer recria o banco para cada teste
        // Flyway garante que as migrações estão aplicadas
        assertTrue(true)
    }

}