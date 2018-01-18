
package com.example.talha.database;

/**
 * Created by talha on 1/4/2018.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.talha.help3.ChatMessage;
import com.example.talha.help3.ConversationItem;
import com.example.talha.help3.FavouriteItem;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Conversations table name
    private static final String TABLE_CONVERSATION = "conversation";
    // Messages table name
    private static final String TABLE_MESSAGE = "message";
    //Favourites table name
    private static final String TABLE_FOVOURITE = "favourite";
    //Favourite messages table name
    private static final String TABLE_FAVOURITE_MESSAGE = "favouritemessage";



    // Conversation Table Columns names
    private static final String KEY_CONVO_ID = "id";
    private static final String KEY_CONVO_NAME = "name";
    private static final String KEY_CONVO_MESSAGE = "message";
    private static final String KEY_CONVO_NUMBER = "number";

    // Message Table Columns names
    private static final String KEY_MSG_ID = "id";
    private static final String KEY_MSG_BODY = "bbody";
    private static final String KEY_MSG_DATE = "bdate";
    private static final String KEY_MSG_TIME = "btime";
    private static final String KEY_MSG_FOR = "conversator";
    private static final String KEY_MSG_FLAG = "ismine";

    // Favourite Table Columns names
    private static final String KEY_FAV_ID = "id";
    private static final String KEY_FAV_NAME = "name";
    private static final String KEY_FAV_MESSAGE = "message";
    private static final String KEY_FAV_NUMBER = "number";

    // Favourite Message Table Columns names
    private static final String KEY_FAV_MSG_ID = "id";
    private static final String KEY_FAV_MSG_BODY = "bbody";
    private static final String KEY_FAV_MSG_DATE = "bdate";
    private static final String KEY_FAV_MSG_TIME = "btime";
    private static final String KEY_FAV_MSG_FOR = "conversator";
    private static final String KEY_FAV_MSG_FLAG = "ismine";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONVERSATION +
                "(" + KEY_CONVO_ID + " INTEGER PRIMARY KEY," + KEY_CONVO_NAME + " TEXT," +
                KEY_CONVO_MESSAGE + " TEXT," + KEY_CONVO_NUMBER + " TEXT" + ")";


        String CREATE_MESSAGES_TABLE = "CREATE TABLE " + TABLE_MESSAGE +
                "(" + KEY_MSG_ID + " INTEGER PRIMARY KEY," + KEY_MSG_BODY + " TEXT," +
                KEY_MSG_DATE + " TEXT," + KEY_MSG_TIME + " TEXT," + KEY_MSG_FOR + " TEXT," + KEY_MSG_FLAG + " TEXT" + ")";

        String CREATE_FOVOURITES_TABLE = "CREATE TABLE " + TABLE_FOVOURITE +
                "(" + KEY_FAV_ID + " INTEGER PRIMARY KEY," + KEY_FAV_NAME + " TEXT," +
                KEY_FAV_MESSAGE + " TEXT," + KEY_FAV_NUMBER + " TEXT" + ")";


        String CREATE_FOVOURITES_MESSAGES_TABLE = "CREATE TABLE " + TABLE_FAVOURITE_MESSAGE +
                "(" + KEY_FAV_MSG_ID + " INTEGER PRIMARY KEY," + KEY_FAV_MSG_BODY + " TEXT," +
                KEY_FAV_MSG_DATE + " TEXT," + KEY_FAV_MSG_TIME + " TEXT," + KEY_FAV_MSG_FOR + " TEXT," + KEY_FAV_MSG_FLAG + " TEXT" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_MESSAGES_TABLE);
        db.execSQL(CREATE_FOVOURITES_TABLE);
        db.execSQL(CREATE_FOVOURITES_MESSAGES_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOVOURITE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE_MESSAGE);

        // Create tables again
        onCreate(db);

    }


    //All CRUD(Create, Read, Update, Delete) Operations For Messages Table

    // Adding new message
    public void addMsg(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_MSG_BODY, item.getBody());
        values.put(KEY_MSG_DATE, item.getDate());
        values.put(KEY_MSG_TIME, item.getTime());
        String flag;
        if(item.getIsMine())
            flag = "1";
        else
            flag = "0";
        values.put(KEY_MSG_FOR, item.getFor());
        values.put(KEY_MSG_FLAG, flag);


        // Inserting Row
        db.insert(TABLE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single message
    public ChatMessage getMsg(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MESSAGE, new String[] { KEY_MSG_ID, KEY_MSG_BODY, KEY_MSG_DATE, KEY_MSG_TIME, KEY_MSG_FOR, KEY_MSG_FLAG }, KEY_CONVO_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ChatMessage item = new ChatMessage();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setBody(cursor.getString(1));
        item.setDate(cursor.getString(2));
        item.setTime(cursor.getString(3));
        item.setFor(cursor.getString(4));
        if(cursor.getString(5).equals("1"))
            item.setIsMine(true);
        else
            item.setIsMine(false);

        return item;
    }

    // Getting All messages
    public ArrayList<ChatMessage> getAllMessages() {
        ArrayList<ChatMessage> List = new ArrayList<ChatMessage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatMessage item = new ChatMessage();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setBody(cursor.getString(1));
                item.setDate(cursor.getString(2));
                item.setTime(cursor.getString(3));
                item.setFor(cursor.getString(4));

                String flag = cursor.getString(5);
                if(flag.equals("1"))
                    item.setIsMine(true);
                else
                    item.setIsMine(false);

// Adding contact to list
                List.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Getting All messages for some specific person
    public ArrayList<ChatMessage> getAllMessagesForSpecificPerson(String conversator) {
        ArrayList<ChatMessage> List = new ArrayList<ChatMessage>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE " + KEY_MSG_FOR " = '";

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_MESSAGE, new String[] { KEY_MSG_ID, KEY_MSG_BODY, KEY_MSG_DATE, KEY_MSG_TIME, KEY_MSG_FOR, KEY_MSG_FLAG }, KEY_MSG_FOR + "=?", new String[] { conversator }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatMessage item = new ChatMessage();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setBody(cursor.getString(1));
                item.setDate(cursor.getString(2));
                item.setTime(cursor.getString(3));
                item.setFor(cursor.getString(4));

                String flag = cursor.getString(5);
                if(flag.equals("1"))
                    item.setIsMine(true);
                else
                    item.setIsMine(false);

// Adding contact to list
                List.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating single message
    public int updateMessage(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();

        String flag;
        if(item.getIsMine())
            flag = "1";
        else
            flag = "0";

        ContentValues values = new ContentValues();
        values.put(KEY_MSG_BODY, item.getBody());
        values.put(KEY_MSG_DATE, item.getDate());
        values.put(KEY_MSG_TIME, item.getTime());
        values.put(KEY_MSG_FOR, item.getFor());
        values.put(KEY_MSG_FLAG, flag);

        // updating row
        return db.update(TABLE_MESSAGE, values, KEY_MSG_ID + " = ?", new String[] { String.valueOf(item.getId()) });
    }

    // Deleting single message
    public void deleteMessage(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGE, KEY_MSG_ID + " = ?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    // Getting messages Count
    public int getMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    //All CRUD(Create, Read, Update, Delete) Operations For Favourite Messages Table

    // Adding new message
    public void addFavMsg(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FAV_MSG_BODY, item.getBody());
        values.put(KEY_FAV_MSG_DATE, item.getDate());
        values.put(KEY_FAV_MSG_TIME, item.getTime());
        String flag;
        if(item.getIsMine())
            flag = "1";
        else
            flag = "0";
        values.put(KEY_FAV_MSG_FOR, item.getFor());
        values.put(KEY_FAV_MSG_FLAG, flag);


        // Inserting Row
        db.insert(TABLE_FAVOURITE_MESSAGE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single message
    public ChatMessage getFavMsg(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAVOURITE_MESSAGE, new String[] { KEY_MSG_ID, KEY_MSG_BODY, KEY_MSG_DATE, KEY_MSG_TIME, KEY_MSG_FOR, KEY_MSG_FLAG }, KEY_CONVO_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ChatMessage item = new ChatMessage();
        item.setId(Integer.parseInt(cursor.getString(0)));
        item.setBody(cursor.getString(1));
        item.setDate(cursor.getString(2));
        item.setTime(cursor.getString(3));
        item.setFor(cursor.getString(4));
        if(cursor.getString(5).equals("1"))
            item.setIsMine(true);
        else
            item.setIsMine(false);

        return item;
    }

    // Getting All messages
    public ArrayList<ChatMessage> getAllFavMessages() {
        ArrayList<ChatMessage> List = new ArrayList<ChatMessage>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FAVOURITE_MESSAGE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatMessage item = new ChatMessage();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setBody(cursor.getString(1));
                item.setDate(cursor.getString(2));
                item.setTime(cursor.getString(3));
                item.setFor(cursor.getString(4));

                String flag = cursor.getString(5);
                if(flag.equals("1"))
                    item.setIsMine(true);
                else
                    item.setIsMine(false);

// Adding contact to list
                List.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Getting All messages for some specific person
    public ArrayList<ChatMessage> getAllFavMessagesForSpecificPerson(String conversator) {
        ArrayList<ChatMessage> List = new ArrayList<ChatMessage>();
        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE " + KEY_MSG_FOR " = '";

        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor = db.rawQuery(selectQuery, null);

        Cursor cursor = db.query(TABLE_FAVOURITE_MESSAGE, new String[] { KEY_MSG_ID, KEY_MSG_BODY, KEY_MSG_DATE, KEY_MSG_TIME, KEY_MSG_FOR, KEY_MSG_FLAG }, KEY_MSG_FOR + "=?", new String[] { conversator }, null, null, null, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ChatMessage item = new ChatMessage();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setBody(cursor.getString(1));
                item.setDate(cursor.getString(2));
                item.setTime(cursor.getString(3));
                item.setFor(cursor.getString(4));

                String flag = cursor.getString(5);
                if(flag.equals("1"))
                    item.setIsMine(true);
                else
                    item.setIsMine(false);

// Adding contact to list
                List.add(item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return List;
    }

    // Updating single message
    public int updateFavMessage(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();

        String flag;
        if(item.getIsMine())
            flag = "1";
        else
            flag = "0";

        ContentValues values = new ContentValues();
        values.put(KEY_MSG_BODY, item.getBody());
        values.put(KEY_MSG_DATE, item.getDate());
        values.put(KEY_MSG_TIME, item.getTime());
        values.put(KEY_MSG_FOR, item.getFor());
        values.put(KEY_MSG_FLAG, flag);

        // updating row
        return db.update(TABLE_FAVOURITE_MESSAGE, values, KEY_MSG_ID + " = ?", new String[] { String.valueOf(item.getId()) });
    }

    // Deleting single message
    public void deleteFavMessage(ChatMessage item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAVOURITE_MESSAGE, KEY_MSG_ID + " = ?", new String[] { String.valueOf(item.getId()) });
        db.close();
    }

    // Getting messages Count
    public int getFavMessagesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAVOURITE_MESSAGE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }



    //All CRUD(Create, Read, Update, Delete) Operations For Conversations Table

    // Adding new conversation
    public void addConvo(ConversationItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());



        // Inserting Row
        db.insert(TABLE_CONVERSATION, null, values);
        db.close(); // Closing database connection
    }

    public void smartAddConvo(ConversationItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());

        //SQLiteDatabase dbToRead = this.getReadableDatabase();

        String number = item.getPhone();
        Integer id;
        Cursor cursor = db.query(TABLE_CONVERSATION, new String[] { KEY_CONVO_ID, KEY_CONVO_NAME, KEY_CONVO_MESSAGE, KEY_CONVO_NUMBER }, KEY_CONVO_NUMBER + "=?", new String[] { number }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = Integer.parseInt(cursor.getString(0));

                db.delete(TABLE_CONVERSATION, KEY_CONVO_ID + " = ?", new String[]{String.valueOf(id)});

            } while (cursor.moveToNext());
        }

        // Inserting Row
        db.insert(TABLE_CONVERSATION, null, values);
        db.close(); // Closing database connection
    }

    // Getting single conversation
    public ConversationItem getConvo(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONVERSATION, new String[] { KEY_CONVO_ID, KEY_CONVO_NAME, KEY_CONVO_MESSAGE, KEY_CONVO_NUMBER }, KEY_CONVO_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ConversationItem item = new ConversationItem();
        item.setID(Integer.parseInt(cursor.getString(0)));
        item.setName(cursor.getString(1));
        item.setMessage(cursor.getString(2));
        item.setPhone(cursor.getString(3));

        //ConversationItem item = new ConversationItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        return item;
    }

    // Getting All Conversations
    public ArrayList<ConversationItem> getAllConversations() {
        ArrayList<ConversationItem> convList = new ArrayList<ConversationItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONVERSATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ConversationItem item = new ConversationItem();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setMessage(cursor.getString(2));
                item.setPhone(cursor.getString(3));

// Adding contact to list
                convList.add(0, item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return convList;
    }

    // Getting All Conversations
    public ArrayList<String> getAllConvoNumbers() {
        ArrayList<String> convList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_CONVO_NUMBER + " FROM " + TABLE_CONVERSATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String nmbr = cursor.getString(0);

// Adding contact to list
                convList.add(0, nmbr);
            } while (cursor.moveToNext());
        }

        // return contact list
        return convList;
    }

    // Updating single conversation
    public int updateContact(ConversationItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());

        // updating row
        return db.update(TABLE_CONVERSATION, values, KEY_CONVO_ID + " = ?", new String[] { String.valueOf(item.getID()) });
    }

    // Deleting single conversation
    public void deleteConvo(ConversationItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONVERSATION, KEY_CONVO_ID + " = ?", new String[] { String.valueOf(item.getID()) });
        db.close();
    }

    // Getting conversations Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONVERSATION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    //All CRUD(Create, Read, Update, Delete) Operations For Favourites Table

    // Adding new favourite
    public void addFav(FavouriteItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());



        // Inserting Row
        db.insert(TABLE_FOVOURITE, null, values);
        db.close(); // Closing database connection
    }

    public void smartAddFav(FavouriteItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());

        //SQLiteDatabase dbToRead = this.getReadableDatabase();

        String number = item.getPhone();
        Integer id;
        Cursor cursor = db.query(TABLE_FOVOURITE, new String[] { KEY_FAV_ID, KEY_FAV_NAME, KEY_FAV_MESSAGE, KEY_FAV_NUMBER }, KEY_FAV_NUMBER + "=?", new String[] { number }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = Integer.parseInt(cursor.getString(0));

                db.delete(TABLE_FOVOURITE, KEY_FAV_ID + " = ?", new String[]{String.valueOf(id)});

            } while (cursor.moveToNext());
        }

        // Inserting Row
        db.insert(TABLE_FOVOURITE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single favourite
    public FavouriteItem getFav(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FOVOURITE, new String[] { KEY_CONVO_ID, KEY_CONVO_NAME, KEY_CONVO_MESSAGE, KEY_CONVO_NUMBER }, KEY_CONVO_ID + "=?", new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        FavouriteItem item = new FavouriteItem();
        item.setID(Integer.parseInt(cursor.getString(0)));
        item.setName(cursor.getString(1));
        item.setMessage(cursor.getString(2));
        item.setPhone(cursor.getString(3));

        //FavouriteItem item = new FavouriteItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        // return contact
        return item;
    }

    // Getting All Favourites
    public ArrayList<FavouriteItem> getAllFavs() {
        ArrayList<FavouriteItem> convList = new ArrayList<FavouriteItem>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_FOVOURITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                FavouriteItem item = new FavouriteItem();
                item.setID(Integer.parseInt(cursor.getString(0)));
                item.setName(cursor.getString(1));
                item.setMessage(cursor.getString(2));
                item.setPhone(cursor.getString(3));

// Adding contact to list
                convList.add(0, item);
            } while (cursor.moveToNext());
        }

        // return contact list
        return convList;
    }

    // Getting All Favourites
    public ArrayList<String> getAllFavNumbers() {
        ArrayList<String> convList = new ArrayList<String>();
        // Select All Query
        String selectQuery = "SELECT " + KEY_FAV_NUMBER + " FROM " + TABLE_FOVOURITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String nmbr = cursor.getString(0);

// Adding contact to list
                convList.add(0, nmbr);
            } while (cursor.moveToNext());
        }

        // return contact list
        return convList;
    }

    // Updating single conversation
    public int updateFav(FavouriteItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONVO_NAME, item.getName());
        values.put(KEY_CONVO_MESSAGE, item.getMessage());
        values.put(KEY_CONVO_NUMBER, item.getPhone());

        // updating row
        return db.update(TABLE_FOVOURITE, values, KEY_CONVO_ID + " = ?", new String[] { String.valueOf(item.getID()) });
    }

    // Deleting single conversation
    public void deleteFav(FavouriteItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOVOURITE, KEY_CONVO_ID + " = ?", new String[] { String.valueOf(item.getID()) });
        db.close();
    }

    // Getting conversations Count
    public int getFavouritesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FOVOURITE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

}
