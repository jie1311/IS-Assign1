import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Writer
{
    public Writer()
    {
    }
    
    public static void writeFile(String fileName, ArrayList<String> content)
    {
        try
        {
            PrintWriter outputFile = new PrintWriter(fileName);
            for (int i = 0; i < content.size(); i++)
                outputFile.println(content.get(i));
            outputFile.close();
        }

        catch (IOException exception)
        {
            System.out.println();
        }
    }
}
