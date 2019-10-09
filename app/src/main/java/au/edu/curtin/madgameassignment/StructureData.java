package au.edu.curtin.madgameassignment;

import java.util.Arrays;
import java.util.List;

/**
 * Sourced from MAD practical 03, with minor changes to available structures and their labels.
 * -------------------------------------------------------------------------------------------------
 *
 * Stores the list of possible structures. This has a static get() method for retrieving an
 * instance, rather than calling the constructor directly.
 *
 * The remaining methods -- get(int), size(), add(Structure) and remove(int) -- provide
 * minimalistic list functionality.
 *
 * There is a static int array called DRAWABLES, which stores all the drawable integer references,
 * some of which are not actually used (yet) in a Structure object.
 */
public class StructureData
{
    public static final int[] DRAWABLES = {
            0, // No structure
            R.drawable.ic_building1, R.drawable.ic_building8,
            R.drawable.ic_road_ns, R.drawable.ic_road_ew, R.drawable.ic_road_nsew,
            R.drawable.ic_road_ne, R.drawable.ic_road_nw, R.drawable.ic_road_se, R.drawable.ic_road_sw,
            R.drawable.ic_road_n, R.drawable.ic_road_e, R.drawable.ic_road_s, R.drawable.ic_road_w,
            R.drawable.ic_road_nse, R.drawable.ic_road_nsw, R.drawable.ic_road_new, R.drawable.ic_road_sew
    };

    private List<Structure> structureList = Arrays.asList(new Structure[] {
            new Residential(R.drawable.ic_building1, "Residential"),
            new Commercial(R.drawable.ic_building8, "Commercial"),
            new Road(R.drawable.ic_road_ns, "Road"),
            new Road(R.drawable.ic_road_ew, "Road"),
            new Road(R.drawable.ic_road_nsew, "Road"),
            new Road(R.drawable.ic_road_ne, "Road"),
            new Road(R.drawable.ic_road_nw, "Road"),
            new Road(R.drawable.ic_road_se, "Road"),
            new Road(R.drawable.ic_road_sw, "Road"),
            new Road(R.drawable.ic_road_n, "Road"),
            new Road(R.drawable.ic_road_e, "Road"),
            new Road(R.drawable.ic_road_s, "Road"),
            new Road(R.drawable.ic_road_w, "Road"),
            new Road(R.drawable.ic_road_nse, "Road"),
            new Road(R.drawable.ic_road_nsw, "Road"),
            new Road(R.drawable.ic_road_new, "Road"),
            new Road(R.drawable.ic_road_sew, "Road"),
    });

    private static StructureData instance = null;


    public static StructureData get()
    {
        if(instance == null)
        {
            instance = new StructureData();
        }
        return instance;
    }

    protected StructureData() {}

    public Structure get(int i)
    {
        return structureList.get(i);
    }

    public int size()
    {
        return structureList.size();
    }

    public void add(Structure s)
    {
        structureList.add(0, s);
    }

    public void remove(int i)
    {
        structureList.remove(i);
    }
}

