package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    By pageTitle = By.className("title");
    By sortDropdown = By.cssSelector("[data-test='product-sort-container']");
    By firstItemTitle = By.cssSelector(".inventory_item:first-child .inventory_item_name");
    By burgerMenuButton = By.id("react-burger-menu-btn");
    By logoutSidebarLink = By.id("logout_sidebar_link");
    By cartBadge = By.cssSelector(".shopping_cart_badge");
    By aboutSidebarLink = By.id("about_sidebar_link");
    By shoppingCartIcon = By.cssSelector("#shopping_cart_container a");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void selectSortOption(String optionText) {
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown));
        new Select(driver.findElement(sortDropdown)).selectByVisibleText(optionText);
    }

    public String getFirstItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstItemTitle)).getText();
    }

    public void clickBurgerMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(burgerMenuButton)).click();
    }

    public void clickLogout() {
        WebElement logoutBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(logoutSidebarLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutBtn);
    }

    public String getAboutLinkHref() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(aboutSidebarLink))
                .getAttribute("href");
    }

    public String getCartBadgeCount() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartBadge)).getText();
    }

    public void clickAddToCartByProductName(String productName) {
        String formattedName = productName.toLowerCase().replace(" ", "-");
        String xpathExpression = String.format("//button[starts-with(@id, 'add-to-cart-%s')]", formattedName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression))).click();
    }

    public void clickRemoveByProductName(String productName) {
        String formattedName = productName.toLowerCase().replace(" ", "-");
        String xpathExpression = String.format("//button[starts-with(@id, 'remove-%s')]", formattedName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression))).click();
    }

    public boolean isCartBadgeInvisible() {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(cartBadge));
    }

    public void clickProductTitleByName(String productName) {
        String xpathExpression = String.format("//div[contains(@class, 'inventory_item_name') and text()='%s']", productName);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathExpression))).click();
    }

    public void clickShoppingCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(shoppingCartIcon)).click();
    }
}