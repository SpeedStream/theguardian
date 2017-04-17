package com.example.speedstream.theguardian;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by speedstream on 17/04/17.
 */

public class NewsSectionsAdapter extends ArrayAdapter<News> {
    private static String APIKEY = "?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";
    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context   The current context. Used to inflate the layout file.
     * @param sections  A list of sections objects to display in a list
     */
    public NewsSectionsAdapter(Activity context, ArrayList<News> sections) {
            // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
            // the second argument is used when the ArrayAdapter is populating a single TextView.
            // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
            // going to use this second argument, so it can be any value. Here, we used 0.
            super(context, 0, sections);
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
            R.layout.section_item, parent, false);
        }
        final News currentSection = getItem(position);

        TextView sectionTitle= (TextView) listItemView.findViewById(R.id.sectionName);
        sectionTitle.setText(currentSection.getmSectionName());

        ImageView sectionURL = (ImageView) listItemView.findViewById(R.id.imageView);
        sectionURL.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                System.out.println(currentSection.getmSectionURL()+APIKEY);
            }
        });
        return listItemView;
    }
}
