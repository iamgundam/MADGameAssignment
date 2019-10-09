package au.edu.curtin.madgameassignment;

public class MapData
{
    private MapElement[][] grid;
    private boolean update; //Set by SettingsActivity when an update to map is required

    private static MapData instance = null;

    public static MapData get()
    {
        if(instance == null)
        {
            instance = new MapData(10, 50);
        }
        return instance;
    }

    private MapData(int h, int w)
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
    }

    public void changeMapSize(int h, int w)
    {
        instance = new MapData(h, w);
    }

    public MapElement get(int i, int j)
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

}

