package stepdef;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;

import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HomeStepDef extends BaseTest {

    private HomePage homePage;

    // @sorting
    @When("User selects filter option {string}")
    public void userSelectsFilterOption(String sortOption) {
        homePage = new HomePage(driver);
        homePage.selectSortOption(sortOption);
    }

    @Then("The first product item name should be {string}")
    public void theFirstProductItemNameShouldBe(String expectedName) {
        homePage = new HomePage(driver);
        String actualName = homePage.getFirstItemName();
        assertEquals(expectedName, actualName);
    }

    // @logout & @about
    @When("User clicks burger menu button")
    public void userClicksBurgerMenuButton() {
        homePage = new HomePage(driver);
        homePage.clickBurgerMenu();
    }

    @When("User clicks logout sidebar link")
    public void userClicksLogoutSidebarLink() {
        homePage = new HomePage(driver);
        homePage.clickLogout();
    }

    @Then("User should be redirected to login page")
    public void userShouldBeRedirectedToLoginPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlToBe("https://www.saucedemo.com/"));
        assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Then("The About link should point to {string}")
    public void theAboutLinkShouldPointTo(String expectedUrl) {
        homePage = new HomePage(driver);
        assertEquals(expectedUrl, homePage.getAboutLinkHref());
    }

    // @cart-add
    @When("User adds the following products to cart")
    public void userAddsTheFollowingProductsToCart(List<String> productList) {
        homePage = new HomePage(driver);
        homePage.addMultipleProductsToCart(productList);
    }

    @Then("The shopping cart badge should display {string}")
    public void theShoppingCartBadgeShouldDisplay(String expectedCount) {
        homePage = new HomePage(driver);
        String actualCount = homePage.getCartBadgeCount();
        assertEquals(expectedCount, actualCount);
    }

    // @cart-remove
    @Given("User has already added the following products to the cart")
    public void userHasAlreadyAddedTheFollowingProductsToTheCart(List<String> productList) {
        homePage = new HomePage(driver);
        homePage.addMultipleProductsToCart(productList);
    }

    @When("User removes the following products from cart")
    public void userRemovesTheFollowingProductsFromCart(List<String> productList) {
        homePage = new HomePage(driver);
        homePage.removeMultipleProductsFromCart(productList);
    }

    @Then("The shopping cart badge should disappear")
    public void theShoppingCartBadgeShouldDisappear() {
        homePage = new HomePage(driver);
        assertTrue(homePage.isCartBadgeInvisible());
    }

    @Given("User has {string} in the cart")
    public void userHasInTheCart(String productName) {
        homePage = new HomePage(driver);
        homePage.clickAddToCartByProductName(productName);
    }
}