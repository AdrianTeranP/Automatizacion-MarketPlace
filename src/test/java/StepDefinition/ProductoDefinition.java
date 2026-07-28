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

    @Given("que el usuario abre el Marketplace sin iniciar sesión")
    public void usuarioabreMarketPlaceSinLogin(){  // aca se rercupera el navegadorf quew se creo en el hooks

        DriverContext.setUp(
                Navegador.Chrome,
                "https://www.facebook.com/marketplace/"
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
        boolean hayResultados = resultadosBusquedaPage.validarUrlDeBusqueda();
        if (!hayResultados){
            throw new AssertionError("No se mostraron resultados de la busqueda");

        }
    }
}
