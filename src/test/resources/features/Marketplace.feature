# Lenguage: es

@Marketplace @TC-001
Feature: Navegacion en Marketplace
  Scenario: Abrir la página principal
    Given abro el navegador en la url "https://www.facebook.com/marketplace/"
    Then la página principal debería mostrarse correctamente

  @Marketplace @TC-002
    Scenario: Ingresar credenciales válidas
    Given abro el navegador en la url "https://www.facebook.com/marketplace/"
    And inicio sesión con mis credenciales
    Then la página principal debería mostrarse correctamente
