package StepDefinition;

import Constant.Constant;
import Control.DriverContext;
import io.cucumber.java.After;        // Se ejecuta DESPUÉS de cada escenario completo
import io.cucumber.java.AfterStep;    // Se ejecuta DESPUÉS de cada step individual
import io.cucumber.java.Before;       // Se ejecuta ANTES de cada escenario completo
import io.cucumber.java.Scenario;     // Objeto que representa el escenario en curso
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    private Scenario scenario;   // Se guarda en @Before, se usa en @AfterStep — igual patrón que
    // marketplacePage/resultadosBusquedaPage en ProductoDefinition

    private static final String tomarCapturaPantalla;
    private static final long PASUA_ANTES_DE_CAPTURAR_MS = 2000;   // (typo: "PASUA" — ver abajo)

    // Bloque estático: se ejecuta UNA sola vez, cuando la clase Hooks se
    // carga por primera vez — antes incluso de que exista cualquier
    // instancia. Es la forma de inicializar un "static final" cuando el
    // valor no es una constante literal simple, sino que requiere lógica.
    static{
        tomarCapturaPantalla = System.getProperty("evidence", "fullEvidence");
    }

    @Before
    public void setUp(Scenario scenario){
        this.scenario = scenario;
        Constant.scenarioStep = scenario;
        Constant.build_name = "Nombre de Proyecto";
    }

    @After
    public void tearDown(){
        DriverContext.quitDriver();   // Cierra el navegador al terminar el escenario
    }

    // Es "para el propio Hooks", así que podría ser private — ver
    // "Conceptos clave" #4
    public void pausaParaEstabilizarVisual(){
        try{
            Thread.sleep(PASUA_ANTES_DE_CAPTURAR_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void generarEvidencia(String imageRefName){

        if (DriverContext.getDriver() == null) {
            System.out.println("Driver no inicializado.");
            return;
        }

        pausaParaEstabilizarVisual();

        byte[] screenShot =
                ((TakesScreenshot) DriverContext.getDriver())
                        .getScreenshotAs(OutputType.BYTES);

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
        } else if(Hooks.tomarCapturaPantalla.equalsIgnoreCase("fullEvidence")){
            generarEvidencia("[SUCCESS] Step ScreenShots");
        }
    }
}