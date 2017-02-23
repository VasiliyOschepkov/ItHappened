package ru.lod_misis.ithappened;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.lod_misis.ithappened.asyncTasks.AsyncTaskGetAllDataFromServer;
import ru.lod_misis.ithappened.asyncTasks.AsyncTaskGetStatistics;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.Parameter;
import ru.lod_misis.ithappened.model.PastEvent;
import ru.lod_misis.ithappened.model.Statistics;
import ru.lod_misis.ithappened.model.StatisticsData;

public class Controller {

    public static void save(Event event) {
        event.save();
    }

    public static void delete (Event event) {
        event.delete();
    }

    public static void asyncTaskGetPhoto(Intent getPhotoGoogleAccIntent) {}

    public static void syncData() {}

    public static List<Event> find(String whereClause, String[] whereArg, String groupBy, String orderBy, String limit) {
        return Event.find(Event.class, whereClause, whereArg, groupBy, orderBy, limit);
    }

    public static List<Event> getAllEvent () {
        return Event.listAll(Event.class);
    }


//    public static DBHelper dbHelper;
//    public static SQLiteDatabase db;
//
//    public static String token;
//    public static Statistics statistics;
//
//
//    public static void postEventOnServer(Event event) {
////        Log.d("Lod", "Отправка данных на сервер");
////        internetTask = new InternetTask();
////        internetTask.execute("Post event on server", event);
//    }
//
//    public static List<Event> getEventListFromDB() {
//        Log.d("Lod", "Получения событий из БД");
//        return dbHelper.selectAllEvent(db);
//    }
//
//    public static List<PastEvent> getPastEventListFromDB(){
//        Log.d("Lod", "Получения фиксаций событий из БД");
//        return dbHelper.selectAllPastEvent(db);
//    }
//
//    public static void saveEvent(Event event) {
//        Log.d("Lod", "Сохранения события в БД");
//        dbHelper.insertEvent(event, db);
//    }
//
//    public static void savePastEvent(Event event, PastEvent pastEvent) {
//        Log.d("Lod", "Сохранения фиксации события в БД");
//        dbHelper.insertPastEvent(event, pastEvent, db);
//    }
//
//    public static void updateEvent(Event event) {
//        Log.d("Lod", "Обновления события в БД");
//        dbHelper.updateEvent(event, db);
//    }
//
//    public static void updatePastEvent(PastEvent pastEvent) {
//        Log.d("Lod", "Обновление фиксации события в БД");
//        dbHelper.updatePastEvent(pastEvent, db);
//    }
//
//    public static void deleteEvent(Event event) {
//        Log.d("Lod", "Удаления события из БД");
//        dbHelper.deleteEvent(event, db);
//    }
//
//    public static void deletePstEvent(PastEvent pastEvent) {
//        Log.d("Lod", "Удаления фиксации события из БД");
//        dbHelper.deletePastEvent(pastEvent, db);
//    }
//
//    public static Event getEventOnDateFromDB(PastEvent pastEvent) {
//        Log.d("Lod", "Получения события по дате из БД");
//        return dbHelper.getEventOnDate(pastEvent.getDateEventHappened(), db);
//    }
//
//    public static List<Event> getListEventsMainFromDB() {
//        Log.d("Lod", "Получения событий из БД, которые на главном экране");
//        return dbHelper.getListEventMain(db);
//    }
//
//    public static String[] getArrayNameEventIsMainTrue(List<Event> listEventIsMainTrue) {
//        String[] arrayNameEvent = new String[6];
//        for (int i = 0; i < listEventIsMainTrue.size(); i++) {
//            Event event = listEventIsMainTrue.get(i);
//            arrayNameEvent[i] = event.getName();
//        }
//        return arrayNameEvent;
//    }
//
//
//    public static void synchronizationData() {
//        Log.d("Lod", "Синхронизация с сервером");
////        if (statistics == null) {
////            new AsyncTaskGetStatistics().execute();
////        }else {
////            new AsyncTaskGetAllDataFromServer().execute();
////        }
//        new AsyncTaskGetAllDataFromServer().execute();
//    }
//
//    public static List<PastEvent> getSortOnDatePastEventList() {
//        return null;
//    }
//
//    private static boolean isInternet() {
//        return true;
//    }
//
//    public static int getIdServerEventWhichIncludePastEvent(int id) {
//        return 0;
//    }
//
//    public static void updateIdServerForEvent(Event event, Integer integer) {
//
//    }
//
//    public static void updateIdServerForPastEvent(PastEvent pastEvent, Integer integer) {
//
//    }
//
//    public static List<PastEvent> getFilterPastEvent(List<String> listIdCheckedEvent) {
//        return dbHelper.getPastEventForCheckedEvent(listIdCheckedEvent, db);
//    }
//
//    public static void setNumberParameterEvent(Parameter parameter, int id) {
//        dbHelper.setNumberParameterEvent(parameter, id, db);
//    }
//
//    public static void setCommentParameterEvent(Parameter parameter, int id) {
//        dbHelper.setCommentParameterEvent(parameter, id, db);
//    }
//
//    public static void setRatingParameterEvent(Parameter parameter, int id) {
//        dbHelper.setRatingParameterEvent(parameter, id, db);
//    }
//
//    public static List<PastEvent> sortOnDateDay(List<PastEvent> listForAdapter) {
//        Date today = new Date();
//
//        for (int i = 0; i < listForAdapter.size(); ) {
//            if (listForAdapter.get(i).getDateEventHappened().getDay() < today.getDay() || listForAdapter.get(i).getDateEventHappened().getDay() > today.getDay()) {
//                listForAdapter.remove(i);
//            }else {
//                i++;
//            }
//        }
//
//        return listForAdapter;
//    }
//
//    public static List<PastEvent> sortOnDateWeek(List<PastEvent> listForAdapter) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.DAY_OF_MONTH, -7);
//        Date dayWeekAgo = calendar.getTime();
//
//        Date today = new Date();
//
//        for (int i = 0; i < listForAdapter.size(); ) {
//            if (listForAdapter.get(i).getDateEventHappened().before(dayWeekAgo) || listForAdapter.get(i).getDateEventHappened().after(today)) {
//                listForAdapter.remove(listForAdapter.get(i));
//            }else {
//                i++;
//            }
//        }
//
//        return listForAdapter;
//    }
//
//    public static List<PastEvent> sortOnDateMonth(List<PastEvent> listForAdapter) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.MONTH, -1);
//        Date dayWeekAgo = calendar.getTime();
//
//        Date today = new Date();
//
//        for (int i = 0; i < listForAdapter.size(); ) {
//            if (listForAdapter.get(i).getDateEventHappened().before(dayWeekAgo) || listForAdapter.get(i).getDateEventHappened().after(today)) {
//                listForAdapter.remove(listForAdapter.get(i));
//            }else {
//                i++;
//            }
//        }
//        return listForAdapter;
//    }
//
//    public static void sortOnDateAllTime(List<PastEvent> listForAdapter) {
//
//    }
//
//    public static List<PastEvent> sortOnDateYourPeriod(List<PastEvent> listForAdapter, Date date_on, Date date_from) {
//        for (int i = 0; i < listForAdapter.size(); ) {
//            if (listForAdapter.get(i).getDateEventHappened().before(date_on) || listForAdapter.get(i).getDateEventHappened().after(date_from)) {
//                listForAdapter.remove(listForAdapter.get(i));
//            }else {
//                i++;
//            }
//        }
//        return listForAdapter;
//    }
//
//    public static List<StatisticsData> getStatisticsData() {
//        String month1 = "Январь";
//        HashMap<Integer, Integer> data1 = new HashMap<>();
//        data1.put(1, 30);
//        data1.put(2, 40);
//        data1.put(3, 50);
//
//        String month2 = "Февраль";
//        HashMap<Integer, Integer> data2 = new HashMap<>();
//        data2.put(1, 50);
//        data2.put(2, 60);
//        data2.put(3, 43);
//
//        String month3 = "Март";
//        HashMap<Integer, Integer> data3 = new HashMap<>();
//        data3.put(1, 34);
//        data3.put(2, 34);
//        data3.put(3, 34);
//
//        StatisticsData statisticsData1 = new StatisticsData(month1, data1);
//        StatisticsData statisticsData2 = new StatisticsData(month2, data2);
//        StatisticsData statisticsData3 = new StatisticsData(month3, data3);
//
//        List<StatisticsData> list = new ArrayList<>();
//        list.add(statisticsData1);
//        list.add(statisticsData2);
//        list.add(statisticsData3);
//        return list;
//    }
//
//    public static List<Event> getEventFromDBOnId(List<Integer> listIdEvent) {
//        return dbHelper.selectEventOnId(db, listIdEvent);
//    }
//
//    public static List<StatisticsData> getStatisticsDataOnEvent(int idEvent) {
//        List<StatisticsData> list = new ArrayList<>();
//        String type1 = "Day"; // 24
//        HashMap<Integer, Integer> data1 = new HashMap<>();
//        data1.put(5, 12);
//        data1.put(13, 8);
//        data1.put(22, 2);
//
//        String type2 = "Week"; // 7
//        HashMap<Integer, Integer> data2 = new HashMap<>();
//        data2.put(1, 4);
//        data2.put(2, 7);
//        data2.put(5, 2);
//
//        String type3 = "Month"; // 30 (31)
//        HashMap<Integer, Integer> data3 = new HashMap<>();
//        data3.put(2, 3);
//        data3.put(13, 5);
//        data3.put(30, 2);
//
//        String type4 = "Year"; // 12
//        HashMap<Integer, Integer> data4 = new HashMap<>();
//        data4.put(2, 12);
//        data4.put(3, 8);
//        data4.put(6, 2);
//
//        list.add(new StatisticsData(type1, data1));
//        list.add(new StatisticsData(type2, data2));
//        list.add(new StatisticsData(type3, data3));
//        list.add(new StatisticsData(type4, data4));
//
//        return list;
//    }
}
