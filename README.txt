
En el paquete proporcionado hay 2 agentes:
	AgenteEstado es el agente encargado de hacer el entrenamiento.
	AgenteTestEstado es el agente encargado de hacer el test.

Si se quiere entrenar, hay que añadir en la clase tracks/ArcadeMachine.java la siguiente línea en la siguiente posición:
Línea 419
Comando:JoseDavidOrtizGomez.AgentEstado.n++;

El Fichero TablaQ tiene que estar en la raíz de la carpeta del proyecto, y debe llamarse "TablaQ". Por defecto se
incluye la TablaQ del entrenamiento óptimo, pero se facilitan las Tablas Q de los otros dos entrenamientos
mencionados en el video (50 y 2000 iteraciones).

Para graficar las estadísticas es necesario  incluir la librería JFreeChart facilitada en la entrega. 
Tener en cuenta, que en la clase Estadisticas.java hay que poner el número de iteraciones exacto que se vaya a hacer
en el entrenamiento para que funcione.