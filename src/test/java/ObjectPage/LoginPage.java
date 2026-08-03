package ObjectPage;

import java.time.Duration;                                    // Importado pero ya no se usa
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;           // Importado pero ya no se usa

public class LoginPage extends BasePage {

    // Localizadores
    private final By campoUsuario = By.xpath(
            "(//input[@name='email'" + " and not(ancestor::*[@role='dialog'])])[1]"
    );

    private final By campoPassword = By.xpath(
            "(//input[@name='pass'" + " and not(ancestor::*[@role='dialog'])])[1]"
    );

    private final By botonIniciarSesion = By.xpath(
            "//input[@name='pass' and not(ancestor::*[@role='dialog'])]" +
                    "/ancestor::form//*[@aria-label='Iniciar sesión' and @role='button']"
    );

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void ingresarUsuario(String usuario) {
        WebElement campo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(campoUsuario)
        );
        campo.clear();
        campo.sendKeys(usuario);
    }

    public void ingresarPassword(String password) {
        WebElement campoPass = wait.until(
                ExpectedConditions.visibilityOfElementLocated(campoPassword)
        );
        campoPass.clear();
        campoPass.sendKeys(password);
    }

    public void clicIniciarSesion() {
        WebElement boton = wait.until(
                ExpectedConditions.elementToBeClickable(botonIniciarSesion)
        );
        boton.click();
    }

    // Método orquestador: encadena los 4 pasos del login en el orden correcto
    public void iniciarSesion(String usuario, String password) {
        cerrarModalSiAparece();   // Heredado de BasePage
        ingresarUsuario(usuario);
        ingresarPassword(password);
        clicIniciarSesion();
    }
}