package br.com.manafood.manafoodproduct.bdd

import io.cucumber.junit.platform.engine.Constants
import org.junit.platform.suite.api.ConfigurationParameter
import org.junit.platform.suite.api.IncludeEngines
import org.junit.platform.suite.api.SelectClasspathResource
import org.junit.platform.suite.api.Suite

/**
 * Runner para executar testes BDD com Cucumber + JUnit 5
 *
 * Execução:
 * - mvn test
 * - mvn test -Dcucumber.filter.tags="@smoke"
 *
 * Configuração:
 * - Features: src/test/resources/features
 * - Step Definitions: br.com.manafood.manafoodproduct.bdd.steps
 * - Plugins: Pretty console, JSON report, HTML report
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "br.com.manafood.manafoodproduct.bdd")
@ConfigurationParameter(
    key = Constants.PLUGIN_PROPERTY_NAME,
    value = "pretty, json:target/cucumber-reports/cucumber.json, html:target/cucumber-reports/cucumber.html"
)
class CucumberBddRunner

