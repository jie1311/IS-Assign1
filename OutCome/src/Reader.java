import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Reader
{
    
    public Reader()
    {
    }

    public static ArrayList readFile(String fileName)
    {
        String input = "unknow";
        ArrayList<String> inputList = new ArrayList<String>();
        try
        {
            FileReader inputFile = new FileReader(fileName);
            Scanner parser = new Scanner(inputFile);
            while (parser.hasNextLine())
            {
                input = parser.nextLine();
                inputList.add(input);
            }
            inputFile.close();
        }    
        catch (FileNotFoundException exception)
        {
            //System.out.println("Can't find input file(s).");
            //System.out.println("Please check the file(s).");
        }
        catch (IOException exception)
        {
            System.out.println("Unexpect exception happened. Please try again.");
        }
        return inputList;
    }
    
    public static Map buildMap(ArrayList input)
    {
        int size = Integer.parseInt("" + input.get(2));
        Map newMap = new Map(size);
        
        //set map units according to the ArrayList
        for (int j = 0; j < size; j++)
            for (int i = 0; i < size; i++)
            {
                char type = ("" +input.get(3 + j)).charAt(i);
                newMap.setUnit(i, j, type);
            }
            
        return newMap;
    }
    
    public static Robbie buildRobbie(ArrayList input)
    {
        int size = Integer.parseInt("" + input.get(2));
        int codI = 0;
        int codJ = 0;
        
        //set robbie coordinate according to the ArrayList
        for (int j = 0; j < size; j++)
            for (int i = 0; i < size; i++)
            {
                char type = ("" +input.get(3 + j)).charAt(i);
                if (type == 'S')
                {
                    codI = i;
                    codJ = j;
                    break;
                }
            }
            
        Robbie newRobbie = new Robbie(codI, codJ);
        return newRobbie;
    }
    
    public static int getDepth(ArrayList input)
    {
        return Integer.parseInt("" + input.get(1));
    }
    
    public static char getAl(ArrayList input)
    {
        return ("" + input.get(0)).charAt(0);
    }
    
    
}
