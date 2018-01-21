package com.example.talha.help3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.talha.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by talha on 1/8/2018.
 */

public class FavMsgDisplay extends AppCompatActivity {


    public static ArrayList<ChatMessage> chatlist;
    public static FavMsgAdapter chatAdapter;
    ListView msgListView;

    public String name;
    public String number;

    public List<FavouriteItem> updateFavData = new ArrayList<FavouriteItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fav_msg_display_layout);

        name = getIntent().getExtras().getString("Name");
        number = getIntent().getExtras().getString("Number");

        getSupportActionBar().setTitle(name);
        //getSupportActionBar().setSubtitle(number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        msgListView = (ListView) findViewById(R.id.favMsgDisplay_msgListView);


        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<ChatMessage>();

        LoadMessages loadMsg = new LoadMessages();
        loadMsg.execute();


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat_display, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.settings_mcd:
                //startActivity(new Intent(this, About.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class LoadMessages extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Message list for this person

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            chatlist = db.getAllFavMessagesForSpecificPerson(number);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            chatAdapter = new FavMsgAdapter(getApplicationContext(), chatlist);

            msgListView.setAdapter(chatAdapter);

            //registerForContextMenu(msgListView);
        }
    }




}