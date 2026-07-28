

@Marketplace @Busqueda
Feature: Búsqueda de productos en MarketPlace

  Scenario: Buscar un producto especifico
      Given que el usuario abre el Marketplace sin iniciar sesión
      When ingresa el nombre de un producto "laptop" en la barra de busqueda
      Then debería mostrar los resultados de la búsqueda
