package com.example.talha.help3;

import android.content.Context;
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

import static com.example.talha.fragments.Conversations.conv_numbers_list;

/**
 * Created by talha on 1/4/2018.
 */

public class ConversationAdapter extends BaseAdapter {

    public static List<ConversationItem> conv_data = new ArrayList<ConversationItem>();
    public static List<ConversationItem> conv_data_numbers = new ArrayList<ConversationItem>();
    private ArrayList<ConversationItem> arraylist;
    Context _c;
    ViewHolder v;

    public ConversationAdapter(List<ConversationItem> conversations, Context context) {
        conv_data = conversations;
        _c = context;
        this.arraylist = new ArrayList<ConversationItem>();
        this.arraylist.addAll(conv_data);
    }

    @Override
    public int getCount() {
        return conv_data.size();
    }

    @Override
    public Object getItem(int i) {
        return conv_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.conv_view, null);

        } else {
            view = convertView;
        }

//        v = new ViewHolder();
//
//        v.title = (TextView) view.findViewById(R.id.conv_name);
//        v.check = (CheckBox) view.findViewById(R.id.conv_check);
//        v.phone = (TextView) view.findViewById(R.id.conv_number);
//        v.message = (TextView) view.findViewById(R.id.conv_msg);
//        v.imageView = (ImageView) view.findViewById(R.id.conv_pic);



        TextView name = (TextView) view.findViewById(R.id.conv_name);
        //v.check = (CheckBox) view.findViewById(R.id.conv_check);
        TextView ph = (TextView) view.findViewById(R.id.conv_number);
        TextView mseg = (TextView) view.findViewById(R.id.conv_msg);
        ImageView imv = (ImageView) view.findViewById(R.id.conv_pic);


        final ConversationItem data = (ConversationItem) conv_data.get(i);
        name.setText(data.getName());
        //v.check.setChecked(data.getCheckedBox());
        ph.setText(data.getPhone());
        mseg.setText(data.getMessage());
        imv.setImageDrawable(this._c.getDrawable(R.drawable.images));

//        final ConversationItem data = (ConversationItem) conv_data.get(i);
//        v.title.setText(data.getName());
//        v.check.setChecked(data.getCheckedBox());
//        v.phone.setText(data.getPhone());
//        v.message.setText(data.getMessage());

        // Set image if exists
//        try {
//
//            if (data.getThumb() != null) {
//                v.imageView.setImageBitmap(data.getThumb());
//            } else {
//                v.imageView.setImageResource(R.drawable.images);
//            }
//            // Seting round image
//            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.images); // Load default image
//
//        } catch (OutOfMemoryError e) {
//            // Add default picture
//            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.images));
//            e.printStackTrace();
//        }

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

    public void add(ConversationItem object) {

        int index = conv_numbers_list.indexOf(object.getPhone());
        if (index >= 0) {
            conv_data.remove(index);
            conv_numbers_list.remove(index);
        }

        conv_data.add(0, object);
        conv_numbers_list.add(0, object.getPhone());
    }


    public void updateData(List<ConversationItem> newData) {

        conv_data = newData;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        conv_data.clear();
        if (charText.length() == 0) {
            conv_data.addAll(arraylist);
        } else {
            for (ConversationItem wp : arraylist) {
                if (wp.getMessage().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    conv_data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    static class ViewHolder {
        ImageView imageView;
        TextView title, message, phone;
        CheckBox check;
    }
}
