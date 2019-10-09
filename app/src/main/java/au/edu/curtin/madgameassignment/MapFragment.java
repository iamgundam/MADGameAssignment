package au.edu.curtin.madgameassignment;

import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

public class MapFragment extends Fragment
{
    private Selector selector;
    private Settings settings;
    private int choice;

    public static final int BUILD = 0;
    public static final int DEMOLISH = 1;
    public static final int INFO = 2;

    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        settings = new Settings();
        settings.load(getActivity());
        choice = BUILD; //Default choice

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_map, ui, false);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(
                getActivity(),
                settings.getMapH(),
                GridLayoutManager.HORIZONTAL,
                false
        ));

        //Ready data
        MapData map = MapData.get();

        //Restart the game if settings have been changed.
        if(map.hasUpdate())
        {
            map.changeMapSize(settings.getMapH(), settings.getMapW());
        }

        AdapterMap adapter = new AdapterMap();

        rv.setAdapter(adapter);

        return view;
    }

    private class AdapterMap extends RecyclerView.Adapter<MapViewHolder>
    {
        private MapData data;

        public AdapterMap()
        {
            super();
            data = MapData.get();
        }

        @Override
        public int getItemCount()
        {
            return settings.getMapH() * settings.getMapW();
        }

        @Override
        public MapViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(MapViewHolder vh, int index)
        {
            int row = index % settings.getMapH();
            int col = index / settings.getMapH();
            vh.bind(data, row, col);
        }
    }

    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView structure;

        public MapViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));


            int size = parent.getMeasuredHeight() / settings.getMapH() +1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
        }

        public void bind(MapData data, int row, int col)
        {
            Structure building;
            final int frow = row;
            final int fcol = col;

            structure = (ImageView)itemView.findViewById(R.id.structure);

            //Sets the appropriate structure to this grid cell.
            building = data.get(row, col).getStructure();
            if(building != null) //If structure found, display its image.
            {
                structure.setImageResource(building.getDrawableId());
            }
            else //No structure found, so draw nothing.
            {
                structure.setImageResource(StructureData.DRAWABLES[0]);
            }

            //On click behaviour
            structure.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {

                    switch(choice)
                    {
                        case BUILD:
                            build();
                        break;

                        case DEMOLISH:
                            demolish();
                        break;
                    }

                }

                //Adds the currently selected structure to the map if valid.
                public void build()
                {
                    Structure selected;
                    MapData data = MapData.get();
                    MapElement current = data.get(frow, fcol);

                    //Default selection is build, have to check if structure has been selected
                    //Must also check if existing structure on tile, can only build on empty space
                    selected = selector.getSelected();
                    if(selected != null && (current.getStructure() == null))
                    {
                        //If structure to be built is not a road, check for adjacent road
                        if(!(selected instanceof Road))
                        {
                            if (hasAdjacentRoad())
                            {
                                //Update grid image to structure, then update map[][].
                                structure.setImageResource(selected.getDrawableId());
                                current.setStructure(selected);
                            }
                        }
                        //Else, if there is no structure currently here, build the road.
                        else if(current.getStructure() == null)
                        {
                            structure.setImageResource(selected.getDrawableId());
                            current.setStructure(selected);
                        }
                    }
                }//end build

                //Demolishes any building or any road with no buildings adjacent.
                public void demolish()
                {
                    Structure selected;
                    MapData data = MapData.get();
                    MapElement current = data.get(frow, fcol);

                    if(current.getStructure() != null)
                    {
                        //If road is to be deleted, check for adjacent buildings
                        if(current.getStructure() instanceof Road)
                        {
                            if(!hasAdjacentBuilding())
                            {
                                //Update grid image to structure, then update map[][].
                                structure.setImageResource(StructureData.DRAWABLES[0]);
                                current.setStructure(null);
                            }
                            //Else, do not delete.
                        }
                        else //Simply delete the building.
                        {
                            structure.setImageResource(StructureData.DRAWABLES[0]);
                            current.setStructure(null);
                        }

                    }
                }

                //Check whether the surrounding tiles have a road.
                public boolean hasAdjacentRoad()
                {
                    MapData data = MapData.get();
                    MapElement adj;
                    boolean valid;

                    valid = false;
                    try //Check right.
                    {
                        //Valid if road structure found above this tile
                        adj = data.get(frow, fcol+1);
                        if(adj.getStructure() != null && adj.getStructure() instanceof Road)
                        {
                            valid = true;
                        }
                    }
                    catch (ArrayIndexOutOfBoundsException e)
                    {
                        //Do nothing and try other tiles.
                    }
                    if(!valid)
                    {
                        try //Check left.
                        {
                            adj = data.get(frow, fcol-1);
                            if (adj.getStructure() != null && adj.getStructure() instanceof Road)
                            {
                                valid = true;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            //Do nothing and try other tiles.
                        }
                    }
                    if(!valid)
                    {
                        try //Check above.
                        {
                            adj = data.get(frow -1, fcol);
                            if (adj.getStructure() != null && adj.getStructure() instanceof Road)
                            {
                                valid = true;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            //Do nothing and try other tiles.
                        }
                    }
                    if(!valid)
                    {
                        try //Check below.
                        {
                            adj = data.get(frow +1, fcol);
                            if (adj.getStructure() != null && adj.getStructure() instanceof Road)
                            {
                                valid = true;
                            }
                        }
                        catch (ArrayIndexOutOfBoundsException e)
                        {
                            //Do nothing.
                        }
                    }
                    return valid;
                }//end hasAdjacentRoad

                //Check whether the surrounding tiles have a Commercial or Residential building.
                public boolean hasAdjacentBuilding()
                {
                    MapData data = MapData.get();
                    MapElement adj;
                    boolean valid;

                    valid = false;
                     try //Check right.
                     {
                         //Valid if either building type found above this tile
                         adj = data.get(frow, fcol+1);
                         if(adj.getStructure() != null &&
                                 (adj.getStructure() instanceof Commercial ||
                                  adj.getStructure() instanceof Residential))
                            {
                                valid = true;
                            }
                     }
                     catch (ArrayIndexOutOfBoundsException e)
                     {
                         //Do nothing and try other tiles.
                     }
                     if(!valid)
                     {
                         try //Check left.
                         {
                             adj = data.get(frow, fcol-1);
                             if(adj.getStructure() != null &&
                                     (adj.getStructure() instanceof Commercial ||
                                      adj.getStructure() instanceof Residential))
                             {
                                    valid = true;
                             }
                         }
                         catch (ArrayIndexOutOfBoundsException e)
                         {
                             //Do nothing and try other tiles.
                         }
                     }
                     if(!valid)
                     {
                         try //Check above.
                         {
                                adj = data.get(frow -1, fcol);
                                if(adj.getStructure() != null &&
                                        (adj.getStructure() instanceof Commercial ||
                                         adj.getStructure() instanceof Residential))
                                {
                                    valid = true;
                                }
                            }
                            catch (ArrayIndexOutOfBoundsException e)
                            {
                                //Do nothing and try other tiles.
                            }
                        }
                     if(!valid)
                     {
                         try //Check below.
                         {
                             adj = data.get(frow +1, fcol);
                             if(adj.getStructure() != null &&
                                     (adj.getStructure() instanceof Commercial ||
                                      adj.getStructure() instanceof Residential))
                             {
                                 valid = true;
                             }
                         }
                         catch (ArrayIndexOutOfBoundsException e)
                         {
                             //Do nothing.
                         }
                     }
                    return valid;
                }//end hasAdjacentBuilding

            });//end onClickListener

        }

    }

    public void setSelector(Selector selector)
    {
        this.selector = selector;
    }

    public int getChoice()
    {
        return choice;
    }

    public void setChoice(int choice)
    {
        this.choice = choice;
    }
}
