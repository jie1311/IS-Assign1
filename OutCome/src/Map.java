import java.util.Arrays;

public class Map
{
    private int size;
    private char[][] matrix;

    public Map()
    {
        size = 3;
        matrix = new char[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 'R';
    }

    public Map(int size)
    {
        this.size = size;
        matrix = new char[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                matrix[i][j] = 'R';
    }
    
    public char[][] getMap()
    {
        return matrix;
    }
    
    public char getUnit(int i, int j)
    {
        return matrix[i][j];
    }
    
    public void setUnit(int i, int j, char type)
    {
        matrix[i][j] = type;
    }
    
    public int[] getGoal()
    {
        int[] coord = new int[2];
        for (int j = 0; j < size; j++)

            for (int i = 0; i < size; i++)
            {
                char type = getUnit(i, j);
                if (type == 'G')
                {
                    coord[0] = i;
                    coord[1] = j;
                    break;
                }
            }
        return coord;
    }
    
    public int[] getStart()
    {
        int[] coord = new int[2];
        for (int j = 0; j < size; j++)
            for (int i = 0; i < size; i++)
            {
                char type = getUnit(i, j);
                if (type == 'S')
                {
                    coord[0] = i;
                    coord[1] = j;
                    break;
                }
            }
        return coord;
    }
    
    public int getSize()
    {
        return size;
    }
}
