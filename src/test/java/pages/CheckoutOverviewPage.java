package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheckoutOverviewPage {
    WebDriver driver;
    WebDriverWait wait;

    By finishBtn = By.id("finish");
    By cancelBtn = By.id("cancel");
    By subtotalLabel = By.className("summary_subtotal_label");
    By taxLabel = By.className("summary_tax_label");
    By totalLabel = By.className("summary_total_label");
    By orderItems = By.className("cart_item");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static class OrderItemInfo {
        public String name;
        public String price;
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(finishBtn)).click();
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelBtn)).click();
    }

    public String getSubtotalText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(subtotalLabel)).getText();
    }

    public String getTaxText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(taxLabel)).getText();
    }

    public String getTotalText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalLabel)).getText();
    }

    private double parseAmount(String labelText) {
        int dollarIndex = labelText.indexOf('$');
        String numberPart = labelText.substring(dollarIndex + 1).trim();
        return Double.parseDouble(numberPart);
    }

    public double getSubtotalAmount() {
        return parseAmount(getSubtotalText());
    }

    public double getTaxAmount() {
        return parseAmount(getTaxText());
    }

    public double getTotalAmount() {
        return parseAmount(getTotalText());
    }

    public List<OrderItemInfo> getOrderItems() {
        List<WebElement> items = driver.findElements(orderItems);
        List<OrderItemInfo> result = new ArrayList<>();
        for (WebElement item : items) {
            OrderItemInfo info = new OrderItemInfo();
            info.name = item.findElement(By.className("inventory_item_name")).getText();
            info.price = item.findElement(By.className("inventory_item_price")).getText();
            result.add(info);
        }
        return result;
    }
}