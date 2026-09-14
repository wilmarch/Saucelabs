package stepdef;

import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginStepDef extends BaseTest {

    LoginPage loginPage;
     HomePage homePage;

    @Given("User is on login page")
    public void userIsOnLoginPage() {
        loginPage = new LoginPage(driver);
        loginPage.openLoginPage();
    }

    @When("User inputs username {string} and password {string}")
    public void userInputsUsernameAndPassword(String username, String password) {
        if (!username.isEmpty()) {
            loginPage.enterUsername(username);
        }
        if (!password.isEmpty()) {
            loginPage.enterPassword(password);
        }
    }

    @And("User clicks login button")
    public void userClicksLoginButton() {
        loginPage.clickLogin();
    }

    @Then("User should be redirected to inventory page")
    public void userShouldBeRedirectedToInventoryPage() {
        homePage = new HomePage(driver);
        assertTrue(homePage.getCurrentUrl().contains("/inventory.html"));
        assertEquals("Products", homePage.getPageTitle());
    }

    @Then("User sees error message {string}")
    public void userSeesErrorMessage(String expectedErrorMessage) {
        String actualErrorMessage = loginPage.getErrorMessage();
        assertEquals(expectedErrorMessage, actualErrorMessage);
    }
}