package ObjectPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MarketplacePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public MarketplacePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void abrirPagina(String url) {
        driver.get(url);
    }

    public boolean validarPaginaPrincipal() {
        try {
            wait.until(ExpectedConditions.urlContains("facebook.com/marketplace"));
            return true;
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}