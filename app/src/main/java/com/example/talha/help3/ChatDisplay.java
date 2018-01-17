package com.example.talha.help3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.talha.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.talha.fragments.Conversations.convAdapter;
import static com.example.talha.help3.MainActivity.CurrentTabPosition;

/**
 * Created by talha on 1/8/2018.
 */

public class ChatDisplay extends AppCompatActivity implements View.OnClickListener {

    private EditText msg_edittext;
    private String user1 = "khushi", user2 = "khushi1";
    private Random random;
    public static ArrayList<ChatMessage> chatlist;
    public static ArrayList<ChatMessage> chatlist1;
    public static ChatAdapter chatAdapter;
    ListView msgListView;

    public String name;
    public String number;

    public ChatMessage chatMessageTobe;
    public ConversationItem convoItemTobe;
    public List<ConversationItem> updateConvData = new ArrayList<ConversationItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        name = getIntent().getExtras().getString("Name");
        number = getIntent().getExtras().getString("Number");

        random = new Random();

        getSupportActionBar().setTitle(name);
        //getSupportActionBar().setSubtitle(number);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //SearchView search = (SearchView) findViewById(R.id.searchViewcl);

        msg_edittext = (EditText) findViewById(R.id.messageEditText);
        msgListView = (ListView) findViewById(R.id.msgListView);
        ImageButton sendButton = (ImageButton) findViewById(R.id.sendMessageButton);
        sendButton.setOnClickListener(this);

        // ----Set autoscroll of listview when a new message arrives----//
        msgListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgListView.setStackFromBottom(true);

        chatlist = new ArrayList<ChatMessage>();
        chatlist1 = new ArrayList<ChatMessage>();

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

            chatlist = db.getAllMessagesForSpecificPerson(number);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            chatAdapter = new ChatAdapter(getApplicationContext(), chatlist);

            msgListView.setAdapter(chatAdapter);

            registerForContextMenu(msgListView);
        }
    }

    class AddMessagesToDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Message list for this person

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

             db.addMsg(chatMessageTobe);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }

    class AddConvoToDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Message list for this person

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());


//            for(ConversationItem item : conv_data)
//            {
//                if( item.getPhone().equals(convoItemTobe.getPhone()) )
//                {
//                    conv_data.remove(item);
//                    db.deleteContact(item);
//                    break;
//                }
//            }

            //db.addConvo(convoItemTobe);
            db.smartAddConvo(convoItemTobe);

            updateConvData = db.getAllConversations();
            //mSectionsPagerAdapter = new MainActivity.SectionsPagerAdapter(getSupportFragmentManager());



            return null;
        }


        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //convAdapter.updateData(updateConvData);
            //mViewPager.setAdapter(mSectionsPagerAdapter);
            //tabLayout.setupWithViewPager(mViewPager);

        }
    }

    class DeleteMessages extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Message list for this person

            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            db.deleteMessage(chatMessageTobe);
            chatAdapter.remove(chatMessageTobe);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            chatAdapter.notifyDataSetChanged();

        }
    }

    public void sendTextMessage(View v) {

        String message = msg_edittext.getEditableText().toString();

        if (!message.equalsIgnoreCase("")) {
            chatMessageTobe = new ChatMessage(1, number, message, true);
            //final ChatMessage chatMessage2 = new ChatMessage("1", number, message, false);
            //chatMessage.setMsgID();
            chatMessageTobe.body = message;
            chatMessageTobe.Date = CommonMethods.getCurrentDate();
            chatMessageTobe.Time = CommonMethods.getCurrentTime();

            msg_edittext.setText("");

            AddMessagesToDB addtoDB = new AddMessagesToDB();
            addtoDB.execute();

            convoItemTobe = new ConversationItem();
            convoItemTobe.setPhone(number);
            convoItemTobe.setName(name);
            convoItemTobe.setMessage(message);

           convAdapter.add(convoItemTobe);

            AddConvoToDB addConvo = new AddConvoToDB();
            addConvo.execute();

//            try {
//                SmsManager smsManager = SmsManager.getDefault();
//                smsManager.sendTextMessage(number, null, message, null, null);
//                //Toast.makeText(getApplicationContext(), "Message Sent",
//                //Toast.LENGTH_LONG).show();
//            } catch (Exception ex) {
//                //Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                //Toast.LENGTH_LONG).show();
//                ex.printStackTrace();
//            }

            if (CurrentTabPosition == 1)
                convAdapter.notifyDataSetChanged();


            chatAdapter.add(chatMessageTobe);

//            chatMessage2.setMsgID();
//            chatMessage2.body = message;
//            chatMessage2.Date = CommonMethods.getCurrentDate();
//            chatMessage2.Time = CommonMethods.getCurrentTime();
//            msg_edittext.setText("");
//            chatAdapter.add(chatMessage2);



            chatAdapter.notifyDataSetChanged();
        }

    }

    public void sendSMS(String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            //Toast.makeText(getApplicationContext(), "Message Sent",
                    //Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            //Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
                    //Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sendMessageButton:
                sendTextMessage(v);
                //String msg = msg_edittext.getEditableText().toString();
                //msg = msg + "\n\nThis messages was sent by CAO(Chat Always On)"
                //sendSMS(number, msg_edittext.getEditableText().toString());


        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if(v.getId() == R.id.msgListView) {



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
        ChatMessage obj = chatAdapter.getMsgItem(info.position);
        int menuItemIndex = item.getItemId();
        //delete button
        if(menuItemIndex == 0)
        {
            chatMessageTobe = obj;
            DeleteMessages del = new DeleteMessages();
            del.execute();
        }
        return true;
    }
}