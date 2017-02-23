package ru.lod_misis.ithappened.asyncTasks;


import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;
import ru.lod_misis.ithappened.model.Statistics;

public class AsyncTaskGetAllDataFromServer extends AsyncTask<Void, Integer, List<Event>> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event/page/";
    private URL url;
    private HttpURLConnection connection;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Statistics statistics;

    @Override
    protected List<Event> doInBackground(Void... params) {
        List<Event> eventList = new ArrayList<>();
        try {
            statistics = Controller.statistics;

            for (int i = statistics.getCurrentPage(); i <= statistics.getTotalPage(); i++) {
                Log.d("Lod", "Получаем " + String.valueOf(i) + " стр");
                url = new URL(urlStr + String.valueOf(i));
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Authentication", "gid-token " + Controller.token);
                connection.connect();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder stringBuilder = new StringBuilder();

                while (in.ready()) {
                    stringBuilder.append(in.readLine());
                }

                Log.e("Lod", stringBuilder.toString());

                JSONObject jsonObjectPage = new JSONObject(stringBuilder.toString());
                getEvents(eventList, jsonObjectPage);
                Log.d("Lod", "Получили " + i + " стр");
            }
            Controller.statistics = null;
        } catch (MalformedURLException e) {
            Log.e("Lod", Log.getStackTraceString(e));
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.e("Lod", Log.getStackTraceString(e));
            e.printStackTrace();
        } catch (JSONException e) {
            Log.e("Lod", Log.getStackTraceString(e));
            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Lod", Log.getStackTraceString(e));
            e.printStackTrace();
        }

        return eventList;
    }

    private void getEvents(List<Event> events, JSONObject jsonObjectPage) {
        Log.e("Lod", "Получаем события из JSON OBJ");
        try {
            JSONArray jsonArray = jsonObjectPage.getJSONArray("CurrentPageEvents");
            for (int i = 0; i < statistics.getItemsPerPage(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int idEvent = jsonObject.getInt("Id");
                String name = jsonObject.getString("Name");
                String description = jsonObject.getString("Description");
                boolean hasMark = jsonObject.getBoolean("HasMark");
                boolean hasNumber = jsonObject.getBoolean("HasNumber");
                boolean hasComment = jsonObject.getBoolean("HasComment");
                String lastModified = jsonObject.getString("LastModified");
                boolean isDeletedEvent = jsonObject.getBoolean("IsDeleted");

                JSONArray happenings = jsonObject.getJSONArray("Happenings");
                List<PastEvent> listTime = new ArrayList<>();
                for (int j = 0; j < happenings.length(); j++) {
                    JSONObject jsonObjectTimeHappened = happenings.getJSONObject(j);
                    int idPastEvent = jsonObject.getInt("Id");
                    String timeHappened = jsonObjectTimeHappened.getString("TimeHappened");
                    int mark = jsonObjectTimeHappened.getInt("Mark");
                    int number = jsonObjectTimeHappened.getInt("Number");
                    String comment = jsonObjectTimeHappened.getString("Comment");
                    String lastModifiedPastEvent = jsonObjectTimeHappened.getString("LastModified");
                    boolean isDeletedPastEvent = jsonObject.getBoolean("IsDeleted");

                    Date time = format.parse(timeHappened);
                    Date last = format.parse(lastModifiedPastEvent);
                    listTime.add(new PastEvent(time, mark, number, comment, last, isDeletedPastEvent, idPastEvent));
                }

                Date lastEvent = format.parse(lastModified);
                Event event = new Event(name, description, listTime, lastEvent, hasNumber, hasMark, hasComment, isDeletedEvent, idEvent);
                events.add(event);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
//        try {
//            statistics = new AsyncTaskGetStatistics().execute().get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

//    @Override
//    protected void onPostExecute(List<Event> events) {
//        super.onPostExecute(events);
//        new AsyncTaskSynchronizationData().execute(events);
//    }
}
