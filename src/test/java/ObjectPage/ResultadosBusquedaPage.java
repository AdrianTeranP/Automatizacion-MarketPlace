package ObjectPage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.regex.Matcher;   // Representa UNA búsqueda de un patrón sobre un texto específico
import java.util.regex.Pattern;   // Representa el patrón (la "receta") de la expresión regular en sí
import java.util.Map;

// extends BasePage: hereda driver, wait, cerrarModalSiAparece(),
// urlContiene(), clicSeguro() y scrollVertical() — nada de eso se
// vuelve a declarar aquí, viene "gratis" de la clase padre.
public class ResultadosBusquedaPage extends BasePage {

    // Locator de CUALQUIER tarjeta de producto en la grilla de resultados.
    // *= significa "contiene": cada producto tiene un ID distinto en su URL,
    // así que no podemos buscar el href completo, solo el fragmento común.
    private final By tarjetaProducto =
            By.cssSelector("a[href*='/marketplace/item/']");

    public ResultadosBusquedaPage(WebDriver driver) {
        super(driver);   // Delega la inicialización de driver/wait al constructor de BasePage
    }

    // ===================== Validaciones de pantalla =====================

    public boolean validarUrlDeBusqueda() {
        return urlContiene("/search");   // Heredado de BasePage — un wait.until + try/catch en una línea
    }

    public boolean validarDetalleProducto() {
        return urlContiene("/marketplace/item/");
    }

    // ===================== TC-4: seleccionar un producto =====================

    public void seleccionarProducto() {

        cerrarModalSiAparece();     // Heredado de BasePage
        scrollVertical(600);        // Heredado de BasePage — fuerza el lazy-load de la grilla

        wait.until(ExpectedConditions.presenceOfElementLocated(tarjetaProducto));
        List<WebElement> tarjetas = driver.findElements(tarjetaProducto);

        WebElement primeraTarjeta = null;

        for (WebElement tarjeta : tarjetas) {
            try {
                if (tarjeta.isDisplayed()) {   // ¿Este elemento específico es visible en pantalla?
                    primeraTarjeta = tarjeta;
                    break;                     // Encontramos la primera visible: dejamos de buscar
                }
            } catch (StaleElementReferenceException e) {
                continue;   // Esta tarjeta desapareció del DOM justo al consultarla: la saltamos
            }
        }

        if (primeraTarjeta == null) {
            throw new RuntimeException("No se encontró ningún producto visible para seleccionar");
        }

        clicSeguro(primeraTarjeta);   // Heredado de BasePage: scrollIntoView + click con fallback JS
    }

    // ===================== Auxiliares de parseo =====================

    // Convierte "/marketplace/item/905301355295533/?ref=search..." → "905301355295533"
    // NOTA: actualmente nadie llama este método (ver "Conceptos clave" #4)
    private String extraerIdProducto(String href) {
        String sinPrefijo = href.split("/marketplace/item/")[1];
        return sinPrefijo.split("/")[0];
    }

    // El patrón se compila UNA sola vez (static final) y se reutiliza en cada
    // llamada — compilar un regex es una operación cara, así que hacerlo cada
    // vez dentro del método sería un desperdicio de recursos.
    private static final Pattern PATRON_PRECIO = Pattern.compile("\\$([\\d.]+)");

    private double extraerPrecio(String textoAriaLabel){
        Matcher matcher = PATRON_PRECIO.matcher(textoAriaLabel);   // Prepara la búsqueda sobre ESTE texto

        if (!matcher.find()){    // Busca la primera coincidencia; devuelve true/false si la encontró
            throw new NumberFormatException("No se encontró ningún precio en: " + textoAriaLabel);
        }

        String soloNumeros = matcher.group(1).replaceAll("[^0-9]", "");
        return Double.parseDouble(soloNumeros);
    }

    // ===================== TC-5/6/7: ordenar resultados =====================

    //Este único método ahora sirve para las 5 opciones, con una sola forma de esperar

    private final By botonOrdenarPor = By.xpath
            ("//div[@role='button' and .//span[text()='Ordenar por']]");

    private static final Map<String, String> SORT_BY_ESPERADO = Map.of(
            "Sugerencias", "sortBy=best_match",
            "Precio: más bajo", "sortBy=price_ascend",
            "Precio: más alto", "sortBy=price_descend",
            "Distancia: más cerca", "sortBy=distance_ascend",
            "Fecha de publicación: más recientes", "sortBy=creation_time_descend"
    );

    public void seleccionarOrden(String textoOpcion){

        cerrarModalSiAparece();

        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(botonOrdenarPor));
        clicSeguro(boton);
        wait.until(ExpectedConditions.attributeToBe(boton, "aria-expanded", "true"));

        By opcion = By.xpath("//span[text()='" + textoOpcion + "']");
        WebElement elementoOpcion = wait.until(ExpectedConditions.elementToBeClickable(opcion));
        clicSeguro(elementoOpcion);

        String fragmentoEsperado = SORT_BY_ESPERADO.get(textoOpcion);
        if (fragmentoEsperado == null){
            throw new IllegalArgumentException("No se conoce el sortBy esperado para: " + textoOpcion);
        }
        wait.until(ExpectedConditions.urlContains(fragmentoEsperado));
    }

    // ===================== TC-5/6: validar el orden =====================

    public boolean validarOrdenPorPrecio(boolean ascendente){

        wait.until(ExpectedConditions.presenceOfElementLocated(tarjetaProducto));
        List<WebElement> tarjetas = driver.findElements(tarjetaProducto);

        if (tarjetas.size() < 2){
            throw new RuntimeException("Se necesitan al menos 2 productos para validar el orden");
        }

        Double precioAnterior = null;   // Double (mayúscula) — puede ser null. Ver "Conceptos clave" #2
        boolean ordenCorrecto = true;

        for (WebElement tarjeta : tarjetas){
            try {
                String textoTarjeta = tarjeta.getDomAttribute("aria-label");
                if (textoTarjeta == null) continue;

                double precioActual = extraerPrecio(textoTarjeta);
                System.out.println("Precio: " + precioActual + " | aria-label: [" + textoTarjeta + "]");

                if (precioActual <= 0){
                    // Trueques/permutas ($0) o precios "gancho" ($1): no son
                    // comparables como precio de venta real, se excluyen.
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