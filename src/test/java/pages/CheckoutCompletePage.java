package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutCompletePage {
    WebDriver driver;
    WebDriverWait wait;

    By completeHeader = By.className("complete-header");
    By backToProductsBtn = By.id("back-to-products");
    By generatePdfBtn = By.id("generate-pdf-order");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCompleteHeaderText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(completeHeader)).getText();
    }

    public void clickBackToProducts() {
        wait.until(ExpectedConditions.elementToBeClickable(backToProductsBtn)).click();
    }

    public void clickGeneratePdf() {
        wait.until(ExpectedConditions.elementToBeClickable(generatePdfBtn)).click();
    }
}