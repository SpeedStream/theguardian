package com.example.speedstream.theguardian;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{
    public static final String JSON_URL = "http://content.guardianapis.com/search?api-key=a8915dce-f35c-4cbf-8041-4de6f10ee8ca";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);
        Log.e("MAINACTIVITY", "onCreate Done");
    }


    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Log.e("MAINACTIVITY", "return onCreateLoader");
        return new NewsLoader(this, JSON_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, final List<News> news) {
        Log.e("MAINACTIVITY", "start onLoadFinished");
        final ListView newsListView = (ListView) findViewById(R.id.listNews);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("MAINACTIVITY", "setOnItemClick");
                String url = news.get(position).getMwebURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        NewsAdapter adapter = new NewsAdapter(getApplicationContext(), R.layout.news_list_item, news);
        Log.e("MAINACTIVITY", "NewsAdapter adapter");
        newsListView.setAdapter(adapter);
        Log.e("MAINACTIVITY", "setAdapter adapter");
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

    }

    public static class NewsLoader extends AsyncTaskLoader<List<News>> {
        private String mUrl;
        public NewsLoader(Context context, String url){
            super(context);
            Log.e("MAINACTIVITY", "url -> " + url);
            mUrl = url;
            Log.e("MAINACTIVITY", "murl -> " + mUrl);
            Log.e("MAINACTIVITY", "NewsLoader end");
        }

        @Override
        public List<News> loadInBackground(){
            Log.e("MAINACTIVITY", "List loadInBg");
            Log.e("MAINACTIVITY", "LIBG mUrl-> " + mUrl);
            Log.e("MAINACTIVITY", "eN.GNF(mUrl) -> " + extractNews.getNewsFrom(mUrl));
            return extractNews.getNewsFrom(mUrl);
        }
    }
}
