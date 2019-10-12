package au.edu.curtin.madgameassignment;

public class GameData
{
    private int height;
    private int width;
    private MapElement[][] grid;
    private int money;
    private boolean update; //Set by SettingsActivity when an update to map is required

    private static GameData instance = null;
    private Settings settings;

    public static GameData get()
    {
        if(instance == null)
        {
            instance = new GameData(10, 50, 1000);
        }
        return instance;
    }

    private GameData(int h, int w, int money)
    {
        update = false;
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
        this.money = money;
    }

    public void restartGame(int h, int w, int money)
    {
        instance = new GameData(h, w, money);
    }

    public MapElement get(int i, int j)
            throws ArrayIndexOutOfBoundsException
    {
        return grid[i][j];
    }

    public boolean hasUpdate()
    {
        return update;
    }

    public void setUpdate(boolean update)
    {
        this.update = update;
    }

    public int getMoney()
    {
        return money;
    }

    public void setMoney(int money)
    {
        this.money = money;
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

