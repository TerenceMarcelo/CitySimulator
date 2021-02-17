package com.example.citysimulator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class FragmentMap extends Fragment
{

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        RecyclerView rv = view.findViewById(R.id.mapRecyclerView);
        rv.setLayoutManager(new GridLayoutManager(getActivity(), GameData.getInstance()
                .getSettings().getMapHeight(), GridLayoutManager.HORIZONTAL, false));
        MapAdapter adapter = new MapAdapter(MapData.getInstance(GameData.getInstance().
                getSettings().getMapWidth(), GameData.getInstance().getSettings().getMapHeight()));
        rv.setAdapter(adapter);

        return view;
    }

    class MapAdapter extends RecyclerView.Adapter<MapDataVHolder>
    {
        private MapData data;

        public MapAdapter(MapData data)
        {
            this.data = data;
        }

        @NonNull
        @Override
        public MapDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater li = LayoutInflater.from(getActivity());
            return new MapDataVHolder(li, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MapDataVHolder holder, int position)
        {
            holder.bind(data, position, this);
        }

        @Override
        public int getItemCount()
        {
            return GameData.getInstance().getSettings().getMapWidth() *
                                                GameData.getInstance().getSettings().getMapHeight();
        }
    }

    static class MapDataVHolder extends RecyclerView.ViewHolder
    {
        public MapDataVHolder(LayoutInflater inflater, ViewGroup inParent)
        {
            super(inflater.inflate(R.layout.grid_cell, inParent, false));
            int size = inParent.getMeasuredHeight() /
                                            GameData.getInstance().getSettings().getMapHeight() + 1;
            ViewGroup.LayoutParams lp = itemView.getLayoutParams();
            lp.width = size;
            lp.height = size;
        }
        public void bind(MapData data, final int position, final MapAdapter adapter)
        {

            GameData game = GameData.getInstance();
            MapElement[][] map = game.getMap();
            MapElement thisElement = map
                                [position % game.getSettings().getMapHeight()]
                                [position / game.getSettings().getMapHeight()];
            ImageView image1 = itemView.findViewById(R.id.grass);
            ImageView image2 = itemView.findViewById(R.id.structure);
            image1.setImageResource(thisElement.getImageID());

            if(thisElement.getImage() != null)
            {
                image2.setImageBitmap(thisElement.getImage());
            }
            else if(thisElement.getStructure() != null)
            {
                image2.setImageResource(thisElement.getStructure().getImageID());
            }
            else
            {
                image2.setImageResource(android.R.color.transparent);
            }

            View v1 = itemView.findViewById(R.id.grass);

            v1.setOnClickListener(new View.OnClickListener()
            {
                //User clicked on a square on the map
                @Override
                public void onClick(View view)
                {
                    GameData game = GameData.getInstance();
                    MapElement[][] map = game.getMap();
                    ImageView image2 = itemView.findViewById(R.id.structure);
                    MapElement selectedSquare = map[position % game.getSettings().getMapHeight()]
                            [position / game.getSettings().getMapHeight()];

                    //If the user selected a structure from the selector
                    if(game.getCurrentSelection() != null)
                    {
                        //Check that there isn't already a structure there
                        if(selectedSquare.getStructure() == null)
                        {
                            //If it's a road, can be placed anywhere
                            if(game.getCurrentSelection() instanceof Road)
                            {
                                selectedSquare.setStructure(game.getCurrentSelection());
                                if(selectedSquare.getImage() != null)
                                {
                                    image2.setImageBitmap(selectedSquare.getImage());
                                }
                                else
                                {
                                    image2.setImageResource(selectedSquare.getStructure().getImageID());
                                }

                                DBModel database = game.getDatabase();

                                DBContainer2 toAdd = new DBContainer2((position % game.getSettings().getMapHeight()),
                                        (position / game.getSettings().getMapHeight()), game.getCurrentSelection().getImageID(), (int) System.currentTimeMillis(), 3);
                                //Add new road to database
                                database.addData2(toAdd);
                                //Add new road to our GameData singleton as well
                                game.addStructure(game.getCurrentSelection());
                            }
                            else  //If it's a building, and there isn't a structure already there
                            {
                                //Check if there is a road in its surrounding blocks
                                if((position % game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapHeight() &&
                                   (position / game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapWidth())
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) + 1]
                                           [(position / game.getSettings().getMapHeight()) + 1].getStructure() instanceof Road)
                                    {
                                        //If there is a road, allow it to be placed
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                //Same as above, for all if statements below.
                                if((position % game.getSettings().getMapHeight()) - 1 > 0 &&
                                        (position / game.getSettings().getMapHeight()) - 1 >= 0)
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) - 1]
                                            [(position / game.getSettings().getMapHeight()) - 1].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position % game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapHeight() &&
                                        (position / game.getSettings().getMapHeight()) - 1 >= 0)
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) + 1]
                                            [(position / game.getSettings().getMapHeight()) - 1].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position % game.getSettings().getMapHeight()) - 1 >= 0 &&
                                        (position / game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapWidth())
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) - 1]
                                            [(position / game.getSettings().getMapHeight()) + 1].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position % game.getSettings().getMapHeight()) - 1 >= 0)
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) - 1]
                                            [(position / game.getSettings().getMapHeight())].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position % game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapHeight())
                                {
                                    if (map[(position % game.getSettings().getMapHeight()) + 1]
                                            [(position / game.getSettings().getMapHeight())].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position / game.getSettings().getMapHeight()) + 1 < game.getSettings().getMapWidth())
                                {
                                    if (map[(position % game.getSettings().getMapHeight())]
                                            [(position / game.getSettings().getMapHeight()) + 1].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }
                                if((position / game.getSettings().getMapHeight()) - 1 >= 0)
                                {
                                    if (map[(position % game.getSettings().getMapHeight())]
                                            [(position / game.getSettings().getMapHeight()) - 1].getStructure() instanceof Road)
                                    {
                                        selectedSquare.setBuildingBuildable(true);
                                    }
                                }

                                //If it is adjacent to a road and there isn't a structure already there.
                                if(selectedSquare.isBuildingBuildable())
                                {
                                    selectedSquare.setStructure(game.getCurrentSelection());
                                    image2.setImageResource(selectedSquare.getStructure().getImageID());

                                    //Add new building to database
                                    DBModel database = game.getDatabase();
                                    if (game.getCurrentSelection() instanceof Residential) {
                                        database.addData2(new DBContainer2((position % game.getSettings().getMapHeight()),
                                                (position / game.getSettings().getMapHeight()), game.getCurrentSelection().getImageID(), (int) System.currentTimeMillis(), 1));
                                    } else if (game.getCurrentSelection() instanceof Commercial) {
                                        database.addData2(new DBContainer2((position % game.getSettings().getMapHeight()),
                                                (position / game.getSettings().getMapHeight()), game.getCurrentSelection().getImageID(), (int) System.currentTimeMillis(), 2));
                                    }
                                    //Add new road to our GameData singleton as well
                                    game.addStructure(game.getCurrentSelection());
                                }
                            }
                        }
                        adapter.notifyItemChanged(getAdapterPosition());
                        game.setCurrentSelection(null);
                    }
                    else if(game.demolishButton())
                    {
                        //If the user had selected the 'demolish' button
                        image2.setImageResource(android.R.color.transparent);

                        DBModel database = game.getDatabase();
                        database.removeData2(selectedSquare.getID());

                        game.removeStructure(selectedSquare.getStructure());
                        selectedSquare.setStructure(null);
                        selectedSquare.setImage(null);
                        selectedSquare.setName(null);

                        game.setDemolishButton(false);
                        adapter.notifyItemChanged(getAdapterPosition());
                    }
                    else if(game.detailsButton())
                    {
                        //If the user had selected the 'details' button
                        if (selectedSquare.getStructure() != null)
                        {
                            game.setDetailsButton(false);
                            Intent intent = new Intent(itemView.getContext(), ScreenDetails.class);
                            intent.putExtra("x", position % game.getSettings().getMapHeight());
                            intent.putExtra("y", position / game.getSettings().getMapHeight());
                            if(selectedSquare.getStructure() instanceof Residential)
                            {
                                intent.putExtra("structureType", 1);
                            }
                            else if(selectedSquare.getStructure() instanceof Commercial)
                            {
                                intent.putExtra("structureType", 2);
                            }
                            else
                            {
                                intent.putExtra("structureType", 3);
                            }
                            itemView.getContext().startActivity(intent);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
