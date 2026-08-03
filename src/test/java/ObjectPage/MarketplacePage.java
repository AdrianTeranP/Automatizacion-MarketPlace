package ObjectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;                              // Constantes de teclas especiales (Enter, Tab, etc.)
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MarketplacePage extends BasePage {

    private final By campoBusqueda =
            By.cssSelector("input[aria-label='Buscar en Marketplace']");

    public MarketplacePage(WebDriver driver) {
        super(driver);   // Inicializa driver/wait heredados de BasePage
    }

    public void abrirPagina(String url) {
        driver.get(url);   // driver.get(...) navega el navegador a esa URL directamente
    }

    public boolean validarPaginaPrincipal() {
        return urlContiene("facebook.com/marketplace");   // Heredado de BasePage
    }

    public void buscarProducto(String producto){
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoBusqueda));
        campo.clear();
        campo.sendKeys(producto);
        campo.sendKeys(Keys.ENTER);   // Simula presionar la tecla Enter físicamente
    }
}