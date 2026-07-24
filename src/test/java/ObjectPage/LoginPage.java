package ObjectPage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    //Modal que aparece sobre MarketPlace
    private final By modalFacebook =
            By.cssSelector("div[role=dialog]");

    // Cerrar modal de Facebook
    private final By botonCerrarModal = By.xpath(
            "(//div[@role='dialog']" + "//*[@aria-label='Cerrar' or @aria-label='Close'])[1]"
    );

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
        if (driver == null) {
            throw new IllegalArgumentException(
                    "El Webdriver no puede ser null"
            );
        }
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(15)
        );
    }

    //Cierra el modal si aparece y si no aparece dentro de unos segundo el proceso sigue
    public void cerrarModalSiAparece() {
        WebDriverWait esperaModal = new WebDriverWait(
                driver, Duration.ofSeconds(6)
        );

        try {
            esperaModal.until(
                    ExpectedConditions.visibilityOfElementLocated(modalFacebook)
            );
            WebElement botonCerrar = esperaModal.until(
                    ExpectedConditions.elementToBeClickable(botonCerrarModal)
            );
            botonCerrar.click();
            esperaModal.until(
                    ExpectedConditions.invisibilityOfElementLocated(modalFacebook)
            );
            System.out.println("Modal cerrado correctamente");

        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Modal no apareció. Continuando con login...");
        }
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