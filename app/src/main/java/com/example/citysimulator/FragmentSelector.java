package com.example.citysimulator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentSelector extends Fragment
{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_selector, container, false);

        RecyclerView rv = view.findViewById(R.id.selectorRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,
                                                                            false));
        rv.setHasFixedSize(true);
        FragmentSelector.MyAdapter adapter = new FragmentSelector.MyAdapter(
                                                                    StructureData.getInstance());
        rv.setAdapter(adapter);

        return view;
    }

    private class MyAdapter extends RecyclerView.Adapter<FragmentSelector.MyDataVHolder>
    {
        private StructureData data;

        public MyAdapter(StructureData data)
        {
            this.data = data;
        }

        @NonNull
        @Override
        public FragmentSelector.MyDataVHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            FragmentSelector.MyDataVHolder xx = new FragmentSelector.MyDataVHolder(li, parent);
            return  xx;
        }

        @Override
        public void onBindViewHolder(@NonNull FragmentSelector.MyDataVHolder holder, int position)
        {
            holder.bind(data, position);
        }

        @Override
        public int getItemCount()
        {
            return StructureData.getInstance().size();
        }
    }

    private class MyDataVHolder extends RecyclerView.ViewHolder
    {

        public MyDataVHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_selection, parent, false));
        }
        public void bind(final StructureData data, final int position)
        {
            GameData game = GameData.getInstance();
            ImageView image = itemView.findViewById(R.id.structureImage);
            image.setImageResource(data.get(position).getImageID());
            TextView cost = itemView.findViewById(R.id.cost);
            if(data.get(position) instanceof Residential)
            {
                cost.setText("$" + game.getSettings().getHouseBuildingCost());
            }
            else if(data.get(position) instanceof Commercial)
            {
                cost.setText("$" + game.getSettings().getCommBuildingCost());
            }
            else if(data.get(position) instanceof Road)
            {
                cost.setText("$" + game.getSettings().getRoadBuildingCost());
            }

            View v = itemView.findViewById(R.id.structureImage);

            v.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    GameData game = GameData.getInstance();

                    game.setCurrentSelection(data.get(position));
                }
            });
        }
    }
}
