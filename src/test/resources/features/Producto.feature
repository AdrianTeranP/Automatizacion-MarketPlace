

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

  @TC-5
  Scenario: Ordenar resultados por precio de menor a mayor
    When ingresa el nombre de un producto "laptop" en la barra de busqueda
    And ordena los resultados por "Precio: más bajo"
    Then los productos deberían mostrarse en orden ascendente de precio

  @TC-6
  Scenario: Ordenar resultados por precio de mayor a menor
    When ingresa el nombre de un producto "laptop" en la barra de busqueda
    And ordena los resultados por "Precio: más alto"
    Then los productos deberían mostrarse en orden descendente de precio

  @TC-7
  Scenario Outline: Aplicar otros criterios de orden disponibles
    When ingresa el nombre de un producto "laptop" en la barra de busqueda
    And ordena los resultados por "<filtro>"
    Then debería mostrar los resultados de la búsqueda

    Examples:
      | filtro                               |
      | Distancia: más cerca                 |
      | Fecha de publicación: más recientes  |

