package stepdef;

import base.BaseTest;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.HomePage;
import pages.ProductDetailPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductDetailStepDef extends BaseTest {

     HomePage homePage;
     ProductDetailPage productDetailPage;

    @When("User clicks on product title {string}")
    public void userClicksOnProductTitle(String productName) {
        homePage = new HomePage(driver);
        homePage.clickProductTitleByName(productName);
    }

    @Then("User should be redirected to product detail page")
    public void userShouldBeRedirectedToProductDetailPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("inventory-item.html"));
        productDetailPage = new ProductDetailPage(driver);
        assertTrue(driver.getCurrentUrl().contains("inventory-item.html"));
    }

    @Then("The product details should display name {string} and price {string}")
    public void theProductDetailsShouldDisplayNameAndPrice(String expectedName, String expectedPrice) {
        assertEquals(expectedName, productDetailPage.getProductName());
        assertEquals(expectedPrice, productDetailPage.getProductPrice());
        assertEquals("Gambar produk tidak sesuai dengan nama produk yang ditampilkan",
                expectedName, productDetailPage.getProductImageAlt());
    }

    @Given("User is on product detail page for {string}")
    public void userIsOnProductDetailPageFor(String productName) {
        homePage = new HomePage(driver);
        homePage.clickProductTitleByName(productName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("inventory-item.html"));
        productDetailPage = new ProductDetailPage(driver);
    }

    @When("User clicks add to cart button")
    public void userClicksAddToCartButton() {
        productDetailPage = new ProductDetailPage(driver);
        productDetailPage.clickAddToCart();
    }

    @Given("User has already added the product to cart from detail page")
    public void userHasAlreadyAddedTheProductToCartFromDetailPage() {
        productDetailPage = new ProductDetailPage(driver);
        productDetailPage.clickAddToCart();
    }

    @When("User clicks remove button")
    public void userClicksRemoveButton() {
        productDetailPage = new ProductDetailPage(driver);
        productDetailPage.clickRemove();
    }

    @Then("The button should change to {string}")
    public void theButtonShouldChangeTo(String expectedButtonText) {
        assertEquals(expectedButtonText, productDetailPage.getActionButtonText());
    }

    @When("User clicks back to products button")
    public void userClicksBackToProductsButton() {
        productDetailPage = new ProductDetailPage(driver);
        productDetailPage.clickBackToProducts();
    }
}