package Control;

import org.openqa.selenium.WebDriver;

import Constant.Navegador;

public class
DriverContext {
    private static final DriverManager driverManager = new DriverManager();

    public static void setUp(Navegador nav, String url){
        System.out.println("Driver context -> Navegador:"+nav);
        driverManager.resolverDriver(nav, url);
    }

    public static WebDriver getDriver(){
        return driverManager.getDriver();
    }

    public static void quitDriver(){
        if (driverManager.getDriver() != null) {
            driverManager.getDriver().quit();
        }
    }
}
