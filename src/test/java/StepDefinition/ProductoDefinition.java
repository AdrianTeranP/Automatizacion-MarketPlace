package StepDefinition;

import Constant.Navegador;                    // Enum con los navegadores soportados (Chrome, Firefox, etc.)
import Control.DriverContext;                  // Clase que crea y entrega el WebDriver activo de la sesión
import ObjectPage.MarketplacePage;             // Page Object de la home de Marketplace
import ObjectPage.ResultadosBusquedaPage;      // Page Object de la pantalla de resultados de búsqueda
import io.cucumber.java.en.Given;              // Anotación para pasos "Given" en español/inglés (el paquete es .en igual)
import io.cucumber.java.en.Then;               // Anotación para pasos "Then"
import io.cucumber.java.en.When;               // Anotación para pasos "When"
import org.openqa.selenium.WebDriver;          // Interfaz que representa el navegador controlado por Selenium
import org.junit.Assert;                       // Importado pero nunca usado — lo vemos más abajo

public class ProductoDefinition {

    // Estas tres variables viven mientras dura UN escenario (ver explicación abajo)
    private WebDriver driver;                          // Referencia al navegador de este escenario
    private MarketplacePage marketplacePage;            // Page Object de la home
    private ResultadosBusquedaPage resultadosBusquedaPage; // Page Object de resultados (se crea más adelante)

    // ===================== TC-3: Buscar un producto =====================

    @Given("que el usuario abre el Marketplace sin iniciar sesión")
    public void usuarioabreMarketPlaceSinLogin(){

        // Le pide a DriverContext que abra un Chrome nuevo apuntando a esta URL.
        // DriverContext es quien realmente instancia el WebDriver por dentro.
        DriverContext.setUp(
                Navegador.Chrome,
                "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
        );

        driver = DriverContext.getDriver();       // Recupera el navegador recién creado
        marketplacePage = new MarketplacePage(driver); // Crea el Page Object de la home, pasándole el driver
    }

    @When("ingresa el nombre de un producto {string} en la barra de busqueda")
    public void ingresarNombreDeProducto(String producto) {
        // {string} en la anotación captura el texto entre comillas del .feature
        // y Cucumber lo pasa automáticamente como el parámetro "producto" aquí.

        marketplacePage.buscarProducto(producto);  // Escribe el término y presiona Enter

        // Después de buscar, cambiamos de "pantalla" (aunque la URL de Facebook
        // no recargue del todo) — por eso instanciamos un Page Object DISTINTO
        // para la nueva pantalla, en vez de seguir usando marketplacePage.
        resultadosBusquedaPage = new ResultadosBusquedaPage(driver);
    }

    @Then("debería mostrar los resultados de la búsqueda")
    public void validarResultadosBusqueda(){
        resultadosBusquedaPage.cerrarModalSiAparece(); // Solo para que el screenshot de evidencia salga limpio
        boolean hayResultados = resultadosBusquedaPage.validarUrlDeBusqueda();

        if (!hayResultados){
            throw new AssertionError("No se mostraron resultados de la busqueda");
        }
    }

    // ===================== TC-4: Seleccionar un producto =====================

    @When("selecciona un producto de los resultados")
    public void seleccionarProducto() {
        resultadosBusquedaPage.seleccionarProducto();
    }

    @Then("debería ver el detalle del producto")
    public void validarDetalleProducto(){
        boolean detalleVisible = resultadosBusquedaPage.validarDetalleProducto();

        if (!detalleVisible){
            throw new AssertionError("No se mostro el detalle del producto");
        }
    }

    // ===================== TC-5, TC-6, TC-7: Ordenar resultados =====================

    @When("ordena los resultados por {string}")
    public void ordenarResultadosPor(String opcionDeOrden) {
        // Este MISMO step sirve para los 5 filtros distintos (Precio: más bajo,
        // Precio: más alto, Distancia, Fecha...) — el texto exacto que Cucumber
        // capture en {string} depende de qué escribiste en cada .feature,
        // incluido lo que trae cada fila del Scenario Outline del TC-7.
        resultadosBusquedaPage.seleccionarOrden(opcionDeOrden);

        // Diagnóstico temporal que dejamos de la sesión pasada
        System.out.println("URL después de ordenar: " + DriverContext.getDriver().getCurrentUrl());
    }

    @Then("los productos deberían mostrarse en orden ascendente de precio")
    public void validarOrdenAscendente(){
        // true = ascendente. Es el único dato que cambia respecto al método de abajo.
        if (!resultadosBusquedaPage.validarOrdenPorPrecio(true)){
            throw new AssertionError("Los productos no están ordenados de menor a mayor precio");
        }
    }

    @Then("los productos deberían mostrarse en orden descendente de precio")
    public void validarOrdenDescendente(){
        // false = descendente. Mismo método de ResultadosBusquedaPage, dirección invertida.
        if (!resultadosBusquedaPage.validarOrdenPorPrecio(false)){
            throw new AssertionError("Los productos no están ordenados de mayor a menor precio");
        }
    }
}