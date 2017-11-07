import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Game
{
    private Map map;
    private Robbie robbie;
    private int depth;
    private char al;
    private ArrayList<Node> avbList;
    private ArrayList<Node> clsList;

    public Game()
    {
        ArrayList input = Reader.readFile("input/inputDFS.txt");
        map = Reader.buildMap(input);
        robbie = Reader.buildRobbie(input);
        al = Reader.getAl(input);
        depth = Reader.getDepth(input);
        int[] robCod = robbie.getCod();
        Node sNode = new Node(map, robCod, 0);
        avbList = new ArrayList<Node>();
        clsList = new ArrayList<Node>();
        addAvbNode(sNode);
    }

    public Game(String fileName)
    {
        ArrayList input = Reader.readFile(fileName);
        map = Reader.buildMap(input);
        robbie = Reader.buildRobbie(input);
        al = Reader.getAl(input);
        depth = Reader.getDepth(input);
        int[] robCod = robbie.getCod();
        Node sNode = new Node(map, robCod, 0);
        avbList = new ArrayList<Node>();
        clsList = new ArrayList<Node>();
        addAvbNode(sNode);
    }

    public char getAl()
    {
        return al;
    }

    public void path(String output)
    {
        int index = 0;
        //index = 0 means it is still finding for path
        //index = 1 means the path has been found
        //index = 2 means no path has been found

        while(index == 0)
        {
            index = path2nd(output);
        }

        if (index == 2)
        {
            //System.out.println("NO PATH.");
            ArrayList<String> outList = new ArrayList<String>();
            outList.add("NO PATH. ");
            Writer.writeFile(output, outList);
        }
    }

    private int path2nd(String output)
    {
        int index = 0;
        //index = 0 means it is still finding for path
        //index = 1 means the path has been found
        //index = 2 means no path has been found
        
        if (avbList.size() > 0)
        {
            ArrayList<Node> opnList = reOrder(avbList, al);
            int count = 0;
            //count means count the nodes has been opened before
            
            for (int i = 0; i < opnList.size(); i++)
            {
                boolean open = true;
                //check if the node has been opened before, 
                //and if the open list all were opened, return no path index(2)
                if (clsList.size() > 0)
                {
                    for (int j = 0; j < clsList.size(); j++)
                    {
                        if (clsList.get(j).getId().equals(opnList.get(i).getId()))
                        {
                            open = false;
                            count++;
                        }
                    }
                }

                if(open)
                {
                    int[] goal = map.getGoal();
                    int[] robCod = opnList.get(i).getRobbie().getCod();
                    //check if succeeded
                    if (robCod[0] == goal[0] && robCod[1] == goal[1])
                    {
                        clsList.add(opnList.get(i));
                        output(output);
                        index = 1;
                        break;
                    }
                    else
                    {
                        clsList.add(opnList.get(i));
                        ArrayList<Node> newList = checkNodes(opnList.get(i));
                        for(int r = 0; r < newList.size(); r++)
                        {
                            addAvbNode(newList.get(r));
                        }
                        break;
                    }
                }
            }
            
            if (count == opnList.size())
            {
                index = 2;
            }
        }
        else
        {
            index = 2;
        }
        return index;
    }

    private void output(String output)
    {
        ArrayList<String> outList = new ArrayList<String>();
        Node lastNode = clsList.get(clsList.size() - 1);
        String reverse = new StringBuffer(lastNode.trace()).reverse().toString();

        //System.out.println("S-" + reverse + "-G " + lastNode.getG());
        outList.add("S-" + reverse + "-G " + lastNode.getG());

        for(int x = 0; x < depth; x++)
        {
            String reverse2 = new StringBuffer(clsList.get(x).trace()).reverse().toString();
            if(reverse2.length() != 0)
            {
                //System.out.println("S-" + reverse2 + " " + outGHF(clsList.get(x), al));
                outList.add("S-" + reverse2 + " " + outGHF(clsList.get(x)));
            }
            else
            {
                //System.out.println("S" + reverse2 + " " + outGHF(clsList.get(x), al));
                outList.add("S" + reverse2 + " " + outGHF(clsList.get(x)));
            }

            String openList = "OPEN ";
            ArrayList<Node> oooList = checkNodes(clsList.get(x));
            if(oooList.size() > 0)
            {
                for(int r = 0; r < oooList.size(); r++)
                {
                    if (oooList.get(r).getLastNode().getLastNode() != null)
                    {
                        if (!oooList.get(r).getId().equals(oooList.get(r).getLastNode().getLastNode().getId()))
                        {
                            String reverse3 = new StringBuffer(oooList.get(r).trace()).reverse().toString();
                            if(reverse3.length() != 0)
                            {
                                openList = openList + "S-" + reverse3 + " ";
                            }
                        }
                    }
                    else
                    {
                        String reverse3 = new StringBuffer(oooList.get(r).trace()).reverse().toString();
                        if(reverse3.length() != 0)
                        {
                            openList = openList + "S-" + reverse3 + " ";
                        }
                    }
                }
            }
            //System.out.println(openList);
            outList.add(openList);

            String closeList = "CLOSED ";
            for(int y = 0; y <= x ; y++)
            {
                String reverse4 = new StringBuffer(clsList.get(y).trace()).reverse().toString();
                if(reverse4.length() != 0)
                {
                    closeList = closeList + "S-" + reverse4 + " ";
                }
                else
                {
                    closeList = closeList + "S ";
                }
            }
            //System.out.println(closeList);
            outList.add(closeList);
        }

        Writer.writeFile(output, outList);
    }

    private String outGHF(Node curNode)
    {
        String returnS = "";
        switch (al)
        {
            case 'A' :
            returnS = curNode.getG() + " " + curNode.getH() + " " + curNode.getF();
            break;

            case 'B' :
            returnS = curNode.getDepth() + " 0 " + curNode.getDepth();
            break;

            case 'D' :
            returnS = curNode.getDepth() + " 0 " + curNode.getDepth();
            break;

            case 'U' :
            returnS = "" + curNode.getG() + " " + curNode.getH() + " " + curNode.getF();
            break;
        }
        return returnS;
    }

    private ArrayList<Node> reOrder(ArrayList<Node> curList, char al)
    {
        ArrayList<Node> newList = new ArrayList<Node>();
        for (int i = 0; i < curList.size(); i++)
        {
            newList.add(curList.get(i));
        }

        switch (al)
        {
            case 'A' :
            //open list in A star should be listed with f = g + h
            if (newList.size() > 1)
            {
                for (int i = 0; i < newList.size() - 1; i++)
                {
                    for (int j = i + 1; j < newList.size(); j++)
                    {
                        if (newList.get(i).getF() > newList.get(j).getF())
                        {
                            Node buffer = new Node(map, robbie.getCod(), 0);
                            buffer = newList.get(i);
                            newList.set(i, newList.get(j));
                            newList.set(j, buffer);
                        }
                    }
                }
            }
            break;

            case 'B' :
            //open list in BFS do not need to reorder
            break;

            case 'D' :
            //open list in DFS should be listed with depth
            if (newList.size() > 1)
            {
                for (int i = 0; i < newList.size() - 1; i++)
                {
                    for (int j = i + 1; j < newList.size(); j++)
                    {
                        if (newList.get(i).getDepth() < newList.get(j).getDepth())
                        {
                            Node buffer = new Node(map, robbie.getCod(), 0);
                            buffer = newList.get(i);
                            newList.set(i, newList.get(j));
                            newList.set(j, buffer);
                        }
                    }
                }
            }

            break;

            case 'U' :
            //open list in UCS should be listed with f = g
            if (newList.size() > 1)
            {
                for (int i = 0; i < newList.size() - 1; i++)
                {
                    for (int j = i + 1; j < newList.size(); j++)
                    {
                        if (newList.get(i).getG() > newList.get(j).getG())
                        {
                            Node buffer = new Node(map, robbie.getCod(), 0);
                            buffer = newList.get(i);
                            newList.set(i, newList.get(j));
                            newList.set(j, buffer);
                        }
                    }
                }
            }
            break;
        }

        return newList;
    }

    private void addAvbNode(Node curNode)
    {
        //check if the node has been opened before add
        if (clsList.size() > 0)
        {
            for (int i = 0; i < clsList.size(); i++)
            {
                if (clsList.get(i).getId().equals(curNode.getId()))
                {
                    break;
                }
                else
                {
                    avbList.add(curNode);
                }
            }
        }
        else
        {
            avbList.add(curNode);
        }
    }

    private ArrayList<Node> checkNodes(Node curNode)
    {
        ArrayList<Node> newList = new ArrayList<Node>();
        int[] robCod = curNode.getRobbie().getCod();
        int g = curNode.getG();

        //right
        if (robCod[0] < map.getSize() -1)
        {
            if (map.getUnit(robCod[0] + 1, robCod[1]) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] + 1;
                newCod[1] = robCod[1];
                Node node = new Node(map, newCod, g + robbie.getNCost(), "R", curNode);
                newList.add(node);
            }
        }

        //downright
        if (robCod[0] < map.getSize() -1 && robCod[1] < map.getSize() -1)
        {
            if (map.getUnit(robCod[0] + 1, robCod[1]) != 'X' 
            && map.getUnit(robCod[0] + 1, robCod[1] + 1) != 'X' 
            && map.getUnit(robCod[0], robCod[1] + 1) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] + 1;
                newCod[1] = robCod[1] + 1;
                Node node = new Node(map, newCod, g + robbie.getDCost(), "RD", curNode);
                newList.add(node);
            }
        }

        //down
        if (robCod[1] < map.getSize() -1)
        {
            if (map.getUnit(robCod[0], robCod[1] + 1) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0];
                newCod[1] = robCod[1] + 1;
                Node node = new Node(map, newCod, g + robbie.getNCost(), "D", curNode);
                newList.add(node);
            }
        }

        //downleft
        if (robCod[0] > 0 && robCod[1] < map.getSize() -1)
        {
            if (map.getUnit(robCod[0] - 1, robCod[1]) != 'X' 
            && map.getUnit(robCod[0] - 1, robCod[1] + 1) != 'X' 
            && map.getUnit(robCod[0], robCod[1] +1) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] - 1;
                newCod[1] = robCod[1] + 1;
                Node node = new Node(map, newCod, g + robbie.getDCost(),"LD", curNode);
                newList.add(node);
            }
        }

        //left
        if (robCod[0] > 0)
        {
            if (map.getUnit(robCod[0] - 1, robCod[1]) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] -1;
                newCod[1] = robCod[1];
                Node node = new Node(map, newCod, g + robbie.getNCost(), "L", curNode);
                newList.add(node);
            }
        }

        //upleft
        if (robCod[0] > 0 && robCod[1] > 0)
        {
            if (map.getUnit(robCod[0], robCod[1] - 1) != 'X' 
            && map.getUnit(robCod[0] - 1, robCod[1] -1) != 'X' 
            && map.getUnit(robCod[0] - 1, robCod[1]) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] - 1;
                newCod[1] = robCod[1] - 1;
                Node node = new Node(map, newCod, g + robbie.getDCost(), "LU", curNode);
                newList.add(node);
            }
        }

        //up
        if (robCod[1] > 0)
        {
            if (map.getUnit(robCod[0], robCod[1] - 1) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0];
                newCod[1] = robCod[1] - 1;
                Node node = new Node(map, newCod, g + robbie.getNCost(), "U", curNode);
                newList.add(node);
            }
        }

        //upright
        if (robCod[0] < map.getSize() -1 && robCod[1] > 0)
        {
            if (map.getUnit(robCod[0], robCod[1] - 1) != 'X' 
            && map.getUnit(robCod[0] + 1, robCod[1] - 1) != 'X' 
            && map.getUnit(robCod[0] + 1, robCod[1]) != 'X')
            {
                int[] newCod = new int[2];
                newCod[0] = robCod[0] + 1;
                newCod[1] = robCod[1] - 1;
                Node node = new Node(map, newCod, g + robbie.getDCost(), "RU", curNode);
                newList.add(node);
            }
        }

        return newList;
    }
}
