//conecta el feature con java

package StepDefinition;

import Constant.Navegador;
import Control.DriverContext;
import ObjectPage.MarketplacePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class MarketplaceDefinition {

    private MarketplacePage marketplacePage;

    @Given("abro el navegador en la url {string}")
    public void abrirNavegadorEnLaUrl(String url) {

        DriverContext.setUp(
                Navegador.Chrome,
                url
        );

        marketplacePage = new MarketplacePage(
                DriverContext.getDriver()
        );
    }

    @Then("la página principal debería mostrarse correctamente")
    public void validarPaginaMarketplace() {

        boolean paginaVisible =
                marketplacePage.validarPaginaPrincipal();

        if (!paginaVisible) {

            throw new AssertionError(
                    "La página de Marketplace no se mostró correctamente"
            );
        }
    }
}