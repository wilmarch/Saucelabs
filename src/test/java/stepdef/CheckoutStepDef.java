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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CheckoutStepDef extends BaseTest {

     CheckoutInfoPage checkoutInfoPage;
     CheckoutOverviewPage checkoutOverviewPage;
     CheckoutCompletePage checkoutCompletePage;

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

    @When("User clicks generate pdf order button")
    public void userClicksGeneratePdfOrderButton() {
        checkoutCompletePage.clickGeneratePdf();
    }

    @Then("User should still be on checkout complete page")
    public void userShouldStillBeOnCheckoutCompletePage() {
        assertTrue("Halaman berpindah setelah klik generate PDF, seharusnya tetap di checkout-complete.html",
                driver.getCurrentUrl().contains("checkout-complete.html"));
    }

    @When("User clicks back to products button on checkout complete page")
    public void userClicksBackToProductsButtonOnCheckoutCompletePage() {
        checkoutCompletePage.clickBackToProducts();
    }

    @Then("User sees checkout error message {string}")
    public void userSeesCheckoutErrorMessage(String expectedError) {
        assertEquals(expectedError, checkoutInfoPage.getErrorMessage());
    }

    @When("User clicks cancel button on checkout information page")
    public void userClicksCancelButtonOnCheckoutInformationPage() {
        checkoutInfoPage.clickCancel();
    }

    @When("User clicks cancel button on checkout overview page")
    public void userClicksCancelButtonOnCheckoutOverviewPage() {
        checkoutOverviewPage.clickCancel();
    }

    @Then("The order summary should display item name {string} and price {string}")
    public void theOrderSummaryShouldDisplayItemNameAndPrice(String expectedName, String expectedPrice) {
        List<CheckoutOverviewPage.OrderItemInfo> items = checkoutOverviewPage.getOrderItems();
        boolean found = items.stream()
                .anyMatch(item -> item.name.equals(expectedName) && item.price.equals(expectedPrice));
        assertTrue("Item dengan nama '" + expectedName + "' dan harga '" + expectedPrice + "' tidak ditemukan di order summary",
                found);
    }

    @Then("The order total should equal subtotal plus tax")
    public void theOrderTotalShouldEqualSubtotalPlusTax() {
        double subtotal = checkoutOverviewPage.getSubtotalAmount();
        double tax = checkoutOverviewPage.getTaxAmount();
        double total = checkoutOverviewPage.getTotalAmount();
        assertEquals("Total (" + total + ") tidak sama dengan subtotal (" + subtotal + ") + tax (" + tax + ")",
                subtotal + tax, total, 0.001);
    }

    @Then("The order subtotal should equal the sum of item prices")
    public void theOrderSubtotalShouldEqualTheSumOfItemPrices() {
        List<CheckoutOverviewPage.OrderItemInfo> items = checkoutOverviewPage.getOrderItems();
        double sumOfItemPrices = items.stream()
                .mapToDouble(item -> Double.parseDouble(item.price.replace("$", "").trim()))
                .sum();
        double subtotal = checkoutOverviewPage.getSubtotalAmount();
        assertEquals("Subtotal (" + subtotal + ") tidak sama dengan jumlah harga item (" + sumOfItemPrices + ")",
                sumOfItemPrices, subtotal, 0.001);
    }

}