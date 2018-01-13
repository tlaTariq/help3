package com.example.talha.help3;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by talha on 1/10/2018.
 */

public class ContactDisplayAdapter extends BaseAdapter {

    public List<Contact> _data;
    private ArrayList<Contact> arraylist;
    Context _c;
    ContactAdapter.ViewHolder v;

    public ContactDisplayAdapter(List<Contact> Contacts, Context context) {
        _data = Contacts;
        _c = context;
        this.arraylist = new ArrayList<Contact>();
        this.arraylist.addAll(_data);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.contact_display_view, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ContactAdapter.ViewHolder();

        v.title = (TextView) view.findViewById(R.id.name_cdv);
        v.check = (CheckBox) view.findViewById(R.id.check_cdv);
        v.phone = (TextView) view.findViewById(R.id.no_cdv);
        v.imageView = (ImageView) view.findViewById(R.id.pic_cdv);

        final Contact data = (Contact) _data.get(i);
        v.title.setText(data.getName());
        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.images);
            }
            // Seting round image
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.images); // Load default image

        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.images));
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());

        /*// Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View contact_view) {
                CheckBox checkBox = (CheckBox) contact_view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });*/

        view.setTag(data);
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (Contact wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        CheckBox check;
    }
}
