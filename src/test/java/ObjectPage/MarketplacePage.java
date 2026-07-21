//Esta clase representa la pagina que se utilizara
package ObjectPage;

import org.openqa.selenium.WebDriver; // importa la interfaz webDriver de Selenium y representa el navegador controlado por la automatizacion

public class MarketplacePage {  // Declarar de una clase

    private WebDriver driver;//Atributos

    public MarketplacePage(WebDriver driver) { //Crear un contructor donde se recibe el navegador
        this.driver = driver; }// Asignar parametros al atributo

    public void abrirPagina(String url) {
            driver.get(url);

        }
        public boolean validarPaginaPrincipal(){//Metodo que devuelve un boolean true pertenece a MarketPalce, false no corresponde

            String urlActual = driver.getCurrentUrl();// aca se indica que la variable  guarda el texto

            return urlActual.contains("https://www.facebook.com/marketplace/");// aca valida el contenido de la url

        }
    }
