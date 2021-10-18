Feature: Consulta de api

	Scenario: Validar GET
	    Given cargo configuración del servicio
	    When llamo al servicio con parámetro 33 y numero de comentario 3338767
	    Then respuesta con estado 200 y el tiempo de respuesta sea 500 ms

	Scenario: Validar POST
			Given cargo configuración del servicio
		  When preparo body "Aqui el comentario" y parámetro 33
			Then respuesta con estado 201
