package ObjectPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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

    //Constructor
    public LoginPage(WebDriver driver) {
        super(driver);
    }



    // Escribir el usuario
    public void ingresarUsuario(String usuario) {

        WebElement campo = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        campoUsuario
                )
        );

        campo.clear();
        campo.sendKeys(usuario);

    }

    // Escribir la contraseña
    public void ingresarPassword(String password) {

        WebElement campoPass = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        campoPassword
                )
        );

        campoPass.clear();
        campoPass.sendKeys(password);

    }

    //Precionar el boton real del formulario superior
    public void clicIniciarSesion() {
        WebElement boton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        botonIniciarSesion
                )
        );
        boton.click();

    }

    //Metodo orquestador
    public void iniciarSesion(
            String usuario,
            String password
    ) {
        cerrarModalSiAparece();
        ingresarUsuario(usuario);
        ingresarPassword(password);
        clicIniciarSesion();
    }

}