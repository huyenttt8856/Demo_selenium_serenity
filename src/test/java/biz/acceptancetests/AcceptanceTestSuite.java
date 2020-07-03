package biz.acceptancetests;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(plugin="cucumber.runtime.formatter.SerenityReporter",features = "src/test/resources/features", glue = "biz", tags = "@test")
public class AcceptanceTestSuite {
//    @Managed(driver="chrome")
//   WebDriver chromeDriver;

}
