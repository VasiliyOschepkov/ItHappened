package ru.lod_misis.ithappened.asyncTasks;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;

public class AsyncTaskPostNewEventOnServer extends AsyncTask<Event, Void, Void> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event";
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Void doInBackground(Event... params) {
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authentication", "gid-token " + Controller.token);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            JSONArray jsonArray = new JSONArray();
            for (Event event : params) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("Name", event.getName());
                jsonObject.accumulate("Description", event.getDescription());
                jsonObject.accumulate("HasMark", event.isMark());
                jsonObject.accumulate("HasNumber", event.isNumber());
                jsonObject.accumulate("HasComment", event.isComment());
                jsonObject.accumulate("LastModified", event.getLastModified());
                jsonObject.accumulate("IsDeleted", event.isDeleted());

                jsonArray.put(jsonObject);
            }

            bufferedWriter.write(jsonArray.toString());
            bufferedWriter.flush();

            InputStream in = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                stringBuilder.append(bufferedReader.readLine());
            }

            // Парсинг JSON массива и получения id
            List<Integer> listId = getListIdFromJSONArray(stringBuilder.toString());

            // Обновение DB
            updateDB(params, listId);

            bufferedReader.close();
            in.close();

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

    private List<Integer> getListIdFromJSONArray(String strArrayId) {
        List<Integer> listId = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(strArrayId);

            for (int i = 0; i < jsonArray.length(); i++) {
                listId.add((int) jsonArray.get(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listId;
    }

    private void updateDB(Event[] events, List<Integer> listId) {
        for (int i = 0; i < listId.size(); i++) {
            Controller.updateIdServerForEvent(events[i], listId.get(i));
        }
    }
}
