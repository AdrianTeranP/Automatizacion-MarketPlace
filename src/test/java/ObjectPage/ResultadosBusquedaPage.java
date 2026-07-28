package ObjectPage;

import java.time.Duration;
import java.util.List;

import Control.DriverContext;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ResultadosBusquedaPage {
    private final WebDriver driver;
    private final  WebDriverWait wait ;

    public  ResultadosBusquedaPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
}
public boolean validarUrlDeBusqueda(){ //Qué hace: confirma que, después de buscar, realmente llegamos a una página de resultados.
     try{
        wait.until(ExpectedConditions.urlContains("/search/"));//wait es el objeto WebDriverWait que ya se creó en el constructor de ResultadosBusquedaPage (con un límite de 15 segundos, siguiendo el mismo patrón que en LoginPage y MarketplacePage) , .until(...) le dice a Selenium: "sigue revisando la condición que te voy a dar, repetidamente, hasta que se cumpla o se acabe el tiempo", ExpectedConditions.urlContains("/search/") es la condición específica: "¿la URL actual del navegador contiene el texto /search/?" — esto viene predefinido en Selenium, no lo escribimos nosotros desde cero
        return true;// si llegamos aquí sin excepción, SÍ funcionó
     }catch (org.openqa.selenium.TimeoutException e) {
         return false;// si se agotó el tiempo, NO funcionó
        }
    }
}