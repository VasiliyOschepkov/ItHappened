package ru.lod_misis.ithappened.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Statistics;


public class AsyncTaskGetStatistics extends AsyncTask<Void, Void, Statistics> {
    private String urlStr = "http://ithappened.azurewebsites.net/api/event/page/1";
    private URL url;
    private HttpURLConnection connection;

    @Override
    protected Statistics doInBackground(Void... params) {
        Statistics statistics = null;
        Log.d("Lod", "Получаем информацию о кол-ве стр");
        try {
            url = new URL(urlStr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Authentication", "gid-token " + Controller.token);
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            while (in.ready()) {
                stringBuilder.append(in.readLine());
            }

            JSONObject jsonObjectPage = new JSONObject(stringBuilder.toString());

            JSONObject jsonObjectInfoPages = jsonObjectPage.getJSONObject("PagingInfo");
            int totalPage = jsonObjectInfoPages.getInt("TotalPages");
            int currentPage = jsonObjectInfoPages.getInt("CurrentPage");
            int totalItems = jsonObjectInfoPages.getInt("TotalItems");
            int itemsPerPage = jsonObjectInfoPages.getInt("ItemsPerPage");
            statistics = new Statistics(totalPage, currentPage, totalItems, itemsPerPage);
            Log.d("Lod", "Кол-во стр: " + String.valueOf(totalPage));
        } catch (MalformedURLException e) {
            Log.d("Lod", "MalformedURLException");
            e.printStackTrace();
        } catch (ProtocolException e) {
            Log.d("Lod", "ProtocolException");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("Lod", "JSONException");
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("Lod", e.toString());
            e.printStackTrace();
        }

        return statistics;
    }

    @Override
    protected void onPostExecute(Statistics statistics) {
        super.onPostExecute(statistics);
//        Controller.statistics = statistics;
        new AsyncTaskGetAllDataFromServer().execute();
    }
}
