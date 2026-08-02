package ObjectPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResultadosBusquedaPage extends BasePage {

    private final By tarjetaProducto =
            By.cssSelector("a[href*='/marketplace/item/']");

    public ResultadosBusquedaPage(WebDriver driver) {
        super(driver);
    }

    public boolean validarUrlDeBusqueda() {
        return urlContiene("/search/");
    }

    public boolean validarDetalleProducto() {
        return urlContiene("/marketplace/item/");
    }

    public void seleccionarProducto() {

        cerrarModalSiAparece();
        scrollVertical(600);

        wait.until(ExpectedConditions.presenceOfElementLocated(tarjetaProducto));
        List<WebElement> tarjetas = driver.findElements(tarjetaProducto);

        WebElement primeraTarjeta = null;
        for (WebElement tarjeta : tarjetas) {
            try {
                if (tarjeta.isDisplayed()) {
                    primeraTarjeta = tarjeta;
                    break;
                }
            } catch (StaleElementReferenceException e) {
                continue;
            }
        }

        if (primeraTarjeta == null) {
            throw new RuntimeException("No se encontró ningún producto visible para seleccionar");
        }

        clicSeguro(primeraTarjeta);
    }

    // Reservados para el futuro @TC-5 (filtro por precio)
    private String extraerIdProducto(String href) {
        String sinPrefijo = href.split("/marketplace/item/")[1];
        return sinPrefijo.split("/")[0];
    }

    private double extraerPrecio(String textoAriaLabel) {
        String[] partes = textoAriaLabel.split(", ");
        String parteConPrecio = partes[1].trim();
        String soloNumeros = parteConPrecio.replaceAll("[^0-9]", "");
        return Double.parseDouble(soloNumeros);
    }
}