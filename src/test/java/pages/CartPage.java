package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItems = By.className("cart_item");
    private final By checkoutBtn = By.id("checkout");
    private final By continueShoppingBtn = By.id("continue-shopping");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static class CartItemInfo {
        public String name;
        public String price;
    }

    public List<CartItemInfo> getCartItems() {
        List<WebElement> items = driver.findElements(cartItems);
        List<CartItemInfo> result = new ArrayList<>();
        for (WebElement item : items) {
            CartItemInfo info = new CartItemInfo();
            info.name = item.findElement(By.className("inventory_item_name")).getText();
            info.price = item.findElement(By.className("inventory_item_price")).getText();
            result.add(info);
        }
        return result;
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn)).click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
    }

    public void clickRemoveByProductName(String productName) {
        String formattedName = productName.toLowerCase().replace(" ", "-");
        By removeBtn = By.id("remove-" + formattedName);
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public void removeMultipleProductsFromCart(List<String> productNames) {
        for (String productName : productNames) {
            clickRemoveByProductName(productName);
        }
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItems).isEmpty();
    }
}