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
   private WebDriver driver =DriverContext.getDriver(); // Obtiene el navegador activo y guardalo en la viariable driver
   private ResultadosBusquedaPage resultadosBusquedaPage;

    @When("el usuario abre MarketPlace sin iniciar sesion ")
    public void usuarioabreMarketPlaceSinLogin(){

   }
}
