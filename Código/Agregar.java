import java.util.*;

public class Agregar
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

    public static void main(String[] args) 
    {
        int arr[][] = {{0, 0, 0, 0}, {1, 3, 2, 1}, {0, 0, 0, 0}, {3, 2 ,3 ,2}, {2, 3, 1 ,2}};
        int tem[][] = new int[arr.length][arr[0].length];
        
        System.out.println("Array original");
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(Arrays.toString(arr[i]));
        }
        
        int index = 0;
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i][0] > 0)
            {
                for(int j = 0; j < arr[0].length; j++)
                {
                    tem[index][j] = arr[i][j];
                    arr[i][j] = 0;
                    
                }
                index++;
            }
        }
    
        for(int i = 0; i < tem.length; i++)
        {
            for(int j = 0; j < tem[0].length; j++)
            {
                arr[i][j] = tem[i][j];
            }
        }
        
        System.out.println();
        System.out.println("Array original depurada");
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(Arrays.toString(arr[i]));
        }
        
        int last = 1;
        int pos = 0;
        while(last != 0)
        {
            last = arr[pos][0];
            pos++;
        }
        System.out.println();
        System.out.println("Ultima posicion: " + pos);
        
        int datos[] = {5, 4, 3, 2};
        for(int i = 0; i < arr[0].length; i++)
        {
            arr[pos-1][i] = datos[i];
        }
        
        System.out.println();
        System.out.println("Array original agregada");
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(Arrays.toString(arr[i]));
        }
        
        
        last = 1;
        pos = 0;
        while(last != 0)
        {
            last = arr[pos][0];
            pos++;
        }
        System.out.println();
        System.out.println("Ultima posicion: " + pos);
        
        int datos2[] = {7, 7, 7, 7};
        for(int i = 0; i < arr[0].length; i++)
        {
            arr[pos-1][i] = datos2[i];
        }
        
        System.out.println();
        System.out.println("Array original agregada");
        for(int i = 0; i < arr.length; i++)
        {
            System.out.println(Arrays.toString(arr[i]));
        }
    }
}

