package com.example.marco.xkcd;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

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

        String url = "http://xkcd.com/info.0.json";

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

            Gson gson = new Gson();

            final Comic comic = gson.fromJson(responseString,Comic.class);

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
        }
    }


    /**
     * Comic bild laden
     * @param comic
     */
    public void showComic(Comic comic){
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);

        Picasso.with(this).load(comic.getImg()).into(image);
    }
}