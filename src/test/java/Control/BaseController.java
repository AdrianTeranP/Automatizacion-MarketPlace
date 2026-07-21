//Su dfuncion principal es evitar repetir codigo tecnico de Selenim en casda pagina y centraliza la logica comun de Selenimum que utilizaran todas las paginas:
//1. Obtiene el webdriver, 2.Inicializa los elemnetos de la pagina, 3.entrega funciones reutilizables

package Control; // en este paquete estan las clases relacionadas con el control tecnico del framework

import java.time.Duration; // es la libreria que permitira la dsureacion del triempo que mostrara la ejecucion

import org.openqa.selenium.WebDriver;// esta es la interfaz de Selenium para controlar el navegador
import org.openqa.selenium.WebElement; // Representa un elemento HTML dentro de la pagina
import org.openqa.selenium.support.PageFactory; //Es una utilidad de Selenium que inicializa elemntos con anotacion como: @Finby
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;// Permite que lo elementos definidos con @Finby tengan una espera implicita al momneto de buscarlos
import org.openqa.selenium.support.ui.ExpectedConditions;// Contiene condiciones predeterminadas para esperas explicitas
import org.openqa.selenium.support.ui.WebDriverWait;// Permite esperar de una manera inteligente hasta que ocurra una condicion.

import Constant.Constant;// Esta clase guada valores globales del framework

public class BaseController { // Esta sera la clase que se llamara del ObjectPage
    private final WebDriver driver;// Aca se declara el navegador utilizado por el BaseController

    public BaseController() { // este seria el constructor el cual se ejecuta automaticamnete cuando se crean objetos de una clase que hereda de BaseController
        this.driver = DriverContext.getDriver();// Aca se obtiene el navegador que ya fue inicializado
        if (this.driver == null) {
            System.out.println("WebDriver no está inicializado!");
        }
    }

    protected void initPage() { // este inicializa los elementos del page object
        if (this.driver != null) {// Ejecuta inicializacion solko si el driver existe
            PageFactory.initElements(new AjaxElementLocatorFactory
                            (this.driver, Constant.TIME_RESPONSE), //Su responsabilidad es localizar los elementos declarados con @Finby
                    this);
        }
    }

    public boolean visualizarElemento(WebElement elementoWeb, int tiempoEspera) {//Este metodo verifica si un elemento se vuelve visible dentro de cierto tiempo
        try {// Aca es donde se puede producir una esepcion
            WebDriverWait wait2 = new WebDriverWait(DriverContext.getDriver(), Duration.ofSeconds(tiempoEspera));
            wait2.until(ExpectedConditions.visibilityOf(elementoWeb));
            System.out.println("Es visible el elemento web " + elementoWeb.getText());
            return true;
        } catch (Exception e) { //Si ocurre un prblema , java
            System.out.println("No es visible el elemento web" + elementoWeb);
            return false;
        }
    }
}
