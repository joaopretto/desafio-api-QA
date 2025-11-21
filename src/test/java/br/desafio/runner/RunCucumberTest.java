package br.desafio.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = "br.desafio",
        plugin = {"pretty", "html:target/cucumber-reports/report.html"},
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class RunCucumberTest {
}
