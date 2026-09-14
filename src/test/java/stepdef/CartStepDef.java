package stepdef;

import base.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CartPage;
import pages.HomePage;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CartStepDef extends BaseTest {

    private HomePage homePage;
    private CartPage cartPage;

    @When("User clicks shopping cart icon")
    public void userClicksShoppingCartIcon() {
        homePage = new HomePage(driver);
        homePage.clickShoppingCartIcon();
    }

    @Then("User should be redirected to cart page")
    public void userShouldBeRedirectedToCartPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("cart.html"));
        cartPage = new CartPage(driver);
        assertTrue(driver.getCurrentUrl().contains("cart.html"));
    }

    @And("The cart should display item name {string} and price {string}")
    public void theCartShouldDisplayItemNameAndPrice(String expectedName, String expectedPrice) {
        List<CartPage.CartItemInfo> items = cartPage.getCartItems();
        boolean found = items.stream()
                .anyMatch(item -> item.name.equals(expectedName) && item.price.equals(expectedPrice));
        assertTrue("Item dengan nama '" + expectedName + "' dan harga '" + expectedPrice + "' tidak ditemukan di cart", found);
    }

    @When("User clicks checkout button")
    public void userClicksCheckoutButton() {
        cartPage.clickCheckout();
    }

    @When("User clicks continue shopping button")
    public void userClicksContinueShoppingButton() {
        cartPage.clickContinueShopping();
    }

    @When("User removes {string} from cart page")
    public void userRemovesFromCartPage(String productName) {
        cartPage.clickRemoveByProductName(productName);
    }

    @Then("The cart should have no items")
    public void theCartShouldHaveNoItems() {
        assertTrue(cartPage.isCartEmpty());
    }
}