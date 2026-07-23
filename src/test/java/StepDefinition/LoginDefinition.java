package StepDefinition;

import Control.DriverContext;
import ObjectPage.LoginPage;
import Utils.ConfigReader;
import io.cucumber.java.en.And;

public class LoginDefinition {

    @And("inicio sesión con mis credenciales")
    public void iniciarSesionConMisCredenciales() {

        LoginPage loginPage = new LoginPage(DriverContext.getDriver());

        loginPage.iniciarSesion(
                ConfigReader.get("usuario"),
                ConfigReader.get("password")
        );

    }

}
