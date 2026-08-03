package StepDefinition;

import Control.DriverContext;
import ObjectPage.LoginPage;
import Utils.ConfigReader;         // Clase propia del proyecto: lee configuración externa
import io.cucumber.java.en.And;    // Anotación para pasos "And" (nueva respecto a lo que ya viste)

public class LoginDefinition {

    @And("inicio sesión con mis credenciales")
    public void iniciarSesionConMisCredenciales() {

        // OJO: acá se crea el LoginPage directamente dentro del método, en
        // vez de guardarlo como variable de instancia (compáralo con cómo
        // ProductoDefinition guarda marketplacePage/resultadosBusquedaPage
        // como campos de la clase).
        LoginPage loginPage = new LoginPage(DriverContext.getDriver());

        loginPage.iniciarSesion(
                ConfigReader.get("usuario"),      // Lee el usuario desde configuración externa
                ConfigReader.get("password")      // Lee la contraseña desde configuración externa
        );
    }
}