import java.util.*;

public class Reservas
{
    public static String[] dias = {"dia1", "dia2", "dia3", "dia4"};
    public static String[] peliculas = { "Pelicula 1", "Pelicula 2", "Pelicula 3", "Pelicula 4", "Pelicula 5" };  
    public static String[] horarios = { "Horario 1", "Horario 2", "Horario 3", "Horario 4", "Horario 5", "Horario 6", "Horario 7", "Horario 8" };
    public static int[] dias_disponibles = { 0, 1, 2, 3 };
    public static int[][] peliculas_cartelera = { { 0, 1, 2, 3 }, { 0, 1, 2, 3 }, { 1, 2, 3, 4 }, { 1, 2, 3, 4 } };
    public static int[][] horarios_peliculas = { { 1, 2, 3, 4 }, { 4, 5, 6, 7 }, { 2, 3, 4, 5 }, { 0, 1, 2, 3 }, { 5, 6, 7, 8 } };
    public static String[] filas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    public static String[] columnas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
    public static int reservas[][] = new int[100][9];

    public static void main(String[] args) {
  
        int dia = 1;
        int pelicula = 1;
        int horario = 1;
        int fila = 0;
        int columna = 0;
    
        int boletos[] = {1, 2, 3};
     
        for(int i = 0; i < 4; i++)
        {
             reservas[i][0] = 1;
             reservas[i][1] = dias_disponibles[i];
             reservas[i][2] = pelicula;
             reservas[i][3] = horario;
             reservas[i][4] = boletos[0];
             reservas[i][5] = boletos[1];
             reservas[i][6] = boletos[2];
             reservas[i][7] = i;
             reservas[i][8] = i;  
        }
       
        reservas[6][0] = 1;
        reservas[6][1] = 3;
       
        Arrays.sort(reservas);
       
        for(int i = 0; i < reservas.length; i++)
        {
        	if(reservas[i][0] == 1)
            {
              String diap = dias[reservas[i][1]];
              String pelp = peliculas[reservas[i][2]];
              String horp = horarios[reservas[i][3]];
              String ninp = "Ninos = " + reservas[i][4];
              String jovp = " Joven = " + reservas[i][5];
              String mayp = " Mayor = " + reservas[i][6];
              String filp = "Fila = " + reservas[i][7];
              String colp = " Columna = " + reservas[i][8];
              System.out.println(i);
              System.out.println(diap + pelp + horp);
              System.out.println(ninp + jovp + mayp);
              System.out.println(filp + colp);
            }
        }
    }
}

