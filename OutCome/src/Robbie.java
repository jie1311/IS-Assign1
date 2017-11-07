import java.util.Arrays;

public class Robbie
{
    private int[] coord;
    private int nCost;
    private int dCost;
    
    public Robbie()
    {
        coord = new int[2];
        coord[0] = 0;
        coord[1] = 0;
        nCost = 2;
        dCost = 1;
    }
    
    public Robbie(int i, int j)
    {
        coord = new int[2];
        coord[0] = i;
        coord[1] = j;
        nCost = 2;
        dCost = 1;
    }
    
    public void setCod(int i, int j)
    {
        coord[0] = i;
        coord[1] = j;
    }
    
    public int[] getCod()
    {
        return coord;
    }
    
    public int getNCost()
    {
        return nCost;
    }
    
    public int getDCost()
    {
        return dCost;
    }
}
