package ru.lod_misis.ithappened.asyncTasks;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class AsyncTaskUpdatePastEventOnServer extends AsyncTask<PastEvent, Void, Void> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event";
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Void doInBackground(PastEvent... params) {
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authentication", Controller.token);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            JSONObject object = new JSONObject();
            for (PastEvent pastEvent : params) {
//                int id = pastEvent.getIdServer();

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("Comment", pastEvent.getComment());
                jsonObject.accumulate("Mark", pastEvent.getMark());
                jsonObject.accumulate("Number", pastEvent.getNumber());
                jsonObject.accumulate("LastModified", pastEvent.getLastModified());
//                jsonObject.accumulate("IsDeleted", pastEvent.isDeleted());

//                object.accumulate(String.valueOf(id), jsonObject);
            }
            bufferedWriter.write(object.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
            out.close();

        } catch (IOException e) {
            Log.d("Lod", e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("Lod", "Проблема с работой json");
            e.printStackTrace();
        }
        return null;
    }
}
