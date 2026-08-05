@Marketplace @TC-1
# Estas dos etiquetas, puestas ARRIBA de "Feature:", se heredan
# automáticamente por TODOS los escenarios de este archivo.
Feature: Navegacion en Marketplace

  Scenario: Abrir la página principal
    # Este escenario no declara ninguna etiqueta propia, así que solo
    # tiene las heredadas: @Marketplace @TC-1
    Given abro el navegador en la url "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
    Then la página principal debería mostrarse correctamente

  @TC-2
  # Esta etiqueta se SUMA a las heredadas del Feature — no las reemplaza.
  Scenario: Ingresar credenciales válidas
    Given abro el navegador en la url "https://www.facebook.com/marketplace/santiagocl/?locale=es_LA"
    And inicio sesión con mis credenciales
    Then la página principal debería mostrarse correctamente