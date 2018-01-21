package com.example.talha.Recievers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.ListView;
import android.widget.Toast;

import com.example.talha.database.DatabaseHandler;
import com.example.talha.fragments.Contacts;
import com.example.talha.help3.ChatMessage;
import com.example.talha.help3.CommonMethods;
import com.example.talha.help3.ConversationItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.talha.fragments.Conversations.convAdapter;
import static com.example.talha.help3.ChatDisplay.chatAdapter;
import static com.example.talha.help3.ChatDisplay.currentUser;
import static com.example.talha.help3.MainActivity.CurrentTabPosition;

/**
 * Created by talha on 1/17/2018.
 */

public class SmsListener extends BroadcastReceiver {

    private SharedPreferences preferences;

    public ChatMessage chatMessageTobe;
    public ConversationItem convoItemTobe;
    public List<ConversationItem> updateConvData = new ArrayList<ConversationItem>();

    public static ArrayList<ChatMessage> chatlist;
    public static ArrayList<ChatMessage> chatlist1;
    //public static ChatAdapter chatAdapter;
    ListView msgListView;

    Context _context;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        _context = context;

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
            SmsMessage[] msgs = null;
            String msg_from;
            if (bundle != null){
                //---retrieve the SMS message received---
                try{
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msg_from = msgs[i].getOriginatingAddress();
                        String msgBody = msgs[i].getMessageBody();

                        Toast.makeText(context, "New Message", Toast.LENGTH_LONG).show();

                        chatMessageTobe = new ChatMessage(1, msg_from, msgBody, false);

                        chatMessageTobe.body = msgBody;
                        chatMessageTobe.Date = CommonMethods.getCurrentDate();
                        chatMessageTobe.Time = CommonMethods.getCurrentTime();

//                        LoadMessages loadMsg = new LoadMessages();
//                        loadMsg.execute();

//                        AddMessagesToDB addtoDB = new AddMessagesToDB();
//                        addtoDB.execute();

                        DatabaseHandler db = new DatabaseHandler(context);

                        db.addMsg(chatMessageTobe);

                        if( currentUser.equals(msg_from))
                        {

                            chatAdapter.add(chatMessageTobe);

                            chatAdapter.notifyDataSetChanged();
                        }

                        convoItemTobe = new ConversationItem();
                        convoItemTobe.setPhone(msg_from);
                        convoItemTobe.setName(Contacts.giveMeName(msg_from));
                        convoItemTobe.setMessage(msgBody);

                        convAdapter.add(convoItemTobe);

                        db.smartAddConvo(convoItemTobe);

                        if (CurrentTabPosition == 0)
                            convAdapter.notifyDataSetChanged();


                        //chatAdapter.add(chatMessageTobe);

                        //chatAdapter.notifyDataSetChanged();

                    }
                }catch(Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
//                            Log.d("Exception caught",e.getMessage());
                }
            }
        }
    }

//    class LoadMessages extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            // Get Message list for this person
//
//            DatabaseHandler db = new DatabaseHandler(context);
//
//            chatlist = db.getAllMessagesForSpecificPerson(number);
//
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//
//            chatAdapter = new ChatAdapter(getApplicationContext(), chatlist);
//
//            msgListView.setAdapter(chatAdapter);
//
//            registerForContextMenu(msgListView);
//        }
//    }
    class AddMessagesToDB extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Message list for this person

            DatabaseHandler db = new DatabaseHandler(_context);

            db.addMsg(chatMessageTobe);
            //Toast.makeText(_context, "added", Toast.LENGTH_LONG).show();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

        }
    }
}
