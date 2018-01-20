package com.example.talha.help3;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
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

import static com.example.talha.fragments.Favourites.fav_numbers_list;

/**
 * Created by talha on 1/4/2018.
 */

public class FavouriteAdapter extends BaseAdapter {

    public static List<FavouriteItem> fav_data = new ArrayList<FavouriteItem>();
    public static List<FavouriteItem> fav_data_numbers = new ArrayList<FavouriteItem>();
    private ArrayList<FavouriteItem> arraylist;
    Context _c;
    ViewHolder v;

    public FavouriteAdapter(List<FavouriteItem> favourites, Context context) {
        fav_data = favourites;
        _c = context;
        this.arraylist = new ArrayList<FavouriteItem>();
        this.arraylist.addAll(fav_data);
    }

    @Override
    public int getCount() {
        return fav_data.size();
    }

    @Override
    public Object getItem(int i) {
        return fav_data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.fav_view, null);

        } else {
            view = convertView;
        }


        TextView name = (TextView) view.findViewById(R.id.fav_name);
        //v.check = (CheckBox) view.findViewById(R.id.fav_check);
        TextView ph = (TextView) view.findViewById(R.id.fav_number);
        TextView mseg = (TextView) view.findViewById(R.id.fav_msg);
        ImageView imv = (ImageView) view.findViewById(R.id.fav_pic);


        final FavouriteItem data = (FavouriteItem) fav_data.get(i);
        name.setText(data.getName());
        //v.check.setChecked(data.getCheckedBox());
        ph.setText(data.getPhone());
        mseg.setText(data.getMessage());
        imv.setImageDrawable(this._c.getDrawable(R.drawable.images));


        view.setTag(data);
        return view;
    }

    public void add(FavouriteItem object) {

        int index = fav_numbers_list.indexOf(object.getPhone());
        if (index >= 0) {
            fav_data.remove(index);
            fav_numbers_list.remove(index);
        }

        fav_data.add(0, object);
        fav_numbers_list.add(0, object.getPhone());
    }


    public void updateData(List<FavouriteItem> newData) {

        fav_data = newData;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        fav_data.clear();
        if (charText.length() == 0) {
            fav_data.addAll(arraylist);
        } else {
            for (FavouriteItem wp : arraylist) {
                if (wp.getMessage().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    fav_data.add(wp);
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
