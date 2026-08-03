package StepDefinition;

import Constant.Navegador;
import Control.DriverContext;
import ObjectPage.MarketplacePage;// Permite obtener el navegador que fue creado previamente
import ObjectPage.ResultadosBusquedaPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.junit.Assert; // Assert daria el resultado final

public class ProductoDefinition {
   private WebDriver driver; // solo declarado, NO inicializado aquí
   private MarketplacePage marketplacePage;
   private ResultadosBusquedaPage resultadosBusquedaPage;

// @TC-3
    @Given("que el usuario abre el Marketplace sin iniciar sesión")
    public void usuarioabreMarketPlaceSinLogin(){  // aca se rercupera el navegadorf quew se creo en el hooks

        DriverContext.setUp(
                Navegador.Chrome,
                "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
         );
        driver = DriverContext.getDriver();      // 2° recién ahora lo recuperamos
        marketplacePage = new MarketplacePage(driver);
    }
    // {string} captura el texto entre comillas del .feature y lo pasa como parámetro
    @When("ingresa el nombre de un producto {string} en la barra de busqueda")//{string} en la anotación captura el texto entre comillas del .feature (por ejemplo "laptop") y lo pasa como parámetro producto al método — mismo mecanismo que ya viste en abrirNavegadorEnLaUrl(String url)
    public void ingresarNombreDeProducto(String producto) {
        marketplacePage.buscarProducto(producto);//Llama a marketplacePage.buscarProducto(...) (el método que armamos en el Cambio 1)
        resultadosBusquedaPage = new ResultadosBusquedaPage(driver);//Después crea un ResultadosBusquedaPage nuevo — porque técnicamente, después de buscar, estamos en una pantalla distinta (la de resultados), así que instanciamos su Page Object correspondiente para poder usarlo en el siguiente step
    }
    @Then("debería mostrar los resultados de la búsqueda")
    public void validarResultadosBusqueda(){
        resultadosBusquedaPage.cerrarModalSiAparece();
        boolean hayResultados = resultadosBusquedaPage.validarUrlDeBusqueda();
        if (!hayResultados){
            throw new AssertionError("No se mostraron resultados de la busqueda");

        }

    }
    // @TC-04
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
    @When("ordena los resultados por {string}")
    public void ordenarResultadosPor(String opcionDeOrden) {
        resultadosBusquedaPage.seleccionarOrden(opcionDeOrden);
        System.out.println("URL después de ordenar: " + DriverContext.getDriver().getCurrentUrl());
    }

    @Then("los productos deberían mostrarse en orden ascendente de precio")
    public void validarOrdenAscendente(){
        if (!resultadosBusquedaPage.validarOrdenPorPrecio(true)){
            throw new AssertionError("Los productos no están ordenados de menor a mayor precio");
        }
    }

    @Then("los productos deberían mostrarse en orden descendente de precio")
    public void validarOrdenDescendente(){
        if (!resultadosBusquedaPage.validarOrdenPorPrecio(false)){
            throw new AssertionError("Los productos no están ordenados de mayor a menor precio");
        }
    }
}
