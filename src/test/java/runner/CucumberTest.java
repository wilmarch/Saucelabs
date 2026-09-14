package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources",
        glue = {"stepdef"},
        plugin = {
                "pretty",
                "html:build/reports/cucumber-reports.html",
                "json:build/reports/cucumber-reports.json"
        },
        monochrome = true
)
public class CucumberTest {
}