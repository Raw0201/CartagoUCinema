import java.util.*;

public class SalasCine {

    //dia-pelicula-horario-butacas

	// Método principal
	public static void main(String[] args)
	{
        String[] dias = {"día1", "día2", "día3", "día4"};
        String[] peliculas = {"peli1", "peli2", "peli3", "peli4"};
        String[] horarios = {"horario1", "horario2", "horario3", "horario4"};
        String[] filas = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] columnas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
        String[][][][][] salas = new String[dias.length][peliculas.length][horarios.length][filas.length][columnas.length];

        for(int d = 0; d < dias.length; d++)
        {
          for(int p = 0; p < peliculas.length; p++)
          {
            for(int h = 0; h < horarios.length; h++)
            {
              for(int i = 0; i < salas[0][0][0].length; i++)
              {
                for(int j = 0; j < salas[0][0][0][0].length; j++)
                {
                  salas[d][p][h][i][j] = filas[i] + columnas[j];
                }
              }
            }
          }
        }

        for(int d = 0; d < dias.length; d++)
        {
          for(int p = 0; p < peliculas.length; p++)
          {
            for(int h = 0; h < horarios.length; h++)
            {
              System.out.println(dias[d] + "/" + peliculas[p] + "/" + horarios[h]);
              for(int i = 0; i < salas[0][0][0].length; i++)
              {
                for(int j = 0; j < salas[0][0][0][0].length; j++)
                {
                  System.out.print(salas[d][p][h][i][j]);
                }
                System.out.println();
              }
              System.out.println();
            }
          }
        }
	}
}

