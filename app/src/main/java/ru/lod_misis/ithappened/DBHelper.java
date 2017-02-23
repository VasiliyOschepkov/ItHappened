package ru.lod_misis.ithappened;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.Parameter;
import ru.lod_misis.ithappened.model.PastEvent;

public class DBHelper extends SQLiteOpenHelper {
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

    public DBHelper(Context context) {
        super(context, "DB_ver_1" +
                "", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TableEvents (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, description TEXT," +
                "lastModifiedEvent TEXT," +
                "isMain INTEGER," +
                "isNumberParameter INTEGER," +
                "isRatingParameter INTEGER," +
                "isCommentParameter INTEGER," +
                "titleNumberParameter TEXT," +
                "titleCommentParameter TEXT," +
                "titleRatingParameter TEXT," +
                "idServer INTEGER)");

        db.execSQL("CREATE TABLE TablePastEvent (idPastEvent INTEGER PRIMARY KEY AUTOINCREMENT," +
                " idEvent INTEGER," +
                " idServer2 INTEGER," +
                " dateEventHappened TEXT," +
                " numberParameter INTEGER," +
                " ratingParameter INTEGER," +
                " commentParameter TEXT," +
                " lastModifiedPastEvent TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Event> selectAllEvent(SQLiteDatabase db) {
        List<Event> eventList = new ArrayList<>();
        Cursor c = db.query("TableEvents", null, null, null ,null, null, null, null);
        if (c.moveToFirst()) {
            int nameEventColIndex = c.getColumnIndex("name");
            int descriptionColIndex = c.getColumnIndex("description");
            int isMainColIndex = c.getColumnIndex("isMain");
            int isNumberColIndex = c.getColumnIndex("isNumberParameter");
            int isMarkColIndex = c.getColumnIndex("isRatingParameter");
            int isCommentColIndex = c.getColumnIndex("isCommentParameter");
            int titleNumberColIndex = c.getColumnIndex("titleNumberParameter");
            int tilteCommentColIndex = c.getColumnIndex("titleCommentParameter");
            int titleRatingColIndex = c.getColumnIndex("titleRatingParameter");
            int lastModifiedEventColIndex = c.getColumnIndex("lastModifiedEvent");
            int idColIndex = c.getColumnIndex("id");
            int idServer1ColIndex = c.getColumnIndex("idServer");

            do {
                Cursor cursor = db.query("TablePastEvent", null, "idEvent = ?", new String[]{String.valueOf(c.getInt(idColIndex))}, null, null, null, null);
                List<PastEvent> list = new ArrayList<>();
                if (cursor.moveToFirst()) {
                    int dateEventHappenedColIndex = cursor.getColumnIndex("dateEventHappened");
                    int markColIndex = cursor.getColumnIndex("ratingParameter");
                    int numberColIndex = cursor.getColumnIndex("numberParameter");
                    int commentColIndex = cursor.getColumnIndex("commentParameter");
                    int lastModifiedPastEventColIndex = cursor.getColumnIndex("lastModifiedPastEvent");
                    int idPastEventColIndex = cursor.getColumnIndex("idPastEvent");
                    int idServer2ColIndex = cursor.getColumnIndex("idServer2");
                    do {
                        try {
                            Date dateEventHappened = format.parse(cursor.getString(dateEventHappenedColIndex));
                            Date lastModifiedPastEvent = format.parse(cursor.getString(lastModifiedPastEventColIndex));
                            PastEvent pastEvent = new PastEvent(cursor.getInt(idPastEventColIndex), dateEventHappened, cursor.getInt(markColIndex), cursor.getInt(numberColIndex), cursor.getString(commentColIndex), lastModifiedPastEvent, cursor.getInt(idServer2ColIndex));
                            list.add(pastEvent);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }while (cursor.moveToNext());

                }else {
                    cursor.close();
                }
                try {
                    Date lastModifiedEvent = format.parse(c.getString(lastModifiedEventColIndex));
                    Event event = new Event(c.getInt(idColIndex),
                            c.getInt(isMainColIndex) == 0 ? false : true,
                            c.getString(nameEventColIndex),
                            c.getString(descriptionColIndex),
                            list,
                            lastModifiedEvent,
                            c.getInt(isNumberColIndex) == 0 ? false : true,
                            c.getInt(isCommentColIndex) == 0 ? false : true,
                            c.getInt(isMarkColIndex) == 0 ? false : true,
                            c.getInt(idColIndex),
                            c.getString(titleNumberColIndex),
                            c.getString(tilteCommentColIndex),
                            c.getString(titleRatingColIndex));
                    eventList.add(event);
                    Log.d("Lod", String.valueOf(event.getId()));
                }catch (ParseException e) {

                }

            }while (c.moveToNext());

        }else {
            c.close();
        }

        return eventList;
    }

    public List<PastEvent> selectAllPastEvent(SQLiteDatabase db) {
        List<PastEvent> pastEventList = new ArrayList<>();

        Cursor cursor = db.query("TablePastEvent", null, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int dateEventHappenedColIndex = cursor.getColumnIndex("dateEventHappened");
            int markColIndex = cursor.getColumnIndex("ratingParameter");
            int numberColIndex = cursor.getColumnIndex("numberParameter");
            int commentColIndex = cursor.getColumnIndex("commentParameter");
            int lastModifiedPastEventColIndex = cursor.getColumnIndex("lastModifiedPastEvent");
            int idPastEventColIndex = cursor.getColumnIndex("idPastEvent");
            int idServerColIndex = cursor.getColumnIndex("idServer2");
            do {
                try {
                    Date dateEventHappened = format.parse(cursor.getString(dateEventHappenedColIndex));
                    Date lastModifiedPastEvent = format.parse(cursor.getString(lastModifiedPastEventColIndex));
                    PastEvent pastEvent = new PastEvent(cursor.getInt(idPastEventColIndex), dateEventHappened, cursor.getInt(markColIndex), cursor.getInt(numberColIndex), cursor.getString(commentColIndex), lastModifiedPastEvent, cursor.getInt(idServerColIndex));
                    pastEventList.add(pastEvent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());

        }else {
            cursor.close();
        }

        return pastEventList;
    }

    public void insertEvent(Event event, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", event.getName());
        contentValues.put("description", event.getDescription());
        contentValues.put("isMain", event.isMain() ? 1 : 0);
        contentValues.put("isNumberParameter", event.isNumber() ? 1 : 0);
        contentValues.put("isRatingParameter", event.isMark() ? 1 : 0);
        contentValues.put("lastModifiedEvent", format.format(event.getLastModified()));

        db.insert("TableEvents", null, contentValues);
    }

    public void insertPastEvent(Event event, PastEvent pastEvent, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        Cursor cursor = db.query("TableEvents", null, "name = ?", new String[]{event.getName()}, null, null, null, null);
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            contentValues.put("idEvent", id);
            contentValues.put("dateEventHappened", format.format(pastEvent.getDateEventHappened()));
            contentValues.put("numberParameter", pastEvent.getNumber());
            contentValues.put("ratingParameter", pastEvent.getMark());
            contentValues.put("commentParameter", pastEvent.getComment());
            contentValues.put("lastModifiedPastEvent", format.format(pastEvent.getLastModified()));

            db.insert("TablePastEvent", null, contentValues);
        }

    }

    public void updateEvent(Event event, SQLiteDatabase db){
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("name", event.getName());
        updatedValues.put("description", event.getDescription());
        updatedValues.put("isMain", event.isMain() ? 1 : 0);
        updatedValues.put("lastModifiedEvent", format.format(event.getLastModified()));

        db.update("TableEvents", updatedValues, "id = " + event.getId(), null);
    }

    public void updatePastEvent(PastEvent pastEvent, SQLiteDatabase db) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put("numberParameter", pastEvent.getNumber());
        updatedValues.put("ratingParameter", pastEvent.getMark());
        updatedValues.put("commentParameter", pastEvent.getComment());
        updatedValues.put("lastModifiedPastEvent", format.format(pastEvent.getLastModified()));

        db.update("TablePastEvent", updatedValues, "idPastEvent = " + pastEvent.getId(), null);
    }

    public void deleteEvent(Event event, SQLiteDatabase db) {
        db.delete("TableEvents", "id = ?", new String[]{String.valueOf(event.getId())});
    }

    public void deletePastEvent(PastEvent pastEvent, SQLiteDatabase db) {
        db.delete("TablePastEvent", "idPastEvent = ?", new String[]{String.valueOf(pastEvent.getId())});
    }

    public Event getEventOnDate(Date dateEventHappened, SQLiteDatabase db) {
        Cursor c = db.query("TablePastEvent", new String[]{"idEvent"}, "dateEventHappened = ?", new String[]{format.format(dateEventHappened)}, null, null, null, null);
        if (c.moveToFirst()) {
            int idEvent = c.getInt(c.getColumnIndex("idEvent"));
            Cursor cursor = db.query("TableEvents", null, "id = " + idEvent, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                Event event = new Event(name, description);
                return event;
            }else {
                return null;
            }
        }else {
            return null;
        }
    }

    public List<Event> getListEventMain(SQLiteDatabase db) {
        List<Event> listEventMain = new ArrayList<>();
        Cursor cursor = db.query("TableEvents", null, "isMain = ?", new String[]{"1"}, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameEventColIndex = cursor.getColumnIndex("name");
            int descriptionColIndex = cursor.getColumnIndex("description");
            int isMainColIndex = cursor.getColumnIndex("isMain");
            int isNumberColIndex = cursor.getColumnIndex("isNumberParameter");
            int isMarkColIndex = cursor.getColumnIndex("isRatingParameter");
            int lastModifiedEventColIndex = cursor.getColumnIndex("lastModifiedEvent");
            int idColIndex = cursor.getColumnIndex("id");
            do {
                Date lastModifiedEvent = null;
                try {
                    lastModifiedEvent = format.parse(cursor.getString(lastModifiedEventColIndex));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Event event = new Event(cursor.getInt(idColIndex), cursor.getInt(isMainColIndex) == 0 ? false : true, cursor.getString(nameEventColIndex), cursor.getString(descriptionColIndex), lastModifiedEvent, cursor.getInt(isNumberColIndex) == 0 ? false : true, cursor.getInt(isMarkColIndex) == 0 ? false : true);
                listEventMain.add(event);
            } while (cursor.moveToNext());
        } else {
            cursor.close();
        }
        return listEventMain;
    }

    public List<PastEvent> getPastEventForCheckedEvent(List<String> listIdCheckedEvent, SQLiteDatabase db) {
        List<PastEvent> pastEventList = new ArrayList<>();
        listIdCheckedEvent.toArray(new String[listIdCheckedEvent.size()]);
        for (String id : listIdCheckedEvent) {
            Cursor cursor = db.query("TablePastEvent", null, "idEvent = ?", new String[]{id}, null, null, null, null);
            if (cursor.moveToFirst()) {
                int dateEventHappenedColIndex = cursor.getColumnIndex("dateEventHappened");
                int markColIndex = cursor.getColumnIndex("ratingParameter");
                int numberColIndex = cursor.getColumnIndex("numberParameter");
                int commentColIndex = cursor.getColumnIndex("commentParameter");
                int lastModifiedPastEventColIndex = cursor.getColumnIndex("lastModifiedPastEvent");
                int idPastEventColIndex = cursor.getColumnIndex("idPastEvent");
                int idServerColIndex = cursor.getColumnIndex("idServer2");
                do {
                    try {
                        Date dateEventHappened = format.parse(cursor.getString(dateEventHappenedColIndex));
                        Date lastModifiedPastEvent = format.parse(cursor.getString(lastModifiedPastEventColIndex));
                        PastEvent pastEvent = new PastEvent(cursor.getInt(idPastEventColIndex), dateEventHappened, cursor.getInt(markColIndex), cursor.getInt(numberColIndex), cursor.getString(commentColIndex), lastModifiedPastEvent, cursor.getInt(idServerColIndex));
                        pastEventList.add(pastEvent);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } while (cursor.moveToNext());

            } else {
                cursor.close();
}
        }
        return pastEventList;
    }

    public void setNumberParameterEvent(Parameter parameter, int id, SQLiteDatabase db) {
        ContentValues updateValues = new ContentValues();
        updateValues.put("titleNumberParameter", parameter.getNameParameter());
        updateValues.put("isNumberParameter", 1);

        db.update("TableEvents", updateValues, "id = " + id, null);
    }

    public void setCommentParameterEvent(Parameter parameter, int id, SQLiteDatabase db) {
        ContentValues updateValues = new ContentValues();
        updateValues.put("titleCommentParameter", parameter.getNameParameter());
        updateValues.put("isCommentParameter", 1);

        db.update("TableEvents", updateValues, "id = " + id, null);
    }

    public void setRatingParameterEvent(Parameter parameter, int id, SQLiteDatabase db) {
        ContentValues updateValues = new ContentValues();
        updateValues.put("titleRatingParameter", parameter.getNameParameter());
        updateValues.put("isRatingParameter", 1);

        db.update("TableEvents", updateValues, "id = " + id, null);
    }

    public List<Event> selectEventOnId(SQLiteDatabase db, List<Integer> listIdEvent) {
        List<Event> eventList = new ArrayList<>();
        Cursor c = db.query("TableEvents", null, null, null ,null, null, null, null);
        if (c.moveToFirst()) {
            int nameEventColIndex = c.getColumnIndex("name");
            int descriptionColIndex = c.getColumnIndex("description");
            int isMainColIndex = c.getColumnIndex("isMain");
            int isNumberColIndex = c.getColumnIndex("isNumberParameter");
            int isMarkColIndex = c.getColumnIndex("isRatingParameter");
            int isCommentColIndex = c.getColumnIndex("isCommentParameter");
            int titleNumberColIndex = c.getColumnIndex("titleNumberParameter");
            int tilteCommentColIndex = c.getColumnIndex("titleCommentParameter");
            int titleRatingColIndex = c.getColumnIndex("titleRatingParameter");
            int lastModifiedEventColIndex = c.getColumnIndex("lastModifiedEvent");
            int idColIndex = c.getColumnIndex("id");
            int idServer1ColIndex = c.getColumnIndex("idServer");

            do {
                for (Integer id : listIdEvent) {
                    if (c.getInt(idColIndex) == id) {
                        Cursor cursor = db.query("TablePastEvent", null, "idEvent = ?", new String[]{String.valueOf(c.getInt(idColIndex))}, null, null, null, null);
                        List<PastEvent> list = new ArrayList<>();
                        if (cursor.moveToFirst()) {
                            int dateEventHappenedColIndex = cursor.getColumnIndex("dateEventHappened");
                            int markColIndex = cursor.getColumnIndex("ratingParameter");
                            int numberColIndex = cursor.getColumnIndex("numberParameter");
                            int commentColIndex = cursor.getColumnIndex("commentParameter");
                            int lastModifiedPastEventColIndex = cursor.getColumnIndex("lastModifiedPastEvent");
                            int idPastEventColIndex = cursor.getColumnIndex("idPastEvent");
                            int idServer2ColIndex = cursor.getColumnIndex("idServer2");
                            do {
                                try {
                                    Date dateEventHappened = format.parse(cursor.getString(dateEventHappenedColIndex));
                                    Date lastModifiedPastEvent = format.parse(cursor.getString(lastModifiedPastEventColIndex));
                                    PastEvent pastEvent = new PastEvent(cursor.getInt(idPastEventColIndex), dateEventHappened, cursor.getInt(markColIndex), cursor.getInt(numberColIndex), cursor.getString(commentColIndex), lastModifiedPastEvent, cursor.getInt(idServer2ColIndex));
                                    list.add(pastEvent);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }while (cursor.moveToNext());

                        }else {
                            cursor.close();
                        }
                        try {
                            Date lastModifiedEvent = format.parse(c.getString(lastModifiedEventColIndex));
                            Event event = new Event(c.getInt(idColIndex),
                                    c.getInt(isMainColIndex) == 0 ? false : true,
                                    c.getString(nameEventColIndex),
                                    c.getString(descriptionColIndex),
                                    list,
                                    lastModifiedEvent,
                                    c.getInt(isNumberColIndex) == 0 ? false : true,
                                    c.getInt(isCommentColIndex) == 0 ? false : true,
                                    c.getInt(isMarkColIndex) == 0 ? false : true,
                                    c.getInt(idColIndex),
                                    c.getString(titleNumberColIndex),
                                    c.getString(tilteCommentColIndex),
                                    c.getString(titleRatingColIndex));
                            eventList.add(event);
                        }catch (ParseException e) {

                        }
                        break;
                    }
                }
            }while (c.moveToNext());

        }else {
            c.close();
        }

        return eventList;
    }
}
