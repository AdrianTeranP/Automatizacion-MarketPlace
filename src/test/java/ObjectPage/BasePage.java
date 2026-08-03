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

// "abstract" = esta clase nunca se instancia directamente (nadie escribe
// "new BasePage(driver)"). Solo existe para que otras clases hereden de
// ella con "extends". Si algún día ya no hay ninguna clase que herede de
// BasePage, Java ni siquiera te dejaría crear un objeto suelto de esta.
public abstract class BasePage {

    // "protected" = visible en esta clase Y en las que hereden (LoginPage,
    // MarketplacePage, ResultadosBusquedaPage), pero no desde afuera
    // (ProductoDefinition, por ejemplo, no puede tocar driver directamente).
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    // "private static final" = una única copia de este locator, compartida
    // por TODAS las instancias de todas las subclases (no se recrea cada
    // vez que abres una nueva pantalla), y nadie fuera de esta clase la
    // necesita — por eso private.
    private static final By MODAL_FACEBOOK =
            By.cssSelector("div[role='dialog']");

    private static final By BOTON_CERRAR_MODAL = By.xpath(
            "(//div[@role='dialog']//*[@aria-label='Cerrar' or @aria-label='Close'])[1]");

    // "protected" también en el constructor: solo las subclases pueden
    // llamarlo (vía super(driver)) — nadie de afuera construye un
    // BasePage a secas.
    protected BasePage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("El WebDriver no puede ser null");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // public: la usan directamente los step definitions (ej. TC-3 llama
    // resultadosBusquedaPage.cerrarModalSiAparece())
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

    // protected: solo lo usan las subclases DESDE ADENTRO de sus propios
    // métodos (ej. validarUrlDeBusqueda() en ResultadosBusquedaPage). Los
    // step definitions nunca llaman urlContiene() directamente.
    protected boolean urlContiene(String fragmento) {
        try {
            wait.until(ExpectedConditions.urlContains(fragmento));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void clicSeguro(WebElement elemento) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", elemento);
        try {
            elemento.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemento);
        }
    }

    protected void scrollVertical(int pixeles) {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, " + pixeles + ");");
    }
}