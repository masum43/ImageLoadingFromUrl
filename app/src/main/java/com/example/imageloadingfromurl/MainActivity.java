package com.example.imageloadingfromurl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    String pic = "https://images.pexels.com/photos/414612/pexels-photo-414612.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageViewId);

        //ek thread theke r ek thread er moddhe connection kora hoy handler class er maddhome
        final Handler mainHandler = new Handler(Looper.getMainLooper()); //ei handler ta j thread e nibo se thread er hoye eti kaj korbe.
        //tai ekn se main thread er hoye kaj krbe. r ta Looper.getMainLooper er jonne hoye thake


        //Background thread initialization in below.. 1 ta class er main method oi class er main thread..
        // so onCreate method class er main thread. r main thread e network ba nicher kaj kora jay na.. tai background
        //thread initialize korte holo...
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(pic);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    //imageView.setImageBitmap(bitmap); // ui k update korte hoy main thread e. background thread theke kora jabe na.

                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });

                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();




    }
}
