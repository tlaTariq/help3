package com.example.talha.help3;

/**
 * Created by talha on 1/4/2018.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;


    public List<ChatMessage> _data;
    private ArrayList<ChatMessage> arraylist;
    Context _c;
    //ChatAdapter.ViewHolder v;

    public ChatAdapter(Context context, ArrayList<ChatMessage> list) {
        _data = list;
        _c = context;
        this.arraylist = new ArrayList<ChatMessage>();
        this.arraylist.addAll(_data);



    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chatbubble, null);
        }
        else
        {view = convertView;}


        ChatMessage message = (ChatMessage) _data.get(position);

        TextView msg = (TextView) view.findViewById(R.id.message_text);
        msg.setText(message.body);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.bubble_layout);
        LinearLayout parent_layout = (LinearLayout) view.findViewById(R.id.bubble_layout_parent);

        // if message is mine then align to right
        if (message.isMine) {
            layout.setBackgroundResource(R.drawable.bubble2);
            parent_layout.setGravity(Gravity.RIGHT);
        }
        // If not mine then align to left
        else {
            layout.setBackgroundResource(R.drawable.bubble1);
            parent_layout.setGravity(Gravity.LEFT);
        }
        msg.setTextColor(Color.BLACK);


        view.setTag(message);
        return view;
    }

    public void add(ChatMessage object) {
        _data.add(object);
    }
}
