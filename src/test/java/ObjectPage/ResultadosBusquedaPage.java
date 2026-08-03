package ObjectPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultadosBusquedaPage extends BasePage {

    private final By tarjetaProducto =
            By.cssSelector("a[href*='/marketplace/item/']");

    public ResultadosBusquedaPage(WebDriver driver) {
        super(driver);
    }

    public boolean validarUrlDeBusqueda() {
        return urlContiene("/search");
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

    private static final Pattern PATRON_PRECIO = Pattern.compile("\\$([\\d.]+)");

    private double extraerPrecio(String textoAriaLabel){
        Matcher matcher = PATRON_PRECIO.matcher(textoAriaLabel);
        if (!matcher.find()){
            throw new NumberFormatException("No se encontró ningún precio en: " + textoAriaLabel);
        }
        String soloNumeros = matcher.group(1).replaceAll("[^0-9]", "");
        return Double.parseDouble(soloNumeros);
    }
    private final By botonOrdenarPor = By.xpath
            ("//div[@role='button' and .//span[text()='Ordenar por']]");

    public void seleccionarOrden(String textoOpcion){

        cerrarModalSiAparece();

        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonOrdenarPor));
        clicSeguro(boton);
        wait.until(ExpectedConditions.attributeToBe(boton, "aria-expanded", "true"));

        By opcion = By.xpath("//span[text()='" + textoOpcion + "']");
        WebElement elementoOpcion = wait.until(ExpectedConditions.elementToBeClickable(opcion));
        clicSeguro(elementoOpcion);

        // El panel no siempre colapsa (aria-expanded puede seguir en "true");
        // la señal confiable de que la selección se aplicó es que el propio
        // texto del botón cambia para mostrar la opción elegida.
        wait.until(ExpectedConditions.textToBePresentInElement(boton, textoOpcion));
    }
    public boolean validarOrdenPorPrecio(boolean ascendente){

        wait.until(ExpectedConditions.presenceOfElementLocated(tarjetaProducto));
        List<WebElement> tarjetas = driver.findElements(tarjetaProducto);

        if (tarjetas.size() < 2){
            throw new RuntimeException("Se necesitan al menos 2 productos para validar el orden");
        }

        Double precioAnterior = null;
        boolean ordenCorrecto = true;

        for (WebElement tarjeta : tarjetas){
            try {
                String textoTarjeta = tarjeta.getDomAttribute("aria-label");
                if (textoTarjeta == null) continue;

                double precioActual = extraerPrecio(textoTarjeta);
                System.out.println("Precio: " + precioActual + " | aria-label: [" + textoTarjeta + "]");

                if (precioActual <= 0){
                    System.out.println("Precio no comparable (posible trueque/gratis), se omite: " + precioActual);
                    continue;
                }

                if (precioAnterior != null){
                    boolean seRompioElOrden = ascendente
                            ? precioActual < precioAnterior
                            : precioActual > precioAnterior;
                    if (seRompioElOrden){
                        System.out.println(">>> ORDEN ROTO AQUÍ <<<");
                        ordenCorrecto = false;
                    }
                }
                precioAnterior = precioActual;

            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                System.out.println("No se pudo parsear precio de: [" + tarjeta.getDomAttribute("aria-label") + "]");
            }
        }
        return ordenCorrecto;
    }


}