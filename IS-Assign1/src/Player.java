import java.util.ArrayList;

public class Player
{
    public ArrayList<Game> allGame = new ArrayList<Game>();
    public static Game game;

    public static void main (String[] arg)
    {
        read(arg[0]);
        if (game.getAl() != 'A' && 
            game.getAl() != 'B' && 
            game.getAl() != 'D' &&
            game.getAl() != 'U')
        {
            System.out.println("Invalid input.");
        }
        else
        {
            cal(arg[1]);
        }
    }

    private static void read(String input)
    {
        game = new Game(input);
    }

    private static void cal(String output)
    {
        game.path(output);
    }

}
