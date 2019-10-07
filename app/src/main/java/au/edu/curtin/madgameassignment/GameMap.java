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
import android.widget.ImageView;

public class GameMap extends Fragment
{
    Selector selector;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_map, ui, false);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(
                getActivity(),
                MapData.HEIGHT,
                GridLayoutManager.HORIZONTAL,
                false
        ));

        //Ready data
        MapData map = MapData.get();

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
            return data.HEIGHT * data.WIDTH;
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
            int row = index % MapData.HEIGHT;
            int col = index / MapData.HEIGHT;
            vh.bind(data, row, col);
        }
    }

    private class MapViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView northwest;
        private ImageView northeast;
        private ImageView southeast;
        private ImageView southwest;
        private ImageView structure;

        public MapViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.grid_cell, parent, false));

            northwest = (ImageView)itemView.findViewById(R.id.northwest);
            northeast = (ImageView)itemView.findViewById(R.id.northeast);
            southeast = (ImageView)itemView.findViewById(R.id.southeast);
            southwest = (ImageView)itemView.findViewById(R.id.southwest);
            structure = (ImageView)itemView.findViewById(R.id.structure);

            int size = parent.getMeasuredHeight() / MapData.HEIGHT +1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
        }

        public void bind(MapData data, int row, int col)
        {
            Structure building;
            final int frow = row;
            final int fcol = col;

            northwest.setImageResource(data.get(row, col).getNorthWest());
            northeast.setImageResource(data.get(row, col).getNorthEast());
            southwest.setImageResource(data.get(row, col).getSouthWest());
            southeast.setImageResource(data.get(row, col).getSouthEast());

            building = data.get(row, col).getStructure();
            if(building != null)
            {
                structure.setImageResource(building.getDrawableId());
            }
            else
            {
                structure.setImageResource(StructureData.DRAWABLES[0]);

            }

            structure.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    MapData data = MapData.get();
                    Structure selected;

                    selected = selector.getSelected();

                    if(selected != null)
                    {
                        structure.setImageResource(selected.getDrawableId());
                        data.get(frow, fcol).setStructure(selector.getSelected());
                    }

                }
            });

        }

    }

    public void setSelector(Selector in)
    {
        selector = in;
    }
}
