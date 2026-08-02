

@Marketplace @TC-1
Feature: Navegacion en Marketplace
  Scenario: Abrir la página principal
    Given abro el navegador en la url "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
    Then la página principal debería mostrarse correctamente

  @TC-2
    Scenario: Ingresar credenciales válidas
    Given abro el navegador en la url "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
    And inicio sesión con mis credenciales
    Then la página principal debería mostrarse correctamente
