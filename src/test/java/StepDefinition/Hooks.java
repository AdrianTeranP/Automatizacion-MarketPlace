package StepDefinition;

import Constant.Constant;
import Control.DriverContext;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {
    private Scenario scenario;
    private static final String tomarCapturaPantalla;
    private static final long PASUA_ANTES_DE_CAPTURAR_MS = 2000;

    static{
        tomarCapturaPantalla = System.getProperty("evidence","fullEvidence");
    }

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
        Constant.scenarioStep = scenario;
        Constant.build_name = "Nombre de Proyecto";
    }

    @After
    public void tearDown(){
        DriverContext.quitDriver();
    }
    public void pausaParaEstabilizarVisual(){
        try{
            Thread.sleep(PASUA_ANTES_DE_CAPTURAR_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void generarEvidencia(String imageRefName){

        if (DriverContext.getDriver() == null) {

            System.out.println(
                    "Driver no inicializado."
            );

            return;
        }
        pausaParaEstabilizarVisual();

        byte[] screenShot =
                ((TakesScreenshot)
                        DriverContext.getDriver())
                        .getScreenshotAs(
                                OutputType.BYTES);
        this.scenario.attach(
                screenShot,
                "image/png",
                imageRefName
        );
    }

    @AfterStep
    public void capturaEvidencia(){

        if (DriverContext.getDriver() == null) {
            return;
        }
        if(this.scenario.isFailed()){
            generarEvidencia("[FAIL] Step ScreenShots");
        } else if(Hooks.tomarCapturaPantalla
                .equalsIgnoreCase("fullEvidence")){
            generarEvidencia("[SUCCESS] Step ScreenShots");
        }
    }
}
