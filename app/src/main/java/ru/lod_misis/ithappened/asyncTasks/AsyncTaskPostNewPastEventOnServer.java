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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class AsyncTaskPostNewPastEventOnServer extends AsyncTask<PastEvent, Void, Void> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event";
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
//            connection.setRequestProperty("Authentication", "gid-token" + Controller.token);
            connection.connect();

            OutputStream out = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));

            JSONObject object = new JSONObject();
            for (PastEvent pastEvent : params) {
                // Берем из DB idServer Event
//                int id = Controller.getIdServerEventWhichIncludePastEvent(pastEvent.getId());

                JSONObject jsonObject = new JSONObject();
//                jsonObject.accumulate("TimeHappened", format.format(pastEvent.getDateEventHappened()));
                jsonObject.accumulate("Mark", pastEvent.getMark());
                jsonObject.accumulate("Number", pastEvent.getNumber());
//                jsonObject.accumulate("NumberTitle", pastEvent.)
                jsonObject.accumulate("Comment", pastEvent.getComment());
                jsonObject.accumulate("LastModified", pastEvent.getLastModified());
//                jsonObject.accumulate("IsDeleted", pastEvent.isDeleted());

//                object.accumulate(String.valueOf(id), jsonObject);
            }

            bufferedWriter.write(object.toString());
            bufferedWriter.flush();

            bufferedWriter.close();
            out.close();

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
        } catch (IOException e) {
            Log.d("Lod", e.getMessage());
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("Lod", "Проблема с работой json");
            e.printStackTrace();
        }
        return null;
    }

    private void updateDB(PastEvent[] pastEvents, List<Integer> listId) {
        for (int i = 0; i < listId.size(); i++) {
//            Controller.updateIdServerForPastEvent(pastEvents[i], listId.get(i));
        }
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
}
