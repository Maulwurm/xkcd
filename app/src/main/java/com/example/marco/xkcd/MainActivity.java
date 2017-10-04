package com.example.marco.xkcd;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements Callback {

    private ImageView image;
    private Comic currentComic;

    public Comic getCurrentComic() {
        return currentComic;
    }

    public void setCurrentComic(Comic currentComic) {
        this.currentComic = currentComic;
    }

    public int getNextComicId(){
        int currentId = getCurrentComic().getNum();
        int nextId = currentId + 1;
        return nextId;
    }

    public int getPreviousId(){
        return getCurrentComic().getNum()-1;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_previous:
                    previous();
                    return true;
                case R.id.navigation_latest:
                    latest();
                    return true;
                case R.id.navigation_next:
                    next();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        image = (ImageView) findViewById(R.id.image);

        latest();

    }

    public void performRequest(String url){

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);

        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("Web","failue");
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Log.d("Web","onResponse");

        ResponseBody responseBody = response.body();

        if(responseBody != null){
            String responseString = responseBody.string();
            Logger.json(responseString);

            // TODO: 04.10.2017 comic can be null


            final Comic comic = convertComic(responseString);

            if(comic != null){
                // TODO: 04.10.2017 titel und url ausgeben

                String titel = comic.getTitle();
                String url = comic.getImg();
                Logger.d("Titel: %s Url: %s",titel,url);

                //change context to main ui
                Handler mainHandler = new Handler(getMainLooper());
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        showComic(comic);
                    }
                };
                mainHandler.post(runnable);
            } else {
                Logger.e("Comic = NULL!!!");
            }
        }
    }

    @Nullable
    public Comic convertComic(String responseString){
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseString,Comic.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Comic bild laden
     * @param comic
     */
    public void showComic(Comic comic){
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        Picasso.with(this).load(comic.getImg()).into(image);

        // TODO: 04.10.2017  save comic
        setCurrentComic(comic);

    }

    /**
     * Navigation
     */

    public void latest(){
        String latestUrl = "http://xkcd.com/info.0.json";
        performRequest(latestUrl);
    }

    public void next(){
        int nextId = getNextComicId();
        String nextUrl = getSpecificUrl(nextId);
        performRequest(nextUrl);
    }

    public void previous(){
        int previousId = getPreviousId();
        String previousUrl = getSpecificUrl(previousId);
        performRequest(previousUrl);
    }

    public String getSpecificUrl(int id){
        String format = "http://xkcd.com/%1$s/info.0.json";
        return String.format(format, id);
    }
}