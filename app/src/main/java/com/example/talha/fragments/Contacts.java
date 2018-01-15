package com.example.talha.fragments;

/**
 * Created by talha on 1/6/2018.
 */

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.talha.help3.ChatDisplay;
import com.example.talha.help3.Contact;
import com.example.talha.help3.ContactAdapter;
import com.example.talha.help3.MainActivity;
import com.example.talha.help3.R;

import java.io.IOException;
import java.util.ArrayList;

public class Contacts extends Fragment {



    // ArrayList
    ArrayList<Contact> contactList;
    //List<SelectUser> temp;
    // Contact List
    ListView listView;
    // Cursor to load contacts list
    Cursor phones;

    // Pop up
    ContentResolver resolver;
    SearchView search;
    public ContactAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_layout, container, false);


        contactList = new ArrayList<Contact>();
        resolver = getContext().getContentResolver();
        listView = (ListView) view.findViewById(R.id.contacts_list);

        Context applicationContext = MainActivity.getContextOfApplication();
        applicationContext.getContentResolver();

        phones = applicationContext.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        LoadContact loadContact = new LoadContact();
        loadContact.execute();

        search = (SearchView) view.findViewById(R.id.searchView);

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

                Intent intent = new Intent(getActivity(), ChatDisplay.class);

                Contact item = contactList.get(i);
                intent.putExtra("Name", item.getName().toString());
                intent.putExtra("Number", item.getPhone().toString());
                getActivity().startActivity(intent);

                //Toast.makeText(getContext(), "On ltem click trigered", Toast.LENGTH_LONG).show();
            }
        });

        return view;
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
                    Toast.makeText(getContext(), "No contacts in your contact list.", Toast.LENGTH_LONG).show();
                }

                while (phones.moveToNext()) {
                    Bitmap bit_thumb = null;
                    String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                    String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
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
                }
            }
            //phones.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ContactAdapter(contactList, getActivity());
            listView.setAdapter(adapter);



            listView.setFastScrollEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }



}
