package ObjectPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BasePage {//significa que esta clase no se puede instanciar directamente. Nunca vas a escribir

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    private static final By MODAL_FACEBOOK =
            By.cssSelector("div[role='dialog']");

    private static final By BOTON_CERRAR_MODAL = By.xpath(
            "(//div[@role='dialog']//*[@aria-label='Cerrar' or @aria-label='Close'])[1]");

    protected BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("El WebDriver no puede ser null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // Cierra el modal de Facebook si aparece; si no aparece, continúa sin fallar
    public void cerrarModalSiAparece() {
        WebDriverWait esperaModal = new WebDriverWait(driver, Duration.ofSeconds(6));
        try {
            esperaModal.until(ExpectedConditions.visibilityOfElementLocated(MODAL_FACEBOOK));
            WebElement botonCerrar = esperaModal.until(
                    ExpectedConditions.elementToBeClickable(BOTON_CERRAR_MODAL));
            botonCerrar.click();
            esperaModal.until(ExpectedConditions.invisibilityOfElementLocated(MODAL_FACEBOOK));
            System.out.println("Modal cerrado");
        } catch (TimeoutException e) {
            System.out.println("Modal no apareció. Continuando...");
        }
    }

    // Espera a que la URL contenga el fragmento indicado
    protected boolean urlContiene(String fragmento) {
        try {
            wait.until(ExpectedConditions.urlContains(fragmento));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Centra el elemento y hace clic; si algo lo tapa, cae al clic por JavaScript
    protected void clicSeguro(WebElement elemento) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", elemento);
        try {
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
        }
    }

    // Desplaza la página verticalmente (fuerza el lazy-load de las grillas)
    protected void scrollVertical(int pixeles) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixeles + ");");
    }
}