package au.edu.curtin.madgameassignment;

public class GameData
{
    private int height;
    private int width;
    private MapElement[][] grid;

    //Game stats
    private int time;
    private int money;
    private int nResidential;
    private int nCommercial;
    private boolean isOver; //True or false for "game over" state

    private static GameData instance = null;

    public static GameData get()
    {
        if(instance == null)
        {
            instance = new GameData(10, 50, 1000);
        }
        return instance;
    }

    private GameData(int h, int w, int inMoney)
    {
        grid = new MapElement[h][w];

        //Fill new map with empty grass blocks
        for(int ii=0; ii<h; ii++)
        {
            for(int jj=0; jj<w; jj++)
            {
                grid[ii][jj] = new MapElement(null, null);
            }
        }

        //Set class fields
        height = h;
        width = w;
        money = inMoney;
        nResidential = 0;
        nCommercial = 0;
        isOver = false;

        instance = this;
    }

    public void restartGame(int h, int w, int inMoney)
    {
        instance = new GameData(h, w, inMoney);
    }

    //Getters and setters --------------------------------------------------------------------------

    public MapElement get(int i, int j)
            throws ArrayIndexOutOfBoundsException
    {
        return grid[i][j];
    }

    public int getTime()
    {
        return time;
    }

    public void setTime(int time)
    {
        this.time = time;
    }

    public int getMoney()
    {
        return instance.money;
    }

    public void setMoney(int money)
    {
        instance.money = money;
    }

    public int getnCommercial()
    {
        return nCommercial;
    }

    public void setnCommercial(int nCommercial)
    {
        this.nCommercial = nCommercial;
    }

    public int getnResidential()
    {
        return nResidential;
    }

    public void setnResidential(int nResidential)
    {
        this.nResidential = nResidential;
    }

    public boolean isOver()
    {
        return isOver;
    }

    public void setOver(boolean over)
    {
        isOver = over;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}

