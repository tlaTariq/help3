package com.example.talha.fragments;

/**
 * Created by talha on 1/4/2018.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.talha.database.DatabaseHandler;
import com.example.talha.help3.FavMsgDisplay;
import com.example.talha.help3.FavouriteAdapter;
import com.example.talha.help3.FavouriteItem;
import com.example.talha.help3.R;

import java.util.ArrayList;
import java.util.List;

public class Favourites extends Fragment {

    private ImageView img;
    private TextView name, msg, number;

    public static List<FavouriteItem> favlist;
    public static List<String> fav_numbers_list;
    public static FavouriteAdapter favAdapter;
    ListView favListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fav_layout, container, false);

        img = (ImageView) view.findViewById(R.id.fav_pic);
        name = (TextView) view.findViewById(R.id.fav_name);
        msg = (TextView) view.findViewById(R.id.fav_msg);
        number = (TextView) view.findViewById(R.id.fav_number);
        favListView = (ListView) view.findViewById(R.id.favListView);

        favlist = new ArrayList<FavouriteItem>();

        Favourites.LoadFavourites loadContact = new Favourites.LoadFavourites();
        loadContact.execute();

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), FavMsgDisplay.class);
                //i.putExtra("Name", etName.getText().toString());
                //i.putExtra("isEntered", true);
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });

        favListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), FavMsgDisplay.class);

                FavouriteItem item = favlist.get(i);
                intent.putExtra("Name", item.getName().toString());
                intent.putExtra("Number", item.getPhone().toString());
                //intent.putExtra("CallFrom", "Fragments");
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    public class LoadFavourites extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            DatabaseHandler db = new DatabaseHandler(getContext());

            favlist = db.getAllFavs();
            fav_numbers_list = db.getAllFavNumbers();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            favAdapter = new FavouriteAdapter(favlist, getActivity());
            favListView.setAdapter(favAdapter);

            // Select item on listclick
            registerForContextMenu(favListView);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.favListView) {
            AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Select any option");
            String[] menuItems = {"delete"};
            for (int i = 0; i< menuItems.length; i++)
            {
                menu.add(menu.NONE, i,i,menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int menuItemIndex = item.getItemId();
        if(menuItemIndex == 0)
        {
        }
        return true;
    }

}
