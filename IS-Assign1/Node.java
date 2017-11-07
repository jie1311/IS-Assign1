public class Node
{
    private Map map;
    private Robbie robbie;
    private int g;
    private int h;
    private int f;
    //g means the cost; h means the heuristic function; and f only means g + h
    //in UCS, f function is not the f here
    private int depth;
    private String id;
    private String trace;
    private String lastMove;
    private Node lastNode;
    //lastNode stores the last node(parent node)
    //lastMove stores the last movement from last node

    public Node (Map map, int[] robCod, int g)
    {
        this.map = map;
        robbie = new Robbie(robCod[0], robCod[1]);
        this.g = g;
        refreshH();
        f = g + h;
        depth = 0;
        id = "" + robCod[0] + "," + robCod[1];
        trace = "";
    }

    public Node (Map map, int[] robCod, int g, String move, Node node)
    {
        this.map = map;
        robbie = new Robbie(robCod[0], robCod[1]);
        this.g = g;
        refreshH();
        f = g + h;        
        id = "" + robCod[0] + "," + robCod[1];
        trace = "";
        lastMove = move;
        lastNode = node; 
        depth = lastNode.depth + 1;
    }

    private void refreshH()
    {
        //calculate the heuristic function
        int[] goal =  map.getGoal();
        int[] robCod = robbie.getCod();
        int ver = Math.abs(goal[0] - robCod[0]);
        int hoz = Math.abs(goal[1] - robCod[1]);
        int dia = Math.min(ver, hoz);
        int str = Math.abs(ver - hoz);
        h = robbie.getNCost()*str + robbie.getDCost()*dia; 
    }
    
    private void refreshTrace()
    {
        trace = "";
    }

    public Map getMap()
    {
        return map;
    }

    public Robbie getRobbie()
    {
        return robbie;
    }

    public int getG()
    {
        return g;
    }

    public int getH()
    {
        return h;
    }
    
    public int getF()
    {
        return f;
    } 

    public String getId()
    {
        return id;
    }

    public int getDepth()
    {
        return depth;
    }
    
    public Node getLastNode()
    {
        return lastNode;
    }
    
    public String trace()
    {
        //use recursion to calculate the trace, lastNode stores the last node(parent node)
        trace = "";
        
        if(lastMove != null)
            trace = trace + lastMove;
        
        try
        {
            if (lastNode.lastNode != null)
                trace = trace + "-" + lastNode.trace();
        }
        catch(Exception e)
        {
        }
        
        return trace;
    }
}
