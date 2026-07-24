# Lenguage: es

  @marketplace @Busqueda
  Feature: Búsqueda de productos en MarketPlace
    Scenario: Buscar un producto especifico
      Given usuario abro el Marketplace sin iniciar sesión
      Then debería mostrar los resultados de la búsqueda
