import java.util.*;
import java.util.Arrays;
import java.io.Console;
import java.time.LocalDate;

public class ProyectoCine {

	// Instancia para captura de teclas presionadas
	public static Console cs = System.console();

	// Recolector de datos ingresados
	public static Scanner sc = new Scanner(System.in);

	// Declaracion de variables globales
	public static int creditoDisponible = 30000;
	public static int[] precios = {2000, 5000, 3500};
	public static int[] diasEstablecidos = { 0, 1, 2, 3};
	public static int[][] peliculasEstablecidas = { { 0, 1, 2, 3 }, { 1, 2, 3, 4}, { 1, 2, 3, 4 }, { 1, 2, 3, 4 } };
	public static int[][] horariosEstablecidos = { { 0, 1, 2, 3 }, { 1, 2, 4, 5 }, { 3, 4, 5, 6 }, { 7 }, { 2, 3, 4, 5 } };
	public static int[][] registroPagados = new int[100][10];
	public static int[][] registroReservados = new int[100][10];
	public static int[][] registroTransacciones = new int[500][10];
	
	public static String[] listaDias = new String[4];
	public static String[] listaPeliculas = { "Disney", "Comedia", "Superheroes", "Terror", "Documentales" };
	public static String[] listaHorarios = { "11:00am", "1:00pm", "3:15pm", "4:40pm", "6:30pm", "7:45pm", "9:30pm", "11:50pm" };
	public static String[] filasSala = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J" };
	public static String[] columnasSala = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10" };

	// Metodo principal
	public static void main(String[] args) {

		cargar_datos_prueba();
		cargarFechas();
		principal();
	}

	// Metodo para mostrar pantalla principal
	public static void principal() {

		// Declaración de variables
		int opcion = 0;

		while (opcion != 5) {
			// Menu principal

			limpiarPantalla();
			mostrarTitulo("", "Menu principal");
			mostrarPanelPrincipal();
			mostrarNotificacion("Seleccione una opcion");
			System.out.println(
					"(1) Reservar boletos \n" +
					"(2) Verificar reservaciones \n" +
					"(3) Panel de administrador \n\n" +
					"(S) Salir \n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();
			limpiarPantalla();

			if (input.equals("S")) {
				if(confirmarSalida())
				{
					limpiarPantalla();
					System.exit(0);
				}
			} else {
				// Validacion de datos
				opcion = validarEntrada(input);

				// Seleccionador de opciones
				switch (opcion) {
					case 1:
						reservar();
						break;
					case 2:
						verificar();
						break;
					case 3:
						administrar();
						break;
					default:
						mostrarError("La opcion ingresada es incorrecta");
						break;
				}
			}
		}
	}

	// Metodo para seleccionar dia
	public static void reservar() {

		// Declaración de variables
		int dia = 0;
		int[] datos = new int[9];

		// Bucle principal
		while (dia != 999) {
			limpiarPantalla();
			mostrarTitulo("", "Menu Reservas");
			mostrarPanelPrincipal();
			mostrarNotificacion("Seleccionar el dia de la reserva");
			mostrarOpciones(diasEstablecidos, listaDias);
			System.out.println("(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();
			limpiarPantalla();

			if (input.equals("R")) {
				dia = 999;
			}else if (input.equals("S")) {
				if(confirmarSalida())
				{
					dia = 999;
					principal();
				}
			} else {
				// Validacion de datos
				dia = validarEntrada(input);

				// Página siguiente
				if (dia > listaDias.length) {
					dia = 0;
					mostrarError("La opcion ingresada es incorrecta");
				} else {
					datos[0] = 1;
					datos[1] = dia;
					seleccionarPelicula(datos);
				}
			}
		}
	}

	// Metodo para seleccionar pelicula
	public static void seleccionarPelicula(int[] datos) {

		// Declaración de variables
		int pelicula = 0;
		int dia = datos[1];
		String datosTitulo = formatearTitulo(datos);

		while (pelicula != 999) {
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Menu peliculas");
			mostrarPanelPrincipal();
			mostrarNotificacion("Seleccionar la pelicula a ver");
			mostrarOpciones(peliculasEstablecidas[dia - 1], listaPeliculas);
			System.out.println("(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();
			limpiarPantalla();

			if (input.equals("R")) {
				pelicula = 999;
			}else if (input.equals("S")) {
				if(confirmarSalida())
				{
					pelicula = 999;
					principal();
				}
			} else {
				// Validacion de datos
				pelicula = validarEntrada(input);

				// Página siguiente
				if (pelicula > peliculasEstablecidas[dia - 1].length) {
					pelicula = 0;
					mostrarError("La opcion ingresada es incorrecta");
				} else {
					datos[2] = peliculasEstablecidas[dia - 1][pelicula - 1] + 1;
					seleccionarHorario(datos);
				}
			}
		}
	}

	// Metodo para seleccionar horario
	public static void seleccionarHorario(int[] datos) {

		// Declaración de variables
		int horario = 0;
		int pelicula = datos[2];
		String datosTitulo = formatearTitulo(datos);

		while (horario != 999) {
			mostrarTitulo(datosTitulo, "Menu horarios");
			mostrarPanelPrincipal();
			mostrarNotificacion("Seleccionar el horario de la pelicula");
			mostrarOpciones(horariosEstablecidos[pelicula - 1], listaHorarios);
			System.out.println("(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();
			limpiarPantalla();

			if (input.equals("R")) {
				horario = 999;
			}else if (input.equals("S")) {
				if(confirmarSalida())
				{
					horario = 999;
					principal();
				}
			} else {
				// Validacion de datos
				horario = validarEntrada(input);

				// Página siguiente
				if (horario > horariosEstablecidos[pelicula - 1].length) {
					horario = 0;
					mostrarError("La opcion ingresada es incorrecta");
				} else {
					datos[3] = horariosEstablecidos[pelicula - 1][horario - 1] + 1;
					seleccionarBoletos(datos);
				}
			}
		}
	}

	// Metodo para seleccionar boletos
	public static void seleccionarBoletos(int[] datos) {

		// Declaración de variables
		int boleto = 0;
		int[] listaBoletos = { datos[4], datos[5], datos[6] };
		int totalBoletos = listaBoletos[0] + listaBoletos[1] + listaBoletos[2];
		String datosTitulo = formatearTitulo(datos);
		String[][] butacas = generarButacas(datos);

		while (boleto != 99) {
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Menu boletos");
			mostrarSala(butacas);
			mostrarNotificacion("Indicar los boletos a comprar (Compra actual = " + totalBoletos + ")");
			System.out.println(
					"(1) Niños        [" + listaBoletos[0] + " x ¢" + String.format("%,d", precios[0]) + "]\n" +
					"(2) Adulto joven [" + listaBoletos[1] + " x ¢" + String.format("%,d", precios[1]) + "]\n" +
					"(3) Adulto mayor [" + listaBoletos[2] + " x ¢" + String.format("%,d", precios[2]) + "]\n\n" +
					"(A) Seleccionar asientos\n" +
					"(R) Regresar\n" +
					"(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();

			if (input.equals("R")) {
				boleto = 99;
				limpiarPantalla();
			}else if (input.equals("S")) {
				if(confirmarSalida())
				{
					boleto = 99;
					principal();
				}
			} else if (input.equals("A")) {
				if (totalBoletos > 0) {
					seleccionarAsientos(datos, butacas);
				} else {
					boleto = 0;
					mostrarError("No ha seleccionado ningún boleto");
				}

			} else {
				// Validacion de datos
				boolean validado = false;

				boleto = validarEntrada(input);
				if (boleto >= 1 & boleto <= 3) {
					System.out.print("Indique la cantidad de boletos: ");
					String input2 = sc.next();
					int cantidad = validarEntrada(input2);

					validado = validarCantidadBoletos(listaBoletos, boleto, cantidad);

					if (validado & cantidad != 999) {
						listaBoletos[boleto - 1] += cantidad;
						totalBoletos += cantidad;
						datos[4] = listaBoletos[0];
						datos[5] = listaBoletos[1];
						datos[6] = listaBoletos[2];
					} else {
						mostrarError("La cantidad ingresada es incorrecta");
					}
				} else {
					mostrarError("El tipo de boleto es incorrecto");
				}
			}
		}
	}

	// Metodo para seleccionar asientos
	public static void seleccionarAsientos(int[] datos, String[][] butacas) {

		// Declaración de variables
		int asiento = 0;
		int fila = -1;
		int columna = -1;
		int totalBoletos = datos[4] + datos[5] + datos[6];
		int datosReserva[][] = new int[1][10];
		boolean guardar = false;
		String datosTitulo = formatearTitulo(datos);
		String[][] butacasTemporal = copiarMatriz(butacas);

		while (asiento != 99) {
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Menu asientos");
			mostrarSala(butacasTemporal);
			mostrarNotificacion("Indicar el primero de " + totalBoletos + " asiento(s) a reservar");
			System.out.println(
					"Nota: Debe seleccionar el primer asiento\n" +
							"Los demás asientos seran asignados automaticamente a la derecha\n\n" +
							"(G) Guardar reservacion\n" +
							"(R) Regresar\n" +
							"(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();

			if (input.equals("R")) {
				asiento = 99;
				limpiarPantalla();
			} else if (input.equals("S")) {
				if(confirmarSalida())
				{
					asiento = 99;
					principal();
				}
			}else if (input.equals("G")) {
				if(guardar)
				{
					guardarReservacion(datos);
				}
				else
				{
					mostrarError("Debe seleccionar el asiento");
				}

			} else {
				try
				{
					fila = Arrays.asList(filasSala).indexOf(input.substring(0, 1));
					columna = Arrays.asList(columnasSala).indexOf(input.substring(1, 3));
				}
				catch (Exception E) {
				}

				int valido = validarAsientos(fila, columna, totalBoletos);
				int disponible = validarDisponibilidad(fila, columna, totalBoletos, butacas);
				if (valido == 0 & disponible == 0) {
					datos[7] = fila;
					datos[8] = columna;
					datosReserva[0] = datos;
					butacasTemporal = copiarMatriz(butacas);
					actualizarButacas(butacasTemporal, datosReserva, "O");
					guardar = true;
				}
			}
		}
	}

	// Metodo para guardar reservacion
	public static void guardarReservacion(int[] datos) {

		// Declaración de variables
		int opcion = 0;
		int totalBoletos = datos[4] + datos[5] + datos[6];
		String nombreDia = obtenerNombreDia(datos);
		String nombrePelicula = obtenerNombrePelicula(datos);
		String nombreHorario = obtenerHorarioPelicula(datos);
		String datosTitulo = formatearTitulo(datos);

		int posicion = obtenerUltimaPosicion(registroReservados);
		registroReservados[posicion] = datos;
		registrarTransaccion(datos, 1);

		String[][] butacas = generarButacas(datos);
		String[] asientos = obtenerNumeroAsientos(datos);

		while (opcion != 99) {
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Reservacion guardada");
			mostrarSala(butacas);
			mostrarNotificacion("Se ha reservado " + totalBoletos + " boletos");

			System.out.print(
				"Pelicula" + nombrePelicula + "\n" +
				"Fecha" + nombreDia + "  Hora" + nombreHorario + "\n" +
				"Sala >> " + datos[2] + "   Asientos >> ");

			for(int i = 0; i < asientos.length; i++)
			{
				System.out.print("|" + asientos[i] + "| ");
			}

			System.out.println(
							"\n\n" +
							"(P) Procesar pago\n" +
							"(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();

			if (input.equals("P")) {
				procesarPago(posicion);
			} else if (input.equals("S")) {
				if(confirmarSalida())
				{
				opcion = 99;
				principal();
				}
			} else {
				mostrarError("La opcion ingresada es incorrecta");
			}
		}
	}

	// Metodo para procesar un pago
	public static void procesarPago(int posicion) {

		// Declaración de variables
		int opcion = 0;
		int[] datos = registroReservados[posicion];
		int totalBoletos = datos[4] + datos[5] + datos[6];
		int total = (datos[4] * precios[0]) + (datos[5] * precios[1]) + (datos[6] * precios[2]);
		String datosTitulo = formatearTitulo(datos);
		String[][] butacas = generarButacas(datos);

		int datosReserva[][] = new int[1][10];
		datosReserva[0] = datos;
		actualizarButacas(butacas, datosReserva, "O");

		while (opcion != 999) {
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Procesar pago");
			mostrarSala(butacas);
			mostrarNotificacion("Proceder con el pago de " + totalBoletos + " boletos por un monto de ¢" + String.format("%,d", total));

			String totalNinos = String.format("%,d", datos[4] * precios[0]);
			String totalAdultos = String.format("%,d", datos[5] * precios[1]);
			String totalMayores = String.format("%,d", datos[6] * precios[2]);

			System.out.println(
					"Niños        [" + datos[4] + " x ¢" + String.format("%,d", precios[0]) +  "] = ¢" + totalNinos + "\n" +
					"Adulto joven [" + datos[5] + " x ¢" + String.format("%,d", precios[1]) +  "] = ¢" + totalAdultos + "\n" +
					"Adulto mayor [" + datos[6] + " x ¢" + String.format("%,d", precios[2]) +  "] = ¢" + totalMayores + "\n\n" +
					"(P) Utilizar credito (Disponible: ¢" + String.format("%,d", creditoDisponible) +")\n" +
					"(S) Salir\n");

			// Captura de datos
			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();

			if (input.equals("P")) {
				if(creditoDisponible >= total)
				{
					creditoDisponible -= total;
					finalizarPago(posicion);
				}
				else
				{
					mostrarError("Credito insuficiente");
				}

			} else if (input.equals("S")) {
				if(confirmarSalida())
				{
				opcion = 999;
				principal();
				}
			} else {
				mostrarError("La opcion ingresada es incorrecta");
			}
		}
	}

	// Metodo para procesar un pago
	public static void finalizarPago(int posicion) {

		// Declaración de variables
		int[] datos = registroReservados[posicion];
		String datosTitulo = formatearTitulo(datos);

		registrarTransaccion(datos, 2);
		registroReservados = eliminarRegistro(registroReservados, posicion);
		registroPagados = agregarRegistro(registroPagados, datos);

		String[][] butacas = generarButacas(datos);

		limpiarPantalla();
		mostrarTitulo(datosTitulo, "Finalizar pago");
		mostrarSala(butacas);
		mostrarNotificacion("Pago procesado con exito");

		System.out.println("Credito disponible: ¢" + String.format("%,d", creditoDisponible) + "\n");
		mostrarMensajeEspera();
		principal();
	}

	// Metodo para pagar
	public static void verificar() {
		
		// Declaración de variables
		int opcion = 0;
		int[] datos = new int[10];

		while (opcion != 999)
		{
			limpiarPantalla();
			mostrarTitulo("", "Verificar reservaciones");
			mostrarPanelPrincipal();
			mostrarNotificacion("Verificar reservaciones");

			int cantidad = 0;
			for(int i = 0; i < registroReservados.length; i++)
			{
				if(registroReservados[i][0] == 1)
				{
					String dia = obtenerNombreDia(registroReservados[i]);
					String pelicula = obtenerNombrePelicula(registroReservados[i]);
					String horario = obtenerHorarioPelicula(registroReservados[i]);
					String asientos[] = obtenerNumeroAsientos(registroReservados[i]);
					int ninos = registroReservados[i][4];
					int adultos = registroReservados[i][5];
					int mayores = registroReservados[i][6];
					System.out.print(
						"(" + (cantidad + 1) + ")" + dia + pelicula + horario + 
						"\n    Niños: " + ninos + " Adultos: " + adultos + " Mayores: " + mayores +
						"\n    Asientos: ");
					for(int j = 0; j <asientos.length; j++)
					{
						System.out.print("|" + asientos[j] + "| ");
					}
					System.out.println("\n");
					cantidad ++;
				}

			}

			// Captura de datos
			if(cantidad > 0)
			{
				System.out.print("(S) Salir al menu principal\n\n");
				System.out.print("Opcion: ");
				String input = sc.next().toUpperCase();

				if (input.equals("S"))
				{
					if(confirmarSalida())
					{
						opcion = 999;
						principal();
					}
				}
				else
				{
					int posicion = validarEntrada(input) - 1;
	
					if(posicion < cantidad)
					{
						datos = registroReservados[posicion];
						operarReservacion(posicion);
					}
					else
					{
						mostrarError("Opcion incorrecta");
					}
				}
			}
			else
			{
				mostrarError("No hay reservaciones para mostrar");
				opcion = 999;
			}
		}
	}

	// Metodo para operar una reservacion
	public static void operarReservacion(int posicion) {
		
		// Declaración de variables
		int reservacion = 0;
		int[] datos = registroReservados[posicion];
		String[][] butacas = generarButacas(datos);
		String nombreDia = obtenerNombreDia(datos);
		String nombrePelicula = obtenerNombrePelicula(datos);
		String nombreHorario = obtenerHorarioPelicula(datos);
		String datosTitulo = formatearTitulo(datos);

		int datosReserva[][] = new int[1][10];
		datosReserva[0] = datos;
		actualizarButacas(butacas, datosReserva, "O");

		while (reservacion != 999)
		{
			limpiarPantalla();
			mostrarTitulo(datosTitulo, "Modificar reservacion");
			mostrarSala(butacas);
			mostrarNotificacion("Reservacion a modificar");

			String dia = obtenerNombreDia(datos);
			String pelicula = obtenerNombrePelicula(datos);
			String horario = obtenerHorarioPelicula(datos);
			String asientos[] = obtenerNumeroAsientos(datos);
			int ninos = datos[4];
			int adultos = datos[5];
			int mayores = datos[6];
			System.out.print(
				"   " + dia + pelicula + horario + 
				"\n    Niños: " + ninos + " Adultos: " + adultos + " Mayores: " + mayores +
				"\n    Asientos: ");
			for(int j = 0; j <asientos.length; j++)
			{
				System.out.print("|" + asientos[j] + "| ");
			}

			System.out.println(
				"\n\nSeleccione una opcion \n\n" + 
				"(P) Pagar reservacion\n" + 
				"(C) Cancelar reservacion\n" +
				"(R) Regresar a la pantalla anterior\n" +
				"(S) Salir al menu principal\n");
			// Captura de datos

			System.out.print("Opcion: ");
			String input = sc.next().toUpperCase();
			
			if (input.equals("R")) {
				reservacion = 999;
			}else if (input.equals("S")) {
				if(confirmarSalida())
				{
					reservacion = 999;
					principal();
				}
			}else if (input.equals("P")) {
				procesarPago(posicion);
			}else if (input.equals("C")) {
				if(confirmarCancelacion())
				{
					cancelarReservacion(posicion);
				}
			} else {
				mostrarError("La opcion ingresada es incorrecta");
			}
		}
	}

	// Metodo para cancelar una reservacion
	public static void cancelarReservacion(int posicion) 
	{
		int[] datos = registroReservados[posicion];
		String datosTitulo = formatearTitulo(datos);

		registrarTransaccion(datos, 3);
		registroReservados = eliminarRegistro(registroReservados, posicion);

		String[][] butacas = generarButacas(datos);

		limpiarPantalla();
		mostrarTitulo("", "Cancelar reservacion");
		mostrarSala(butacas);
		mostrarNotificacion("Reservacion cancelada con exito");
		mostrarMensajeEspera();
		principal();
	}

	// Metodo para administrar
	public static void administrar() {
		mostrarTitulo("", "Panel de administrador");
		mostrarPanelPrincipal();
		mostrarNotificacion("Panel de administrador");

		for(int i = 0; i < registroTransacciones.length; i++)
		{
			if(registroTransacciones[i][registroTransacciones[0].length - 1] > 0)
			{
				System.out.println(Arrays.toString(registroTransacciones[i]));
			}
		}
		mostrarMensajeEspera();
	}

	// Metodo para mostrar mensaje de error
	public static void mostrarError(String mensaje) {
		limpiarPantalla();
		mostrarTitulo("", "Ventana emergente");
		mostrarPanelError();
		mostrarNotificacion(mensaje);
		mostrarMensajeEspera();
	}

	// Metodo para mostrar mensaje de salida
	public static boolean confirmarSalida() {
		limpiarPantalla();
		mostrarTitulo("", "Ventana emergente");
		mostrarPanelAtencion();
		mostrarNotificacion(
			"Seguro que desea salir?\n" +
			"Los datos no guardados seran eliminados");

		// Captura de datos
		System.out.print("Presione 'Y' para confirmar: ");
		String input = sc.next().toUpperCase();

		if(input.equals("Y"))
		{
			return true;
		}
		return false;
	}

		// Metodo para mostrar mensaje de salida
		public static boolean confirmarCancelacion() {
			limpiarPantalla();
			mostrarTitulo("", "Ventana emergente");
			mostrarPanelAtencion();
			mostrarNotificacion(
				"Seguro que desea cancelar la reservacion?\n" +
				"Esta accion no puede ser revertida");
	
			// Captura de datos
			System.out.print("Presione 'Y' para confirmar: ");
			String input = sc.next().toUpperCase();
	
			if(input.equals("Y"))
			{
				return true;
			}
			return false;
		}

	// Mostrar banner principal
	public static void mostrarPanelPrincipal() {
		System.out.println(
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n" +
				".   _____             __                   _           .\n" +
				".  / ___/ ___ _ ____ / /_ ___ _ ___ _ ___ ( )___       .\n" +
				". / /__  / _ `// __// __// _ `// _ `// _ \\|/(_-<       .\n" +
				". \\___/  \\_,_//_/   \\__/ \\_,_/ \\_, / \\___/ /___/       .\n" +
				".   __  __        _           /___/       _  __        .\n" +
				".  / / / / ___   (_)_  __ ___  ____ ___  (_)/ /_ __ __ .\n" +
				". / /_/ / / _ \\ / /| |/ // -_)/ __/(_-< / // __// // / .\n" +
				". \\____/ /_//_//_/ |___/ \\__//_/  /___//_/ \\__/ \\_, /  .\n" +
				".   _____  _                                   /___/   .\n" +
				".  / ___/ (_)___  ___  __ _  ___ _                     .\n" +
				". / /__  / // _ \\/ -_)/  ' \\/ _ `/                     .\n" +
				". \\___/ /_//_//_/\\__//_/_/_/\\_,_/                      .\n" +
				".                                                      .\n" +
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n");

	}

	// Mostrar banner de error
	public static void mostrarPanelError() {
		System.out.println(
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".              ___                      _              .\n" +
				".             | __| _ _  _ _  ___  _ _ | |             .\n" +
				".             | _| | '_|| '_|/ _ \\| '_||_|             .\n" +
				".             |___||_|  |_|  \\___/|_|  (_)             .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n");

	}

	// Mostrar banner de atencion
	public static void mostrarPanelAtencion() {
		System.out.println(
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".         _   _                   _             _      .\n" +
				".        /_\\ | |_  ___  _ _   __ (_) ___  _ _  | |     .\n" +
				".       / _ \\|  _|/ -_)| ' \\ / _|| |/ _ \\| ' \\ |_|     .\n" +
				".      /_/ \\_\\\\__|\\___||_||_|\\__||_|\\___/|_||_|(_)     .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				".                                                      .\n" +
				". . . . . . . . . . . . . . . . . . . . . . . . . . . .\n");

	}

	// Metodo para generar lista de butacas
	public static void registrarTransaccion(int[] datos, int tipo)
	{
		int posicion = obtenerUltimaPosicion(registroTransacciones);	
		for(int i = 0; i < datos.length; i++)
		{
			registroTransacciones[posicion][i] = datos[i];
		}
		registroTransacciones[posicion][datos.length] = tipo;
	}


	// Metodo para generar lista de butacas
	public static String[][] generarButacas(int[] datos) {

		// Declaración de variables
		int[][] listaReservas = enlistarRegistros(datos, registroReservados);
		int[][] listaPagos = enlistarRegistros(datos, registroPagados);
		String[][] butacas = new String[filasSala.length][columnasSala.length];

		for (int i = 0; i < butacas.length; i++) {
			for (int j = 0; j < butacas[0].length; j++) {
				butacas[i][j] = filasSala[i].toString() + columnasSala[j].toString();
			}
		}

		butacas = actualizarButacas(butacas, listaReservas, "R");
		butacas = actualizarButacas(butacas, listaPagos, "P");

		return butacas;
	}

	// Metodo para generar lista de registros
	public static int[][] enlistarRegistros(int[] datos, int[][] registro) {

		// Declaración de variables
		int dia = datos[1];
		int pelicula = datos[2];
		int horario = datos[3];
		int cantidad = 0;

		for (int r = 0; r < registro.length; r++) {
			if (registro[r][0] == 1) {
				if (registro[r][1] == dia & registro[r][2] == pelicula & registro[r][3] == horario) {
					cantidad++;
				}
			}
		}

		if (cantidad > 0) {
			int[][] listaRegistros = new int[cantidad][datos.length];

			int p = 0;
			for (int r = 0; r < registro.length; r++) {
				if (registro[r][1] == dia & registro[r][2] == pelicula & registro[r][3] == horario) {
					for (int d = 0; d < registro[0].length; d++) {
						listaRegistros[p][d] = registro[r][d];
					}
					p++;
				}
			}
			return listaRegistros;
		} else {
			int[][] listaRegistros = new int[0][0];
			return listaRegistros;
		}
	}

	// Metodo para generar lista de registros
	public static int[][] ordenarRegistros(int[][] registro) {

		// Declaración de variables
		int indice = 0;
		int[][] registroOrdenado = new int[registro.length][registro[0].length];
		for (int i = 0; i < registro.length; i++) {
			if (registro[i][0] == 1) {
				for (int j = 0; j < registro[0].length; j++) {
						registroOrdenado[indice][j] = registro[i][j];
					}
				indice++;
			}
		}
		return registroOrdenado;
	}

	// Metodo para agregar un registro
	public static int[][] agregarRegistro(int[][] registro, int[] datos)
	{
		int posicion = obtenerUltimaPosicion(registro);
		registro[posicion] = datos;
		return registro;
	}

	// Metodo para eliminar un registro
	public static int[][] eliminarRegistro(int[][] registro, int posicion)
	{
		int[] listaBlanco = {0, 0, 0, 0, 0, 0, 0, 0, 0};
		registro[posicion] = listaBlanco;
		registro = ordenarRegistros(registro);
		return registro;
	}

	// Metodo para generar lista de butacas
	public static String[][] actualizarButacas(String[][] butacas, int[][] registro, String marca) {
		try {
			for (int i = 0; i < registro.length; i++) {
				int cantidad = registro[i][4] + registro[i][5] + registro[i][6];
				int fila = registro[i][7];
				int columna = registro[i][8];
				for (int j = 0; j < cantidad; j++) {
					butacas[fila][columna + j] = "-" + marca + "-";
				}
			}
		} catch (Exception E) {
		}
		return butacas;
	}

	// Metodo para copiar una matriz
	public static String[][] copiarMatriz(String[][] matriz) {
		String[][] matrizCopia = new String[matriz.length][matriz[0].length];

		for (int i = 0; i < matriz.length; i++) {
			for (int j = 0; j < matriz[0].length; j++) {
				matrizCopia[i][j] = matriz[i][j];
			}
		}
		return matrizCopia;
	}

	// Metodo para obtener numero de asientos
	public static String[] obtenerNumeroAsientos(int[] datos) {

		// Declaración de variables
		int cantidad = datos[4] + datos[5] + datos[6];
		int fila = datos[7];
		int columna = datos[8];
		String asiento = "";
		String[] asientos = new String[cantidad];

		for(int i = 0; i < cantidad; i++)
		{
			asiento = filasSala[fila] + columnasSala[columna + i];
			asientos[i] = asiento;
		}
		return asientos;
	}

	// Metodo para procesar un pago
	public static int obtenerUltimaPosicion(int[][] registro) {

		int posicion = 0;
		for(int i = 0; i < registro.length; i++)
		{
			if (registro[i][0] == 1) {
				posicion++;
			}
		}
		return posicion;
	}

	// Mostrar configuración de la sala
	public static void mostrarSala(String[][] butacas) {

		// Declaración de variables
		String butaca = "";

		System.out.print(". ".repeat(28) + "\n" +
				"." + " ".repeat(2) + "-".repeat(21) + "PANTALLA" + "-".repeat(21) + " ".repeat(2) + ".\n" +
				"." + " ".repeat(54) + "." + "\n" + "." + " ".repeat(54) + "." + "\n");

		for (int i = 0; i < butacas.length; i++) {
			System.out.print(".");
			for (int j = 0; j < butacas[0].length; j++) {
				butaca = "|" + butacas[i][j] + "|";
				if (j == 1 | j == 7) {
					butaca = "|" + butacas[i][j] + "|  ";
				}
				System.out.print(butaca);
			}
			System.out.print(".");
			System.out.println();
		}
		System.out.print(". ".repeat(28) + "\n\n");
	}

	// Mostrar barra de titulo
	public static void mostrarTitulo(String titulo, String pagina) {
		System.out.println("Cartago's University Cinema" + titulo);
		System.out.println("\n-- " + pagina + " --");
	}

	// Formatear texto del titulo
	public static String formatearTitulo(int[] datos) {
		String nombreDia = obtenerNombreDia(datos);
		String nombrePelicula = obtenerNombrePelicula(datos);
		String nombreHorario = obtenerHorarioPelicula(datos);
		String datosTitulo = nombreDia + nombrePelicula + nombreHorario;

		return datosTitulo;
	}

	// Mostrar barra de notificaciones
	public static void mostrarNotificacion(String notificacion) {
		System.out.println(notificacion + "\n");
	}

	// Mostrar opciones de menu
	public static void mostrarOpciones(int[] opciones, String[] lista) {
		for (int i = 0; i < opciones.length; i++) {
			System.out.println("(" + (i + 1) + ") " + lista[opciones[i]]);
		}
		System.out.println("\n(R) Regresar");
	}

	// Validador de asientos
	public static int validarAsientos(int fila, int columna, int cantidad) {
		if (fila == -1 | columna == -1) {
			mostrarError("Asiento fuera del rango");
			return 1;
		} else if ((columna + cantidad) > columnasSala.length) {
			mostrarError("Seleccion fuera de rango");
			return 2;
		}
		return 0;
	}

	// Validador de disponibilidad
	public static int validarDisponibilidad(int fila, int columna, int cantidad, String[][] butacas) {
		try
		{
			for (int i = 0; i < cantidad; i++) {
				if (butacas[fila][columna + i].equals("-R-") | butacas[fila][columna + i].equals("-P-")) {
					mostrarError("No es posible reservar los asientos seleccionados");
					return 1;
				}
			}
		} catch (Exception E) {
			return 1;
		}
		return 0;
	}

	// Validador de formato de entrada
	public static int validarEntrada(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception E) {
			return 999;
		}
	}

	// Validador de cantidad de boletos
	public static boolean validarCantidadBoletos(int[] listaBoletos, int boleto, int cantidad) {
		if (listaBoletos[boleto - 1] + cantidad >= 0) {
			return true;
		}
		return false;
	}

	// Metodo para registrar fechas
	public static void cargarFechas() {
		for (int i = 0; i < listaDias.length; i++) {
			LocalDate hoy = LocalDate.now();
			String fecha = LocalDate.parse(hoy.toString()).plusDays(i).toString();
			listaDias[i] = fecha;
		}
	}

	// Metodo para obtener nombre del dia
	public static String obtenerNombreDia(int[] datos) {

		// Declaración de variables
		int dia = datos[1];
		String nombreDia = "";

		if(dia > 0)
		{
			nombreDia = " >> " + listaDias[dia - 1];
		}
		return nombreDia;
	}

	// Metodo para obtener nombre de la pelicula
	public static String obtenerNombrePelicula(int[] datos) {

		// Declaración de variables
		int dia = datos[1];
		int pelicula = datos[2];
		String nombrePelicula = "";

		if(pelicula > 0)
		{
			nombrePelicula = " >> " + listaPeliculas[pelicula - 1];
		}
		return nombrePelicula;
	}

	// Metodo para obtener horario de la pelicula
	public static String obtenerHorarioPelicula(int[] datos) {

		// Declaración de variables
		int dia = datos[1];
		int pelicula = datos[2];
		int horario = datos[3];
		String nombreHorario = "";

		if(horario > 0)
		{
			nombreHorario = " >> " + listaHorarios[horario - 1];
		}
		return nombreHorario;
	}

	// Metodo para limpiar la pantalla
	public static void limpiarPantalla() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception E) {
			System.out.println(E);
		}
	}

	// Metodo para regresar al menu principal
	public static void mostrarMensajeEspera() {
		System.out.print("Presione Enter para continuar... ");
		cs.readLine();
		limpiarPantalla();
	}

	// Metodo de datos de prueba
	public static void cargar_datos_prueba() {

		int[] datos1 = {1,1,1,1,1,1,1,0,0};
		registroReservados[0] = datos1;

		int[] datos2 = {1,2,1,1,1,1,1,5,7};
		registroReservados[1] = datos2;

		int[] datos3 = {1,1,1,1,1,1,1,1,3};
		registroPagados[0] = datos3;
	}
}