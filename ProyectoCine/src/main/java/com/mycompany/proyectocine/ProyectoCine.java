/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.proyectocine;

import java.io.Console;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Sistema de administracion de venta de boletos Cartago's University Cinema
 *
 * @author Rau Gonzalez
 */
public class ProyectoCine {

    /**
     * * Instancia para captura de teclas presionadas *
     */
    public static Console cs = System.console();
    /**
     * * Recolector de datos ingresados *
     */
    public static Scanner sc = new Scanner(System.in);
    /**
     * * Credito disponible para compras *
     */
    public static int creditoDisponible = 30000;
    /**
     * * Precio de los boletos *
     */
    public static int[] preciosBoletos = {2000, 5000, 3500};
    /**
     * * Lista de dias disponibles en el menu *
     */
    public static int[] diasDisponibles = {0, 1, 2, 3};
    /**
     * * Lista de peliculas disponibles en el menu *
     */
    public static int[][] peliculasDisponibles = {{0, 1, 2, 3}, {1, 2, 3, 4}, {1, 2, 3, 4}, {1, 2, 3, 4}};
    /**
     * * Lista de horarios disponibles en el menu *
     */
    public static int[][] horariosDisponibles = {{0, 1, 2, 3}, {1, 2, 4, 5}, {3, 4, 5, 6}, {7}, {2, 3, 4, 5}};
    /**
     * * Registro de pagos de reservaciones *
     */
    public static int[][] registroPagos = new int[100][10];
    /**
     * * Registro de reservaciones de boletos *
     */
    public static int[][] registroReservaciones = new int[100][10];
    /**
     * * Registro de transacciones hechas *
     */
    public static int[][] registroTransacciones = new int[500][10];
    /**
     * * Vector de nombres de dias *
     */
    public static String[] vectorDias = new String[4];
    /**
     * * Vector de nombres de peliculas *
     */
    public static String[] vectorPeliculas = {"Disney", "Comedia", "Superheroes", "Terror", "Documentales"};
    /**
     * * Vector de nombres de horarios *
     */
    public static String[] vectorHorarios = {"11:00am", "1:00pm", "3:15pm", "4:40pm", "6:30pm", "7:45pm", "9:30pm", "11:50pm"};
    /**
     * * Vector de nombres de filas *
     */
    public static String[] vectorFilas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    /**
     * * Vector de nombres de columnas *
     */
    public static String[] vectorColumnas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};

    /**
     * Metodo constructor de clase
     *
     */
    public ProyectoCine() {
    }

    /**
     * Metodo principal
     *
     * @param args Parametros ingresados por consola
     */
    public static void main(String[] args) {

        cargarDatosPruebas();
        cargarFechas();
        pantallaPrincipal();
    }

    /**
     * Metodo para mostrar la pantalla principal
     *
     */
    public static void pantallaPrincipal() {

        // Declaracion de variables
        String opcion = "";

        // Bucle principal
        while (opcion != "S") {
            // Menu principal
            limpiarPantalla();
            mostrarTitulo("", "Menu principal");
            mostrarBannerPrincipal();
            mostrarNotificacion("Seleccione una opcion");
            System.out.println("""
                                           (1) Reservar boletos
                                           (2) Verificar reservaciones
                                           (3) Panel de administrador

                                           (S) Salir
                                           """);

            // Captura de datos
            System.out.print("Opcion: ");
            opcion = sc.next().toUpperCase();
            limpiarPantalla();

            // Verificacion de opciones
            switch (opcion) {
                case "1" ->
                    reservar();
                case "2" ->
                    verificar();
                case "3" ->
                    administrar();
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?")){
                        limpiarPantalla();
                        System.exit(0);
                    }
                }
                default -> {
                    mostrarError("La opcion ingresada es incorrecta");
                }
            }  
        }
    }

    /**
     * Metodo para seleccionar el dia de la reservacion
     *
     */
    public static void reservar() {

        // Declaracion de variables
        int dia = 0;
        int[] datosReservacion = new int[10];

        // Bucle principal
        while (dia != 999) {
            // Menu de pagina
            limpiarPantalla();
            mostrarTitulo("", "Menu Reservas");
            mostrarBannerPrincipal();
            mostrarNotificacion("Seleccionar el dia de la reserva");
            mostrarOpciones(diasDisponibles, vectorDias);
            System.out.println("(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();
            limpiarPantalla();

            // Seleccion de opciones
            switch (input) {
                case "R" ->
                    dia = 999;
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        dia = 999;
                        pantallaPrincipal();
                    }
                }
                default -> {
                    // Validacion de datos
                    dia = convertirTextoNumero(input);
                    // Pagina siguiente
                    if (dia > vectorDias.length) {
                        dia = 0;
                        mostrarError("La opcion ingresada es incorrecta");
                    } else {
                        datosReservacion[0] = 1;
                        datosReservacion[1] = dia;
                        seleccionarPelicula(datosReservacion);
                    }
                }
            }
        }
    }

    /**
     * Metodo para seleccionar la pelicula
     *
     * @param datosReservacion Vector de datos de reservacion
     */
    public static void seleccionarPelicula(int[] datosReservacion) {

        // Declaracion de variables
        int pelicula = 0;
        int dia = datosReservacion[1];
        String datosTitulo = formatearTitulo(datosReservacion);

        // Bucle principal
        while (pelicula != 999) {
            // Menu de pagina
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Menu peliculas");
            mostrarBannerPrincipal();
            mostrarNotificacion("Seleccionar la pelicula a ver");
            mostrarOpciones(peliculasDisponibles[dia - 1], vectorPeliculas);
            System.out.println("(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();
            limpiarPantalla();

            // Seleccion de opciones
            switch (input) {
                case "R" ->
                    pelicula = 999;
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        pelicula = 999;
                        pantallaPrincipal();
                    }
                }
                default -> {
                    // Validacion de datos
                    pelicula = convertirTextoNumero(input);
                    // Pagina siguiente
                    if (pelicula > peliculasDisponibles[dia - 1].length) {
                        pelicula = 0;
                        mostrarError("La opcion ingresada es incorrecta");
                    } else {
                        datosReservacion[2] = peliculasDisponibles[dia - 1][pelicula - 1] + 1;
                        seleccionarHorario(datosReservacion);
                    }
                }
            }
        }
    }

    /**
     * Metodo para seleccionar el horario de la pelicula
     *
     * @param datosReservacion Vector de datos de reservacion
     */
    public static void seleccionarHorario(int[] datosReservacion) {

        // Declaracion de variables
        int horario = 0;
        int pelicula = datosReservacion[2];
        String datosTitulo = formatearTitulo(datosReservacion);

        // Bucle principal
        while (horario != 999) {
            // Menu de pagina 
            mostrarTitulo(datosTitulo, "Menu horarios");
            mostrarBannerPrincipal();
            mostrarNotificacion("Seleccionar el horario de la pelicula");
            mostrarOpciones(horariosDisponibles[pelicula - 1], vectorHorarios);
            System.out.println("(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();
            limpiarPantalla();

            // Seleccion de opciones
            switch (input) {
                case "R" ->
                    horario = 999;
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        horario = 999;
                        pantallaPrincipal();
                    }
                }
                default -> {
                    // Validacion de datos
                    horario = convertirTextoNumero(input);
                    // Pagina siguiente
                    if (horario > horariosDisponibles[pelicula - 1].length) {
                        horario = 0;
                        mostrarError("La opcion ingresada es incorrecta");
                    } else {
                        datosReservacion[3] = horariosDisponibles[pelicula - 1][horario - 1] + 1;
                        seleccionarBoletos(datosReservacion);
                    }
                }
            }
        }
    }

    /**
     * Metodo para seleccionar el tipo y cantidad de boletos
     *
     * @param datosReservacion Vector de datos de reservacion
     */
    public static void seleccionarBoletos(int[] datosReservacion) {

        // Declaracion de variables
        int tipoBoleto = 0;
        int[] boletosReservados = {datosReservacion[4], datosReservacion[5], datosReservacion[6]};
        int totalBoletos = boletosReservados[0] + boletosReservados[1] + boletosReservados[2];
        String datosTitulo = formatearTitulo(datosReservacion);
        String[][] matrizAsientos = generarAsientos(datosReservacion);

        // Bucle principal
        while (tipoBoleto != 99) {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Menu boletos");
            mostrarSala(matrizAsientos);
            mostrarNotificacion("Indicar los boletos a comprar (Compra actual = " + totalBoletos + ")");
            System.out.println("(1) Niños        [" + boletosReservados[0] + " x ¢" + String.format("%,d", preciosBoletos[0]) + "]\n"
                    + "(2) Adulto joven [" + boletosReservados[1] + " x ¢" + String.format("%,d", preciosBoletos[1]) + "]\n"
                    + "(3) Adulto mayor [" + boletosReservados[2] + " x ¢" + String.format("%,d", preciosBoletos[2]) + "]\n\n"
                    + "(A) Seleccionar asientos\n"
                    + "(R) Regresar\n"
                    + "(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (input) {
                case "R" -> {
                    tipoBoleto = 99;
                    limpiarPantalla();
                }
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        tipoBoleto = 99;
                        pantallaPrincipal();
                    }
                }
                case "A" -> {
                    if (totalBoletos > 0) {
                        seleccionarAsientos(datosReservacion);
                    } else {
                        tipoBoleto = 0;
                        mostrarError("No ha seleccionado ningun boleto");
                    }
                }
                default -> {
                    // Validacion de datos
                    tipoBoleto = convertirTextoNumero(input);
                    if (tipoBoleto >= 1 & tipoBoleto <= 3) {
                        System.out.print("Indique la cantidad de boletos: ");
                        String input2 = sc.next();
                        int cantidad = convertirTextoNumero(input2);

                        boolean validado = validarCantidadBoletos(boletosReservados, tipoBoleto, cantidad);

                        if (validado & cantidad != 999) {
                            boletosReservados[tipoBoleto - 1] += cantidad;
                            totalBoletos += cantidad;
                            datosReservacion[4] = boletosReservados[0];
                            datosReservacion[5] = boletosReservados[1];
                            datosReservacion[6] = boletosReservados[2];
                        } else {
                            mostrarError("La cantidad ingresada es incorrecta");
                        }
                    } else {
                        mostrarError("El tipo de boleto es incorrecto");
                    }
                }
            }
        }
    }

    /**
     * Metodo para seleccionar los asientos
     *
     * @param datosReservacion Vector de datos de reservacion
     */
    public static void seleccionarAsientos(int[] datosReservacion) {
        // Declaracion de variables
        int numeroAsiento = 0;
        int fila = -1;
        int columna = -1;
        int totalBoletos = datosReservacion[4] + datosReservacion[5] + datosReservacion[6];
        int datosReserva[][] = new int[1][10];
        boolean procederGuardar = false;
        String datosTitulo = formatearTitulo(datosReservacion);
        String[][] matrizAsientos = generarAsientos(datosReservacion);
        String[][] matrizTemporal = generarAsientos(datosReservacion);

        // Bucle principal
        while (numeroAsiento != 99) {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Menu asientos");
            mostrarSala(matrizTemporal);
            mostrarNotificacion("Indicar el primero de " + totalBoletos + " asiento(s) a reservar");
            System.out.println("""
                                           Nota: Debe seleccionar el primer asiento
                                           Los demas asientos seran asignados automaticamente a la derecha

                                           (G) Guardar reservacion
                                           (R) Regresar
                                           (S) Salir
                                           """);

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (input) {
                case "R" -> {
                    numeroAsiento = 99;
                    limpiarPantalla();
                }
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        numeroAsiento = 99;
                        pantallaPrincipal();
                    }
                }
                case "G" -> {
                    if (procederGuardar) {
                        guardarReservacion(datosReservacion);
                    } else {
                        mostrarError("Debe seleccionar el asiento");
                    }
                }
                default -> {
                    // Validacion de datos
                    try {
                        fila = Arrays.asList(vectorFilas).indexOf(input.substring(0, 1));
                        columna = Arrays.asList(vectorColumnas).indexOf(input.substring(1, 3));
                    } catch (Exception E) {
                    }
                    int valido = validarAsientos(fila, columna, totalBoletos);
                    int disponible = validarDisponibilidad(fila, columna, totalBoletos, matrizAsientos);
                    if (valido == 0 & disponible == 0) {
                        datosReservacion[7] = fila;
                        datosReservacion[8] = columna;
                        datosReserva[0] = datosReservacion;
                        matrizTemporal = copiarMatriz(matrizAsientos);
                        actualizarAsientos(matrizTemporal, datosReserva, "O");
                        procederGuardar = true;
                    }
                }
            }
        }
    }

    /**
     * Metodo para registrar la reservacion
     *
     * @param datosReservacion Vector de datos de reservacion
     *
     */
    public static void guardarReservacion(int[] datosReservacion) {

        // Declaracion de variables
        int opcion = 0;
        int totalBoletos = datosReservacion[4] + datosReservacion[5] + datosReservacion[6];
        String nombreDia = obtenerNombreDia(datosReservacion);
        String nombrePelicula = obtenerNombrePelicula(datosReservacion);
        String nombreHorario = obtenerHorarioPelicula(datosReservacion);
        String datosTitulo = formatearTitulo(datosReservacion);

        // Registro de transacciones
        int posicion = obtenerSiguientePosicion(registroReservaciones);
        registroReservaciones[posicion] = datosReservacion;
        registrarTransaccion(datosReservacion, 1);

        // Actualizar grafico de asientos
        String[][] matrizAsientos = generarAsientos(datosReservacion);
        String[] asientos = obtenerNumerosAsientos(datosReservacion);

        // Bucle principal
        while (opcion != 99) {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Reservacion guardada");
            mostrarSala(matrizAsientos);
            mostrarNotificacion("Se ha reservado " + totalBoletos + " boletos");

            System.out.print(
                    "Pelicula" + nombrePelicula + "\n"
                    + "Fecha" + nombreDia + "  Hora" + nombreHorario + "\n"
                    + "Sala >> " + datosReservacion[2] + "   Asientos >> ");

            for (String asiento : asientos) {
                System.out.print("|" + asiento + "| ");
            }

            System.out.println("""


                                           (P) Procesar pago
                                           (S) Salir
                                           """);

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (input) {
                case "P" ->
                    procesarPago(posicion);
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        opcion = 99;
                        pantallaPrincipal();
                    }
                }
                default ->
                    mostrarError("La opcion ingresada es incorrecta");
            }
        }
    }

    /**
     * Metodo para procesar un pago
     *
     * @param indiceRegistro Posicion del registro a procesar
     *
     */
    public static void procesarPago(int indiceRegistro) {

        // Declaracion de variables
        int opcion = 0;
        int[] datosReservacion = registroReservaciones[indiceRegistro];
        int totalBoletos = datosReservacion[4] + datosReservacion[5] + datosReservacion[6];
        int montoTotal = (datosReservacion[4] * preciosBoletos[0]) + (datosReservacion[5] * preciosBoletos[1]) + (datosReservacion[6] * preciosBoletos[2]);
        String datosTitulo = formatearTitulo(datosReservacion);

        // Actualizar grafico de asientos
        String[][] matrizAsientos = generarAsientos(datosReservacion);
        int matrizReservacion[][] = new int[1][10];
        matrizReservacion[0] = datosReservacion;
        actualizarAsientos(matrizAsientos, matrizReservacion, "O");

        // Bucle principal
        while (opcion != 999) {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Procesar pago");
            mostrarSala(matrizAsientos);
            mostrarNotificacion("Proceder con el pago de " + totalBoletos + " boletos por un monto de ¢" + String.format("%,d", montoTotal));

            String totalNinos = String.format("%,d", datosReservacion[4] * preciosBoletos[0]);
            String totalAdultos = String.format("%,d", datosReservacion[5] * preciosBoletos[1]);
            String totalMayores = String.format("%,d", datosReservacion[6] * preciosBoletos[2]);

            System.out.println("Niños        [" + datosReservacion[4] + " x ¢" + String.format("%,d", preciosBoletos[0]) + "] = ¢" + totalNinos + "\n"
                    + "Adulto joven [" + datosReservacion[5] + " x ¢" + String.format("%,d", preciosBoletos[1]) + "] = ¢" + totalAdultos + "\n"
                    + "Adulto mayor [" + datosReservacion[6] + " x ¢" + String.format("%,d", preciosBoletos[2]) + "] = ¢" + totalMayores + "\n\n"
                    + "(P) Utilizar credito (Disponible: ¢" + String.format("%,d", creditoDisponible) + ")\n"
                    + "(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (input) {
                case "P" -> {
                    if (creditoDisponible >= montoTotal) {
                        creditoDisponible -= montoTotal;
                        finalizarPago(indiceRegistro);
                    } else {
                        mostrarError("Credito insuficiente");
                    }
                }
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        opcion = 999;
                        pantallaPrincipal();
                    }
                }
                default ->
                    mostrarError("La opcion ingresada es incorrecta");
            }
        }
    }

    /**
     * Metodo para finalizar el proceso de pago
     *
     * @param indiceRegistro Posicion del registro a procesar
     *
     */
    public static void finalizarPago(int indiceRegistro) {

        // Declaracion de variables
        int[] datosReservacion = registroReservaciones[indiceRegistro];
        String datosTitulo = formatearTitulo(datosReservacion);

        // Registrar transaccion
        registrarTransaccion(datosReservacion, 2);
        registroReservaciones = eliminarRegistro(indiceRegistro, registroReservaciones);
        registroPagos = agregarRegistro(datosReservacion, registroPagos);

        // Actualizar grafico de asientos
        String[][] butacas = generarAsientos(datosReservacion);

        // Menu de pagina 
        limpiarPantalla();
        mostrarTitulo(datosTitulo, "Finalizar pago");
        mostrarSala(butacas);
        mostrarNotificacion("Pago procesado con exito");
        System.out.println("Credito disponible: ¢" + String.format("%,d", creditoDisponible) + "\n");
        mostrarMensajeEspera();
        pantallaPrincipal();
    }

    /**
     * Metodo para verificar reservaciones registradas
     *
     */
    public static void verificar() {

        // Declaracion de variables
        int opcion = 0;

        // Bucle principal
        while (opcion != 999) {
            // Menu de pagina
            limpiarPantalla();
            mostrarTitulo("", "Verificar reservaciones");
            mostrarBannerPrincipal();
            mostrarNotificacion("Verificar reservaciones");

            // Bucle de reservaciones
            int cantidad = 0;
            for (int[] registroReservado : registroReservaciones) {
                if (registroReservado[0] == 1) {
                    String dia = obtenerNombreDia(registroReservado);
                    String pelicula = obtenerNombrePelicula(registroReservado);
                    String horario = obtenerHorarioPelicula(registroReservado);
                    String[] asientos = obtenerNumerosAsientos(registroReservado);
                    int ninos = registroReservado[4];
                    int adultos = registroReservado[5];
                    int mayores = registroReservado[6];
                    System.out.print(
                            "(" + (cantidad + 1) + ")" + dia + pelicula + horario
                            + "\n    Niños: " + ninos + " Adultos: " + adultos + " Mayores: " + mayores
                            + "\n    Asientos: ");
                    for (String asiento : asientos) {
                        System.out.print("|" + asiento + "| ");
                    }
                    System.out.println("\n");
                    cantidad++;
                }
            }

            // Captura de datos
            if (cantidad > 0) {
                System.out.print("(S) Salir al menu principal\n\n");
                System.out.print("Opcion: ");
                String input = sc.next().toUpperCase();

                // Seleccion de opciones
                if (input.equals("S")) {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        opcion = 999;
                        pantallaPrincipal();
                    }
                } else {
                    int posicion = convertirTextoNumero(input) - 1;

                    if (posicion < cantidad) {
                        procesarReservacion(posicion);
                    } else {
                        mostrarError("Opcion incorrecta");
                    }
                }
            } else {
                mostrarError("No hay reservaciones para mostrar");
                opcion = 999;
            }
        }
    }

    /**
     * Metodo para procesar una reservacion
     *
     * @param indiceRegistro Posicion del registro a procesar
     *
     */
    public static void procesarReservacion(int indiceRegistro) {
        // Declaracion de variables
        int reservacion = 0;
        int[] datosReservacion = registroReservaciones[indiceRegistro];
        String[][] matrizAsientos = generarAsientos(datosReservacion);
        String datosTitulo = formatearTitulo(datosReservacion);

        // Actualizar grafico de asientos
        int matrizReservacion[][] = new int[1][10];
        matrizReservacion[0] = datosReservacion;
        actualizarAsientos(matrizAsientos, matrizReservacion, "O");

        // Bucle principal
        while (reservacion != 999) {
            // Menu de pagina
            limpiarPantalla();
            mostrarTitulo(datosTitulo, "Modificar reservacion");
            mostrarSala(matrizAsientos);
            mostrarNotificacion("Reservacion a modificar");

            // Obtener datos de reservacion
            String dia = obtenerNombreDia(datosReservacion);
            String pelicula = obtenerNombrePelicula(datosReservacion);
            String horario = obtenerHorarioPelicula(datosReservacion);
            String asientos[] = obtenerNumerosAsientos(datosReservacion);
            int ninos = datosReservacion[4];
            int adultos = datosReservacion[5];
            int mayores = datosReservacion[6];

            System.out.print(
                    "   " + dia + pelicula + horario
                    + "\n    Niños: " + ninos + " Adultos: " + adultos + " Mayores: " + mayores
                    + "\n    Asientos: ");
            for (String asiento : asientos) {
                System.out.print("|" + asiento + "| ");
            }

            System.out.println("""


                                           Seleccione una opcion

                                           (P) Pagar reservacion
                                           (C) Cancelar reservacion
                                           (R) Regresar a la pantalla anterior
                                           (S) Salir al menu principal
                                           """);

            // Captura de datos
            System.out.print("Opcion: ");
            String input = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (input) {
                case "R" ->
                    reservacion = 999;
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?\nLos datos no guardados seran eliminados")) {
                        reservacion = 999;
                        pantallaPrincipal();
                    }
                }
                case "P" ->
                    procesarPago(indiceRegistro);
                case "C" -> {
                    if (mensajeConfirmacion("\nSeguro que desea cancelar la reservacion?\nEsta accion no puede ser revertida")) {
                        cancelarReservacion(indiceRegistro);
                    }
                }
                default ->
                    mostrarError("La opcion ingresada es incorrecta");
            }
        }
    }

    /**
     * Metodo para cancelar una reservacion
     *
     * @param indiceRegistro Posicion del registro a procesar
     *
     */
    public static void cancelarReservacion(int indiceRegistro) {
        // Declaracion de variables
        int[] datos = registroReservaciones[indiceRegistro];
        String datosTitulo = formatearTitulo(datos);

        // Registro de transacciones
        registrarTransaccion(datos, 3);
        registroReservaciones = eliminarRegistro(indiceRegistro, registroReservaciones);

        // Actualizar grafico de asientos
        String[][] asientos = generarAsientos(datos);

        // Menu de pagina
        limpiarPantalla();
        mostrarTitulo(datosTitulo, "Cancelar reservacion");
        mostrarSala(asientos);
        mostrarNotificacion("Reservacion cancelada con exito");
        mostrarMensajeEspera();
        pantallaPrincipal();
    }

    /**
     * Metodo para verificar datos administrativos
     *
     */
    public static void administrar() {
        // Declaracion de variables
        String opcion = "";
        String administrador = "Admin";
        String contrasenaAdmin = "****";

        // Bucle principal
        while (opcion != "S") {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo("", "Panel de administrador");
            mostrarBannerPrincipal();
            mostrarNotificacion("Se requiere usuario y contraseña para ingresar");
            System.out.println("(A) Acceder\n(S) Salir\n");

            // Captura de datos
            System.out.print("Opcion: ");
            opcion = sc.next().toUpperCase();

            // Seleccion de opciones
            switch (opcion) {
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?")) {
                        pantallaPrincipal();
                    }
                }
                case "A" -> {
                    System.out.print("Usuario: ");
                    String usuario = sc.next();
                    System.out.print("Contraseña: ");
                    String contrasena = sc.next();

                    if(usuario.equals(administrador) & contrasena.equals(contrasenaAdmin))
                    {
                        seleccionarReporte();
                    }
                    else
                    {
                        mostrarError("Usuario o contraseña incorrectos");
                    }
                }
                default -> {
                    mostrarError("Opcion incorrecta");
                }
            }
        }
    }

    /**
     * Metodo para seleccionar tipo de reporte
     *
     */
    public static void seleccionarReporte() {
        // Declaracion de variables
        String informe = "";
    
        // Bucle principal
        while (informe != "S") {
            // Menu de pagina 
            limpiarPantalla();
            mostrarTitulo("", "Seleccion de reporte");
            mostrarBannerPrincipal();
            System.out.println("""
                                           (1) Informe de transacciones diarias
                                           (2) Recaudacion por pelicula

                                           (S) Salir al menu principal
                                           """);
    
            // Captura de datos
            System.out.print("Opcion: ");
            informe = sc.next().toUpperCase();
            limpiarPantalla();
    
            // Seleccion de opciones
            switch (informe) {
                case "1" -> {
                    generarInformeDiario();
                }
                case "2" -> {
                    generarInformeRecaudacion();
                }
                case "S" -> {
                    if (mensajeConfirmacion("Seguro que desea salir?")) {
                        pantallaPrincipal();
                    }
                }
                default -> {
                    mostrarError("Opcion incorrecta");
                }
            }
        }
    }

    /**
     * Metodo para generar informe diario
     *
     */
    public static void generarInformeDiario() {

        int asientosTotales = 400;

        limpiarPantalla();
        mostrarTitulo("", "Informe por dia");

        for(int i = 0; i < vectorDias.length; i++)
        {
            int reservados = 0;
            int pagados = 0;
            int cancelados = 0;
            int ocupacion = 0;

            System.out.println("\n" + vectorDias[i]);

            for(int j = 0; j < registroReservaciones.length; j++)
            {
                if(registroReservaciones[j][1] == i + 1)
                {
                    reservados += registroReservaciones[j][4] + registroReservaciones[j][5] + registroReservaciones[j][6];
                }
            }

            for(int k = 0; k < registroTransacciones.length; k++)
            {
                if(registroTransacciones[k][0] == 1)
                {
                    if(registroTransacciones[k][1] == i + 1 & registroTransacciones[k][9] == 2)
                    {
                        pagados += registroTransacciones[k][4] + registroTransacciones[k][5] + registroTransacciones[k][6];
                    }
                    else if(registroTransacciones[k][1] == i + 1 & registroTransacciones[k][9] == 3)
                    {
                        cancelados += registroTransacciones[k][4] + registroTransacciones[k][5] + registroTransacciones[k][6];
                    }
                }
            }

            ocupacion = (reservados + pagados) * 100 / asientosTotales;
    
            System.out.println("\tBoletos reservados : " + reservados);
            System.out.println("\tBoletos pagados    : " + pagados);
            System.out.println("\tBoletos cancelados : " + cancelados);
            System.out.println("\tOcupacion de salas : " + ocupacion + "%");
        }
        System.out.println();
        mostrarMensajeEspera();
    }

    /**
     * Metodo para generar informe de recaudacion por pelicula
     *
     */
    public static void generarInformeRecaudacion() {
        limpiarPantalla();
        mostrarTitulo("", "Informe de recaudacion por pelicula");

        for(int i = 0; i < vectorPeliculas.length; i++)
        {
            int ninosR = 0;
            int adultosR = 0;
            int mayoresR = 0;
            int ninosP = 0;
            int adultosP = 0;
            int mayoresP = 0;
            int totalNinos = 0;
            int totalAdultos = 0;
            int totalMayores = 0;
            int sumaTotal = 0;

            for(int j = 0; j < registroReservaciones.length; j++)
            {
                if(registroReservaciones[j][2] == i + 1)
                {
                    ninosR += registroTransacciones[j][4];
                    adultosR += registroTransacciones[j][5];
                    mayoresR += registroTransacciones[j][6];
                }
            }

            for(int k = 0; k < registroTransacciones.length; k++)
            {
                if(registroTransacciones[k][0] == 1)
                {
                    if(registroTransacciones[k][2] == i + 1 & registroTransacciones[k][9] == 2)
                    {
                        ninosP += registroTransacciones[k][4];
                        adultosP += registroTransacciones[k][5];
                        mayoresP += registroTransacciones[k][6];
                    }
                }
            }

            totalNinos = (ninosR + ninosP) * preciosBoletos[0];
            totalAdultos = (adultosR + adultosP) * preciosBoletos[1];
            totalMayores = (mayoresR + mayoresP) * preciosBoletos[2];
            sumaTotal = totalNinos + totalAdultos + totalMayores;

            System.out.println("\tNiños   : " + ninosR + "(R) " + ninosP + "(P) = ¢" + String.format("%,d", totalNinos));
            System.out.println("\tAdultos : " + adultosR + "(R) " + adultosP + "(P) = ¢" + String.format("%,d", totalAdultos));
            System.out.println("\tMayores : " + mayoresR + "(R) " + mayoresP + "(P) = ¢" + String.format("%,d", totalMayores));
            System.out.println("\tTotal   : ¢" + String.format("%,d", sumaTotal));

        }

        System.out.println();
        mostrarMensajeEspera();
    }

    /**
     * Metodo para actualizar matriz de asientos marcados
     *
     * @param matrizAsientos Matriz de datos de asientos
     * @param matrizRegistros Matriz de datos registrados
     * @param marca Simbolo a aplicar al asiento
     * @return String[][] Matriz de asientos marcados
     * @exception Error por matriz de datos registrados en blanco
     *
     */
    public static String[][] actualizarAsientos(String[][] matrizAsientos, int[][] matrizRegistros, String marca) {
        try {
            for (int[] registros : matrizRegistros) {
                int cantidad = registros[4] + registros[5] + registros[6];
                int fila = registros[7];
                int columna = registros[8];
                for (int j = 0; j < cantidad; j++) {
                    matrizAsientos[fila][columna + j] = "-" + marca + "-";
                }
            }
        } catch (Exception E) {
        }
        return matrizAsientos;
    }

    /**
     * Metodo para agregar un registro a una matriz de registros
     *
     * @param datosReservacion Datos de la reservacion a agregar
     * @param matrizRegistro Matriz donde se agregaran los datos
     * @return int[][] Matriz con los datos agregados
     *
     */
    public static int[][] agregarRegistro(int[] datosReservacion, int[][] matrizRegistro) {
        // Declaracion de variables
        int posicion = obtenerSiguientePosicion(matrizRegistro);

        matrizRegistro[posicion] = datosReservacion;
        return matrizRegistro;
    }    

    /**
     * Metodo de carga de datos de prueba
     *
     */
    public static void cargarDatosPruebas() {

        int[] reservacion1 = {1, 1, 1, 1, 1, 1, 1, 0, 0, 1};
        registroReservaciones[0] = reservacion1;
        registroTransacciones[0] = reservacion1;

        int[] reservacion2 = {1, 3, 1, 1, 5, 1, 1, 5, 2, 1};
        registroReservaciones[1] = reservacion2;
        registroTransacciones[1] = reservacion2;

        int[] pago1 = {1, 1, 1, 1, 1, 1, 1, 1, 3, 2};
        registroPagos[0] = pago1;
        registroTransacciones[2] = pago1;

        int[] cancelacion1 = {1, 2, 1, 1, 1, 1, 1, 1, 3, 3};
        registroTransacciones[3] = cancelacion1;
    }

    /**
     * Metodo para convertir dato de texto a numero entero
     *
     * @param datoIngresado Dato ingresado por el usuario
     * @return int Dato en formato de numero
     *
     */
    public static int convertirTextoNumero(String datoIngresado) {
        try {
            return Integer.parseInt(datoIngresado);
        } catch (NumberFormatException E) {
            return 999;
        }
    }

    /**
     * Metodo para copiar datos de una matriz
     *
     * @param matrizOriginal Matriz de datos a copiar
     * @return String[][] Matriz de datos copiados
     *
     */
    public static String[][] copiarMatriz(String[][] matrizOriginal) {
        String[][] matrizCopia = new String[matrizOriginal.length][matrizOriginal[0].length];

        for (int i = 0; i < matrizOriginal.length; i++) {
            System.arraycopy(matrizOriginal[i], 0, matrizCopia[i], 0, matrizOriginal[0].length);
        }
        return matrizCopia;
    }

    /**
     * Metodo para eliminar un registro a una matriz de registros
     *
     * @param indiceRegistro Posicion del registro a eliminar
     * @param matrizRegistro Matriz donde se agregaran los datos
     * @return int[][] Matriz con los datos eliminados
     *
     */
    public static int[][] eliminarRegistro(int indiceRegistro, int[][] matrizRegistro) {
        // Declaracion de variables
        int[] listaBlanco = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        matrizRegistro[indiceRegistro] = listaBlanco;
        matrizRegistro = ordenarRegistros(matrizRegistro);
        return matrizRegistro;
    }

    /**
     * Metodo para dar formato al titulo de la aplicacion
     *
     * @param datosReservacion Vector de datos de la reservacion
     * @return Cadena de caracteres del titulo
     *
     */
    public static String formatearTitulo(int[] datosReservacion) {
        String nombreDia = obtenerNombreDia(datosReservacion);
        String nombrePelicula = obtenerNombrePelicula(datosReservacion);
        String nombreHorario = obtenerHorarioPelicula(datosReservacion);
        String tituloFormateado = nombreDia + nombrePelicula + nombreHorario;

        return tituloFormateado;
    }

    /**
     * Metodo para generar matriz de asientos
     *
     * @param datosReservacion Vector de datos de reservacion
     * @return String Matriz de asientos con numero
     *
     */
    public static String[][] generarAsientos(int[] datosReservacion) {
        // Declaracion de variables
        int[][] matrizReservaciones = generarRegistroDiaPeliculaHorario(datosReservacion, registroReservaciones);
        int[][] matrizPagos = generarRegistroDiaPeliculaHorario(datosReservacion, registroPagos);
        String[][] matrizAsientos = new String[vectorFilas.length][vectorColumnas.length];

        // Generacion de numeros de asiento
        for (int i = 0; i < matrizAsientos.length; i++) {
            for (int j = 0; j < matrizAsientos[0].length; j++) {
                matrizAsientos[i][j] = vectorFilas[i] + vectorColumnas[j];
            }
        }

        // Actualizacion de asientos reservados y pagados
        matrizAsientos = actualizarAsientos(matrizAsientos, matrizReservaciones, "R");
        matrizAsientos = actualizarAsientos(matrizAsientos, matrizPagos, "P");

        return matrizAsientos;
    }

    /**
     * Metodo para generar matriz de registros por dia-pelicula-horario
     *
     * @param datosReservacion Datos de la reservacion a procesar
     * @param matrizRegistro Registro a procesar
     * @return int[][] Matriz de listado de registros correspondientes al dia,
     * pelicula y horario
     *
     */
    public static int[][] generarRegistroDiaPeliculaHorario(int[] datosReservacion, int[][] matrizRegistro) {
        // Declaracion de variables
        int dia = datosReservacion[1];
        int pelicula = datosReservacion[2];
        int horario = datosReservacion[3];
        int cantidadRegistros = 0;

        // Conteo de registros validos
        for (int[] registros : matrizRegistro) {
            if (registros[0] == 1) {
                if (registros[1] == dia & registros[2] == pelicula & registros[3] == horario) {
                    cantidadRegistros++;
                }
            }
        }

        System.out.print("Cantidad de registros: " + cantidadRegistros);

        if (cantidadRegistros > 0) {
            int[][] listaRegistros = new int[cantidadRegistros][datosReservacion.length];

            // Copia de registros correspondiente al dia, pelicula y horario
            int p = 0;
            for (int[] registros : matrizRegistro) {
                if (registros[1] == dia & registros[2] == pelicula & registros[3] == horario) {
                    System.arraycopy(registros, 0, listaRegistros[p], 0, matrizRegistro[0].length);
                    p++;
                }
            }
            // Retorno de registro correspondiente al dia, pelicula y horario
            return listaRegistros;
        } else {
            // Retorno de registro en blanco
            int[][] listaRegistros = new int[0][0];
            return listaRegistros;
        }
    }

    /**
     * Metodo para limpiar datos de pantalla
     *
     * @exception Error de interrupcion
     *
     */
    public static void limpiarPantalla() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException E) {
            System.out.println(E);
        }
    }

    /**
     * Metodo para mostrar mensaje de espera
     *
     */
    public static void mostrarMensajeEspera() {
        System.out.print("Presione Enter para continuar... ");
        cs.readLine();
        limpiarPantalla();
    }

    /**
     * Metodo para mostrar mensaje de confirmacion
     *
     * @param mensaje Mensaje a mostrar
     * @return boolean Condicion de resultado
     *
     */
    public static boolean mensajeConfirmacion(String mensaje) {
        // Encabezado de pagina
        limpiarPantalla();
        mostrarTitulo("", "Ventana emergente");
        mostrarBannerAtencion();
        mostrarNotificacion(mensaje);

        // Captura de datos
        System.out.print("Presione 'Y' para confirmar: ");
        String input = sc.next().toUpperCase();

        return input.equals("Y");
    }

    /**
     * Metodo para mostrar banner de atencion
     *
     */
    public static void mostrarBannerAtencion() {
        System.out.println("""
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .         _   _                   _             _      .
                                   .        /_\\ | |_  ___  _ _   __ (_) ___  _ _  | |     .
                                   .       / _ \\|  _|/ -_)| ' \\ / _|| |/ _ \\| ' \\ |_|     .
                                   .      /_/ \\_\\\\__|\\___||_||_|\\__||_|\\___/|_||_|(_)     .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   """);

    }

    /**
     * Metodo para mostrar banner de error
     *
     */
    public static void mostrarBannerError() {
        System.out.println("""
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .              ___                      _              .
                                   .             | __| _ _  _ _  ___  _ _ | |             .
                                   .             | _| | '_|| '_|/ _ \\| '_||_|             .
                                   .             |___||_|  |_|  \\___/|_|  (_)             .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   .                                                      .
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   """);

    }

    /**
     * Metodo para mostrar banner principal
     *
     */
    public static void mostrarBannerPrincipal() {
        System.out.println("""
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   .   _____             __                   _           .
                                   .  / ___/ ___ _ ____ / /_ ___ _ ___ _ ___ ( )___       .
                                   . / /__  / _ `// __// __// _ `// _ `// _ \\|/(_-<       .
                                   . \\___/  \\_,_//_/   \\__/ \\_,_/ \\_, / \\___/ /___/       .
                                   .   __  __        _           /___/       _  __        .
                                   .  / / / / ___   (_)_  __ ___  ____ ___  (_)/ /_ __ __ .
                                   . / /_/ / / _ \\ / /| |/ // -_)/ __/(_-< / // __// // / .
                                   . \\____/ /_//_//_/ |___/ \\__//_/  /___//_/ \\__/ \\_, /  .
                                   .   _____  _                                   /___/   .
                                   .  / ___/ (_)___  ___  __ _  ___ _                     .
                                   . / /__  / // _ \\/ -_)/  ' \\/ _ `/                     .
                                   . \\___/ /_//_//_/\\__//_/_/_/\\_,_/                      .
                                   .                                                      .
                                   . . . . . . . . . . . . . . . . . . . . . . . . . . . .
                                   """);

    }

    /**
     * Metodo para mostrar un mensaje de error
     *
     * @param mensaje Mensaje a mostrar
     *
     */
    public static void mostrarError(String mensaje) {
        // Encabezado de pagina
        limpiarPantalla();
        mostrarTitulo("", "Ventana emergente");
        mostrarBannerError();
        mostrarNotificacion(mensaje);
        mostrarMensajeEspera();
    }

    /**
     * Metodo para mostrar barra de notificaciones
     *
     * @param mensaje Mensaje a mostrar
     *
     */
    public static void mostrarNotificacion(String mensaje) {
        System.out.println(mensaje + "\n");
    }

    /**
     * Metodo para mostrar opciones del menu
     *
     * @param opcionesDisponibles Vector de indices de opciones disponibles
     * @param vectorOpciones Vector de texto de opciones
     *
     */
    public static void mostrarOpciones(int[] opcionesDisponibles, String[] vectorOpciones) {
        // Bucle de impresion de opciones
        for (int i = 0; i < opcionesDisponibles.length; i++) {
            System.out.println("(" + (i + 1) + ") " + vectorOpciones[opcionesDisponibles[i]]);
        }
        System.out.println("\n(R) Regresar");
    }

    /**
     * Metodo para mostrar banner de la sala
     *
     * @param matrizAsientos Matriz de datos de asientos
     *
     */
    public static void mostrarSala(String[][] matrizAsientos) {
        // Parte superior de la sala
        System.out.print(
                ". ".repeat(28) + "\n"
                + "." + " ".repeat(2) + "-".repeat(21) + "PANTALLA" + "-".repeat(21) + " ".repeat(2) + ".\n"
                + "." + " ".repeat(54) + "." + "\n" + "." + " ".repeat(54) + "." + "\n");

        // Asientos de la sala
        for (String[] asientos : matrizAsientos) {
            System.out.print(".");
            for (int j = 0; j < matrizAsientos[0].length; j++) {
                String asiento = "|" + asientos[j] + "|";
                if (j == 1 | j == 7) {
                    asiento = "|" + asientos[j] + "|  ";
                }
                System.out.print(asiento);
            }
            System.out.print(".\n");
        }
        // Parte inferior de la sala
        System.out.print(". ".repeat(28) + "\n\n");
    }

    /**
     * Metodo para mostrar el titulo de la pantalla
     *
     * @param titulo Cadena de texto del titulo
     * @param pagina Cadena de texto de nombre de pagina
     */
    public static void mostrarTitulo(String titulo, String pagina) {
        System.out.println("Cartago's University Cinema" + titulo);
        System.out.println("\n-- " + pagina + " --");
    }

    /**
     * Metodo para obtener el horario de la pelicula
     *
     * @param datosReservacion Datos de la reservacion
     * @return String Nombre del horario
     *
     */
    public static String obtenerHorarioPelicula(int[] datosReservacion) {
        // Declaracion de variables
        int horario = datosReservacion[3];
        String nombreHorario = "";

        // Verificacion de la condicion
        if (horario > 0) {
            nombreHorario = " >> " + vectorHorarios[horario - 1];
        }
        return nombreHorario;
    }

    /**
     * Metodo para obtener el nombre del dia de la reservacion
     *
     * @param datosReservacion Datos de la reservacion
     * @return String Nombre del dia de la reservacion
     *
     */
    public static String obtenerNombreDia(int[] datosReservacion) {
        // Declaracion de variables
        int dia = datosReservacion[1];
        String nombreDia = "";

        // Verificacion de la condicion
        if (dia > 0) {
            nombreDia = " >> " + vectorDias[dia - 1];
        }
        return nombreDia;
    }

    /**
     * Metodo para obtener el nombre de la pelicula
     *
     * @param datosReservacion Datos de la reservacion
     * @return String Nombre de la pelicula
     *
     */
    public static String obtenerNombrePelicula(int[] datosReservacion) {
        // Declaracion de variables
        int pelicula = datosReservacion[2];
        String nombrePelicula = "";

        // Verificacion de la condicion
        if (pelicula > 0) {
            nombrePelicula = " >> " + vectorPeliculas[pelicula - 1];
        }
        return nombrePelicula;
    }

    /**
     * Metodo para obtener numero de asientos en formato fila-columna
     *
     * @param datosReservacion Datos de la reservacion a procesar
     * @return Stringp[] Matriz de numeros de asientos
     *
     */
    public static String[] obtenerNumerosAsientos(int[] datosReservacion) {

        // Declaracion de variables
        int cantidad = datosReservacion[4] + datosReservacion[5] + datosReservacion[6];
        int fila = datosReservacion[7];
        int columna = datosReservacion[8];
        String[] asientos = new String[cantidad];

        // Generacion de numeros de asiento
        for (int i = 0; i < cantidad; i++) {
            String asiento = vectorFilas[fila] + vectorColumnas[columna + i];
            asientos[i] = asiento;
        }
        return asientos;
    }

    /**
     * Metodo para obtener la primera posicion libre de un registro
     *
     * @param matrizRegistro Registro a verificar
     * @return int Primera posicion libre del registro
     *
     */
    public static int obtenerSiguientePosicion(int[][] matrizRegistro) {
        // Declaracion de variables
        int posicion = 0;

        // Bucle principal
        for (int[] registro1 : matrizRegistro) {
            if (registro1[0] == 1) {
                posicion++;
            }
        }
        return posicion;
    }

    /**
     * Metodo para ordenar un registro eliminando filas en blanco
     *
     * @param registroOriginal Registro original a ordenar
     * @return int[][] Registro copia ordenado
     *
     */
    public static int[][] ordenarRegistros(int[][] registroOriginal) {

        // Declaracion de variables
        int indice = 0;
        int[][] registroOrdenado = new int[registroOriginal.length][registroOriginal[0].length];

        // Copia del registro
        for (int[] registro1 : registroOriginal) {
            if (registro1[0] == 1) {
                System.arraycopy(registro1, 0, registroOrdenado[indice], 0, registroOriginal[0].length);
                indice++;
            }
        }
        return registroOrdenado;
    }

    /**
     * Metodo para registrar una transaccion
     *
     * @param datosReservacion Datos de la reservacion a registrar
     * @param tipoTransaccion Tipo de transaccion a registrar
     *
     */
    public static void registrarTransaccion(int[] datosReservacion, int tipoTransaccion) {
        int posicion = obtenerSiguientePosicion(registroTransacciones);
        mostrarNotificacion("posicion" + posicion);
        System.arraycopy(datosReservacion, 0, registroTransacciones[posicion], 0, datosReservacion.length);
        registroTransacciones[posicion][datosReservacion.length - 1] = tipoTransaccion;
    }

    /**
     * Metodo de validacion de numeros de asientos
     *
     * @param fila Fila del asiento selecionado
     * @param columna Columna del asiento seleccionado
     * @param cantidad Cantidad de asientos en la transaccion
     * @return int Codigo de verificacion de asientos
     *
     */
    public static int validarAsientos(int fila, int columna, int cantidad) {
        // Asientos que no pertenecen a la sala
        if (fila == -1 | columna == -1) {
            mostrarError("Asiento fuera del rango");
            return 1;
            // Asientos que se sale del rango disponible
        } else if ((columna + cantidad) > vectorColumnas.length) {
            mostrarError("Seleccion fuera de rango");
            return 2;
        }
        // Asientos validos
        return 0;
    }

    /**
     * Metodo para validar cantidad de boletos a reservar
     *
     * @param listaBoletos Lista de boletos adquiridos
     * @param tipoBoleto Tipo de boleto de la transaccion
     * @param cantidadTransaccion Cantidad de boletos a reservar
     * @return boolean Validacion de transaccion
     *
     */
    public static boolean validarCantidadBoletos(int[] listaBoletos, int tipoBoleto, int cantidadTransaccion) {
        return listaBoletos[tipoBoleto - 1] + cantidadTransaccion >= 0;
    }

    /**
     * Metodo para generar fechas de reservacion
     *
     */
    public static void cargarFechas() {
        for (int i = 0; i < vectorDias.length; i++) {
            LocalDate fechaActal = LocalDate.now();
            String fechaCargar = LocalDate.parse(fechaActal.toString()).plusDays(i).toString();
            vectorDias[i] = fechaCargar;
        }
    }

    /**
     * Metodo de validacion de disponibilidad de asientos
     *
     * @param fila Fila del asiento selecionado
     * @param columna Columna del asiento seleccionado
     * @param cantidad Cantidad de asientos en la transaccion
     * @param matrizAsientos Matriz de asientos segun dia-pelicula-horario
     * @exception Error de matriz de asientos vacia
     * @return int Codigo de verificacion de disponibilidad
     *
     */
    public static int validarDisponibilidad(int fila, int columna, int cantidad, String[][] matrizAsientos) {
        try {
            for (int i = 0; i < cantidad; i++) {
                // Verificacion de asientos reservados o pagados
                if (matrizAsientos[fila][columna + i].equals("-R-") | matrizAsientos[fila][columna + i].equals("-P-")) {
                    mostrarError("No es posible reservar los asientos seleccionados");
                    return 1;
                }
            }
        } catch (Exception E) {
            return 1;
        }
        // Asientos disponibles
        return 0;
    }
}  