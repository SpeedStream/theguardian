package com.example.speedstream.theguardian;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by speedstream on 17/04/17.
 * Clase para mostrar las noticias de una seccion en particular
 */

public class NewsfeedAdapter extends ArrayAdapter<News> {

    Activity theContext = null;
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param newsFeed A List of AndroidFlavor objects to display in a list
     */
    public NewsfeedAdapter(Activity context, ArrayList<News> newsFeed) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsFeed);
        theContext = context;
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        final News currentNews = getItem(position);

        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        dateTextView.setText(currentNews.getmPublicationDate());

        TextView newsTitleTextView = (TextView) listItemView.findViewById(R.id.Title);
        newsTitleTextView.setText(currentNews.getmWebTitle());

        final LinearLayout openInBrowser = (LinearLayout) listItemView.findViewById(R.id.readArticle);
        openInBrowser.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Log.e("currentNews", currentNews.getmwebURL());
                String url = currentNews.getmwebURL();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent();
                intent.setData(uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                theContext.startActivity(intent);
            }
        });

        return listItemView;
    }
}
