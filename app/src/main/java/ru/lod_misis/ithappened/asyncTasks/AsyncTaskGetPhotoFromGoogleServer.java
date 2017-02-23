package ru.lod_misis.ithappened.asyncTasks;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class AsyncTaskGetPhotoFromGoogleServer extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;

    public AsyncTaskGetPhotoFromGoogleServer(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        String urlStr = params[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new URL(urlStr).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        }catch (IOException e) {
            Log.d("Lod", e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
