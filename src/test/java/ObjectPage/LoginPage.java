package ObjectPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    //Los localizadores de los elementos de la página de inicio de sesión
    private By campoUsuario = By.name("email");
    private By campoPasword = By.name("pass");
    private By botonIniciarSesion = By.xpath("//input[@name='pass']/following::span[normalize-space()='Iniciar sesión'][1]");

    //el constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //Metodo de accion ndividual
    public void ingresarUsuario(String usuario) {
        WebElement input = driver.findElement(campoUsuario);
        input.clear();
        input.sendKeys(usuario);
    }
    public void ingresarPassword(String password){
        WebElement input = driver.findElement(campoPasword);
        input.clear();
        input.sendKeys(password);
    }
    public void clicIniciarSesion(){
        driver.findElement(botonIniciarSesion).click();
    }
    //El metodo orquestador
    public void iniciarSesion(String usuario, String password){
        ingresarUsuario(usuario);
        ingresarPassword(password);
        clicIniciarSesion();
    }


}

