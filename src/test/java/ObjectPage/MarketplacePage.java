package ObjectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    private final By campoBusqueda = By.cssSelector("input[aria-label='Buscar en Marketplace']");;//Esto es un locator — le dice a Selenium dónde está el campo. Usamos aria-label porque el input no tiene id ni name, y aria-label es el atributo más estable que encontramos al inspeccionar.
    public void buscarProducto(String producto){
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(campoBusqueda));//Esta línea espera (hasta 15 segundos) a que el campo de búsqueda sea visible, y una vez que lo es, lo guarda en la variable campo — mismo patrón de espera explícita que usamos en todo LoginPage.
        campo.clear();//Limpia el campo (por si tenía texto previo)
        campo.sendKeys(producto); //escribe el término de búsqueda recibido como parámetro — igual que ingresarUsuario/ingresarPassword en el login.
        campo.sendKeys(Keys.ENTER);//Esta es la parte nueva: Keys.ENTER no es texto, es una tecla especial. Keys es una clase de Selenium con constantes para teclas que no puedes "escribir" como caracteres normales (Enter, Tab, flechas, Escape, etc.). Al enviarla con sendKeys, Selenium simula que el usuario presionó físicamente esa tecla — y como confirmaste que Enter dispara la búsqueda en Facebook, esto reemplaza la necesidad de hacer clic en algún botón de lupa.
    }
}