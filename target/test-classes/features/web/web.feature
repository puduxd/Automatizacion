@iexplorer
Feature: Prueba de Automatización Funcional

Background: 
    Given ingreso a URL "https://www.falabella.com/falabella-cl/"

	Scenario: Compra de calzado
	    Given navegar a menu "Categorías" luego a "Zapatos"
	    When realizar click en "Zapatos de seguridad"
	    And realizar filtración por "Botas"
	    * realizar click en algun producto "CALL IT SPRING - Bota Hombre Café" y talla "10"
	    # (hacer clic en el botón “+” hasta llegar a 4)
	    * Aumentar la cantidad hasta 4 
	    Then Validar al realizar click en "Ver bolsar de compras", para desplegar el detalle de la bolsa de compras
	    # (No continuar con la compra)
	    * Culminar la automatización
	    
