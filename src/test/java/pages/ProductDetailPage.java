package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductDetailPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By productName = By.cssSelector(".inventory_details_name");
    private final By productPrice = By.cssSelector(".inventory_details_price");
    private final By productImage = By.cssSelector(".inventory_details_img");
    private final By backToProductsBtn = By.id("back-to-products");
    private final By addToCartBtn = By.cssSelector("button[id^='add-to-cart']");
    private final By removeBtn = By.cssSelector("button[id^='remove']");

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getProductName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productName)).getText();
    }

    public String getProductPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice)).getText();
    }

    public String getProductImageAlt() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(productImage)).getAttribute("alt");
    }

    public void clickAddToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    public void clickRemove() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public void clickBackToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsBtn)).click();
    }


    public String getActionButtonText() {
        List<WebElement> addBtnElements = driver.findElements(addToCartBtn);
        if (!addBtnElements.isEmpty() && addBtnElements.get(0).isDisplayed()) {
            return addBtnElements.get(0).getText();
        }
        return wait.until(ExpectedConditions.visibilityOfElementLocated(removeBtn)).getText();
    }
}