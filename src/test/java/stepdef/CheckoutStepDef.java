package stepdef;

import base.BaseTest;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutInfoPage;
import pages.CheckoutOverviewPage;
import pages.HomePage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckoutStepDef extends BaseTest {

    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;

    // Background
    @Given("User is on checkout information page")
    public void userIsOnCheckoutInformationPage() {
        HomePage homePage = new HomePage(driver);
        homePage.clickShoppingCartIcon();
        new CartPage(driver).clickCheckout();
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("checkout-step-one.html"));
        checkoutInfoPage = new CheckoutInfoPage(driver);
    }

    // @positive & @negative (isi form)
    @When("User inputs first name {string}, last name {string}, and postal code {string}")
    public void userInputsFirstNameLastNameAndPostalCode(String firstName, String lastName, String postalCode) {
        checkoutInfoPage.enterFirstName(firstName);
        checkoutInfoPage.enterLastName(lastName);
        checkoutInfoPage.enterPostalCode(postalCode);
    }

    @And("User clicks continue button")
    public void userClicksContinueButton() {
        checkoutInfoPage.clickContinue();
    }

    // @positive lanjut ke overview & complete
    @Then("User should be redirected to checkout overview page")
    public void userShouldBeRedirectedToCheckoutOverviewPage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("checkout-step-two.html"));
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        assertTrue(driver.getCurrentUrl().contains("checkout-step-two.html"));
    }

    @When("User clicks finish button")
    public void userClicksFinishButton() {
        checkoutOverviewPage.clickFinish();
    }

    @Then("User should be redirected to checkout complete page")
    public void userShouldBeRedirectedToCheckoutCompletePage() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlContains("checkout-complete.html"));
        checkoutCompletePage = new CheckoutCompletePage(driver);
        assertTrue(driver.getCurrentUrl().contains("checkout-complete.html"));
    }

    @Then("User sees order confirmation message {string}")
    public void userSeesOrderConfirmationMessage(String expectedMessage) {
        assertEquals(expectedMessage, checkoutCompletePage.getCompleteHeaderText());
    }

    // @negative (error validasi form)
    @Then("User sees checkout error message {string}")
    public void userSeesCheckoutErrorMessage(String expectedError) {
        assertEquals(expectedError, checkoutInfoPage.getErrorMessage());
    }

    // @cancel
    @When("User clicks cancel button on checkout information page")
    public void userClicksCancelButtonOnCheckoutInformationPage() {
        checkoutInfoPage.clickCancel();
    }

}