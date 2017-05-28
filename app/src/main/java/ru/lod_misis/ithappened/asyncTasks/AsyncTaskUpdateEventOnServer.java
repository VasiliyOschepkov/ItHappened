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

public class AsyncTaskUpdateEventOnServer extends AsyncTask<Event, Void, Void> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event";
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Void doInBackground(Event... params) {
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
            for (Event event : params) {
//                int id = event.getIdServer();

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("Name", event.getName());
//                jsonObject.accumulate("Description", event.getDescription());
//                jsonObject.accumulate("HasMark", event.isMark());
//                jsonObject.accumulate("HasNumber", event.isNumber());
//                jsonObject.accumulate("HasComment", event.isComment());
//                jsonObject.accumulate("LastModified", event.getLastModified());
//                jsonObject.accumulate("IsDeleted", event.isDeleted());

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
