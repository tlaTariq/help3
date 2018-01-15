package com.example.talha.fragments;

/**
 * Created by talha on 1/4/2018.
 */

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

        /*((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(
                "Chats");*/
        img = (ImageView) view.findViewById(R.id.conv_pic);
        name = (TextView) view.findViewById(R.id.conv_name);
        msg = (TextView) view.findViewById(R.id.conv_msg);
        number = (TextView) view.findViewById(R.id.conv_number);
        convListView = (ListView) view.findViewById(R.id.convListView);

        //convListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        //convListView.setStackFromBottom(true);

        ConversationItem item1 = new ConversationItem(); item1.setName("Ali"); item1.setMessage("Message1.."); item1.setPhone("03134206141");
        ConversationItem item2 = new ConversationItem(); item2.setName("Ahmad"); item2.setMessage("Message2.."); item2.setPhone("03134206141");
        ConversationItem item3 = new ConversationItem(); item3.setName("Basit"); item3.setMessage("Message3.."); item3.setPhone("03134206141");
        ConversationItem item4 = new ConversationItem(); item4.setName("Malik"); item4.setMessage("Message4.."); item4.setPhone("03134206141");



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

            ConversationItem item1 = new ConversationItem(); item1.setName("Ali"); item1.setMessage("Message1.."); item1.setPhone("03134206141");
            ConversationItem item2 = new ConversationItem(); item2.setName("Ahmad"); item2.setMessage("Message2.."); item2.setPhone("03134206141");
            ConversationItem item3 = new ConversationItem(); item3.setName("Basit"); item3.setMessage("Message3.."); item3.setPhone("03134206141");
            ConversationItem item4 = new ConversationItem(); item4.setName("Malik"); item4.setMessage("Message4.."); item4.setPhone("03134206141");
//
           // db.addConvo(item1); db.addConvo(item2); db.addConvo(item3); db.addConvo(item4);

            convlist = db.getAllConversations();
            conv_numbers_list = db.getAllConvoNumbers();
//            convlist = new ArrayList<ConversationItem>();
//            convlist.add(item1); convlist.add(item2); convlist.add(item3); convlist.add(item4);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            convAdapter = new ConversationAdapter(convlist, getActivity());
            convListView.setAdapter(convAdapter);

            // Select item on listclick

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }



}
