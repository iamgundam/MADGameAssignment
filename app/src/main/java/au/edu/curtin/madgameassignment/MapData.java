package au.edu.curtin.madgameassignment;

public class MapData
{
    private MapElement[][] grid;

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
}

