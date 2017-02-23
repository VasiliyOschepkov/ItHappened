package ru.lod_misis.ithappened.asyncTasks;


import android.os.AsyncTask;

import org.json.JSONArray;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class AsyncTaskDeletePastEventOnServer extends AsyncTask<PastEvent, Void, Void> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event/";
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Void doInBackground(PastEvent... params) {
        try {
            JSONArray array = new JSONArray();
            for (PastEvent pastEvent : params) {
                array.put(pastEvent.getIdServer());
            }

            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authentication", "gid-token " + Controller.token);
            connection.setRequestProperty("content-type", "application/json");
            connection.setDoOutput(true);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
            bufferedWriter.write(array.toString());
            bufferedWriter.flush();

            bufferedWriter.close();
            out.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
