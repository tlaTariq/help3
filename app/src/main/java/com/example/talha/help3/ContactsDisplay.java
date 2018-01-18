package com.example.talha.help3;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by talha on 1/9/2018.
 */

public class ContactsDisplay extends Activity {

    // ArrayList
    ArrayList<Contact> contactList;
    //List<SelectUser> temp;

    ArrayList<String> phone_numbers;

    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones;

    // Pop up
    ContentResolver resolver;
    SearchView search;
    ContactDisplayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts_display_layout);


        contactList = new ArrayList<Contact>();
        phone_numbers = new ArrayList<String>();
        resolver = this.getContentResolver();
        listView = (ListView) findViewById(R.id.contacts_list_contacts_display);

        phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        LoadContact loadContact = new LoadContact();
        loadContact.execute();



        search = (SearchView) findViewById(R.id.searchView_contacts_display);

        //*** setOnQueryTextListener ***
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                adapter.filter(newText);
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Contact item = contactList.get(i);

                Intent intent = new Intent(ContactsDisplay.this, ChatDisplay.class);
                intent.putExtra("Name", item.getName().toString());
                intent.putExtra("Number", item.getPhone().toString());
                startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });


    }


    class LoadContact extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get Contact list from Phone

            if (phones != null) {
                if (phones.getCount() == 0) {
                    Toast.makeText(getApplicationContext(), "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    if(phoneNumber.startsWith("0"))
                        phoneNumber = "+92" + phoneNumber.substring(1);

                    String image_thumb = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                    try {
                        if (image_thumb != null) {
                            bit_thumb = MediaStore.Images.Media.getBitmap(resolver, Uri.parse(image_thumb));
                        } else {

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Contact Contact = new Contact();
                    Contact.setThumb(bit_thumb);
                    Contact.setName(name);
                    Contact.setPhone(phoneNumber);
                    Contact.setCheckedBox(false);
                    contactList.add(Contact);

                    phone_numbers.add(phoneNumber);
                }
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ContactDisplayAdapter(contactList, getApplicationContext());
            listView.setAdapter(adapter);



            listView.setFastScrollEnabled(true);
        }
    }

    public String giveMeName(String number)
    {
        int id = phone_numbers.indexOf(number);
        if(id >=0 )
            return contactList.get(id).getName();
        return number;
    }


}