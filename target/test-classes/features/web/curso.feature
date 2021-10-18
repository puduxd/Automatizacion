Feature: Pruebas de curso automatización

  @prueba1
  Scenario: Se valida el ingreso a traves de login 
    Given ingreso a url "https://opensource-demo.orangehrmlive.com/index.php/leave/viewMyLeaveList/"
    When ingrese las credenciales usuario "Admin" y clave "admin123"
    Then validar el ingreso a pagina "My Leave List"
