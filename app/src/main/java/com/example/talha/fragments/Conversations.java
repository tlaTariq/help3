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
import com.example.talha.help3.ChatDisplay;
import com.example.talha.help3.ConversationAdapter;
import com.example.talha.help3.ConversationItem;
import com.example.talha.help3.R;

import java.util.ArrayList;
import java.util.List;

public class Conversations extends Fragment {

    private ImageView img;
    private TextView name, msg, number;

    public static List<ConversationItem> convlist;
    public static List<String> conv_numbers_list;
    public static ConversationAdapter convAdapter;
    ListView convListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conv_layout, container, false);


        img = (ImageView) view.findViewById(R.id.conv_pic);
        name = (TextView) view.findViewById(R.id.conv_name);
        msg = (TextView) view.findViewById(R.id.conv_msg);
        number = (TextView) view.findViewById(R.id.conv_number);
        convListView = (ListView) view.findViewById(R.id.convListView);

        convlist = new ArrayList<ConversationItem>();

        Conversations.LoadConversations loadContact = new Conversations.LoadConversations();
        loadContact.execute();

        convListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ChatDisplay.class);
                //i.putExtra("Name", etName.getText().toString());
                //i.putExtra("isEntered", true);
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });

        convListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getActivity(), ChatDisplay.class);

                ConversationItem item = convlist.get(i);
                intent.putExtra("Name", item.getName().toString());
                intent.putExtra("Number", item.getPhone().toString());
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });


        return view;
    }

    public class LoadConversations extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            DatabaseHandler db = new DatabaseHandler(getContext());

            convlist = db.getAllConversations();
            conv_numbers_list = db.getAllConvoNumbers();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            convAdapter = new ConversationAdapter(convlist, getActivity());
            convListView.setAdapter(convAdapter);

            // Select item on listclick
            registerForContextMenu(convListView);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.convListView) {
            AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Select any option");
            String[] menuItems = {"delete" , "add to favourite"};
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
