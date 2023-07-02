import java.util.*;

public class Butacas {

	// Método principal
	public static void main(String[] args)
	{
    String filas[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    String columnas[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10"};
    String butacas[][] = new String[10][10];
    String butaca = "";
  
    System.out.print(". ".repeat(31) + "\n" + 
    "." + " ".repeat(2) + "-".repeat(21) + "PANTALLA" + "-".repeat(21) + " ".repeat(7) + ".\n" + 
    "." + " ".repeat(59) + "." + "\n" + "." + " ".repeat(54) + ".    ." + "\n");
    
    for(int i = 0; i < butacas.length; i++)
    {
    	for(int j = 0; j < butacas[0].length; j++)
        {
        	butacas[i][j] = filas[i] + columnas[j];
        }
    }
    
    for(int i = 0; i < butacas.length; i++)
    {
    	System.out.print(".");
    	for(int j = 0; j < butacas[0].length; j++)
        {
        	butaca = "|" + butacas[i][j] + "|";
        	if(j == 1 | j== 7)
            {
            	butaca = "|" + butacas[i][j] + "|  ";
            }
        	System.out.print(butaca);
        }
        System.out.print(".    .");
        System.out.println();
    }
    System.out.print(". ".repeat(31) + "\n");
	}
}

