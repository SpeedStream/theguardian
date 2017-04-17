package com.example.speedstream.theguardian;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by speedstream on 14/04/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, int resource, List<News> news){
        super(context, resource, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Log.e("ADAPTER", "view Adapter");
        View listViewItem = convertView;
        if(listViewItem == null){
            Log.e("ADAPTEr", "listViewItem -> Null");
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false)
            ;}

        News currentNews = getItem(position);

        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        TextView section = (TextView) listViewItem.findViewById(R.id.Section);
        TextView title = (TextView) listViewItem.findViewById(R.id.Title);

        date.setText(currentNews.getmPublicationDate());
        section.setText(currentNews.getmSectionName());
        title.setText(currentNews.getmWebTitle());

        return listViewItem;
    }

    /*TODO
    Color section...
    * */
}
