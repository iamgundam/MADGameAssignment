package au.edu.curtin.madgameassignment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Selector extends Fragment
{
    Structure selected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup ui, Bundle bundle)
    {
        View view = inflater.inflate(R.layout.fragment_selector, ui, false);

        RecyclerView rv = (RecyclerView)view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        //Ready data
        StructureData data = StructureData.get();

        AdapterSelector adapter = new AdapterSelector();

        rv.setAdapter(adapter);

        return view;
    }

    private class AdapterSelector extends RecyclerView.Adapter<SelectorViewHolder>
    {
        StructureData data;

        public AdapterSelector()
        {
            super();
            data = StructureData.get();
        }

        @Override
        public int getItemCount()
        {
            return data.size();
        }

        @Override
        public SelectorViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new SelectorViewHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(SelectorViewHolder vh, int index)
        {
            vh.bind(index);
        }
    }

    private class SelectorViewHolder extends RecyclerView.ViewHolder
    {
        ImageView building;
        TextView buildingName;
        StructureData data;

        public SelectorViewHolder(LayoutInflater li, ViewGroup parent)
        {
            super(li.inflate(R.layout.list_selection, parent, false));
            data = StructureData.get();
            building = (ImageView)itemView.findViewById(R.id.structure);
            buildingName = (TextView)itemView.findViewById(R.id.structureName);
        }

        public void bind(int index)
        {
            final int i = index;

            building.setImageResource(data.get(index).getDrawableId());
            buildingName.setText(data.get(index).getLabel());

            //Listens for clicks and sets current structure to place
            building.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    selected = data.get(i);
                }
            });
        }
    }

    public Structure getSelected()
    {
        return selected;
    }
}
