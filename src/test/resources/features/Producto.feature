

@Marketplace @Busqueda
Feature: Búsqueda de productos en MarketPlace

  Background:
    Given que el usuario abre el Marketplace sin iniciar sesión

  @TC-3
  Scenario: Buscar un producto especifico
      When ingresa el nombre de un producto "laptop" en la barra de busqueda
      Then debería mostrar los resultados de la búsqueda

  @TC-4
  Scenario: Seleccionar un producto de la búsqueda
    When ingresa el nombre de un producto "laptop" en la barra de busqueda
    And selecciona un producto de los resultados
    Then debería ver el detalle del producto

