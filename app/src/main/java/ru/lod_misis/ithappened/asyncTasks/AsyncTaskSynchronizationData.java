package ru.lod_misis.ithappened.asyncTasks;


import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class AsyncTaskSynchronizationData extends AsyncTask<List<Event>, Void, Void> {
    public static List<Event> syncNewEvent = new ArrayList<>();
    public static List<Event> syncUpdateEvent = new ArrayList<>();
    public static List<Event> syncDeleteEvent = new ArrayList<>();

    public static List<PastEvent> syncUpdatePastEvent = new ArrayList<>();
    public static List<PastEvent> syncNewPastEvent = new ArrayList<>();
    public static List<PastEvent> syncDeletePastEvent = new ArrayList<>();

    @Override
    protected Void doInBackground(List<Event>... params) {
        Log.d("Lod", "Старт синхронизации");
        List<Event> listEventFromDB = Controller.getEventListFromDB();
        List<Event> listEventFromServer = params[0];

        for (Event eventFromServer : listEventFromServer) {
            for (Event eventFromDB : listEventFromDB) {
                if (eventFromServer.getIdServer() == eventFromDB.getIdServer()) {
                    boolean eventFromServerAfterEventFromDB = eventFromServer.getLastModified().after(eventFromDB.getLastModified());
                    boolean eventFromDBAfterEventFromServer = eventFromDB.getLastModified().after(eventFromServer.getLastModified());

                    listEventFromDB.remove(eventFromDB);
                    
                    if (eventFromServer.isDeleted() && eventFromDB.isDeleted()) {
                        break;
                    }else if (eventFromServer.isDeleted() && eventFromServerAfterEventFromDB) {
                        deleteEventFromDB(eventFromDB);
                        break;
                    }else if (eventFromDB.isDeleted() && eventFromDBAfterEventFromServer) {
                        deleteEventOnServer(eventFromServer);
                        break;
                    }

                    if (eventFromServerAfterEventFromDB) {
                        updateEventInDB(eventFromDB, eventFromServer);
                    }else if (eventFromDBAfterEventFromServer) {
                        updateEventOnServer(eventFromDB);
                    }
                    updatePastEvent(eventFromDB.getListHappenedEvent(), eventFromServer.getListHappenedEvent());
                }
            }

            if (!eventFromServer.isDeleted()) {
                addEventInDB(eventFromServer);
            }
        }

        for (Event newEventFromDB : listEventFromDB) {
            syncNewEvent.add(newEventFromDB);
            for (PastEvent newPastEvent : newEventFromDB.getListHappenedEvent()) {
                syncNewPastEvent.add(newPastEvent);
            }
        }

        startAsyncTasksThatPostDataOnServer();

        return null;
    }

    private void addEventInDB(Event eventFromServer) {
        Controller.saveEvent(eventFromServer);
    }

    private void updateEventOnServer(Event eventFromDB) {
        syncUpdateEvent.add(eventFromDB);
    }

    private void updatePastEvent(List<PastEvent> listHappenedEventFromDB, List<PastEvent> listHappenedEventFromServer) {
        for (PastEvent pastEventFromServer : listHappenedEventFromServer) {
            for (PastEvent pastEventFromDB : listHappenedEventFromDB) {
                if (pastEventFromServer.getIdServer() == pastEventFromDB.getIdServer()) {
                    boolean pastEventFromServerAfterPastEventFromDB = pastEventFromServer.getLastModified().after(pastEventFromDB.getLastModified());
                    boolean pastEventFromDBAfterPastEventFromServer = pastEventFromDB.getLastModified().after(pastEventFromServer.getLastModified());
                    
                    listHappenedEventFromDB.remove(pastEventFromDB);
                    
                    if (pastEventFromServer.isDeleted() && pastEventFromServerAfterPastEventFromDB) {
                        pastEventFromDB.setIsDeleted(true);
                        Controller.updatePastEvent(pastEventFromDB);
                        break;                        
                    }else if (pastEventFromDB.isDeleted() && pastEventFromDBAfterPastEventFromServer) {
                        syncDeletePastEvent.add(pastEventFromServer);
                        break;
                    }else if (pastEventFromDB.isDeleted() && pastEventFromServer.isDeleted()) {
                        break;
                    }
                    
                    if (pastEventFromServerAfterPastEventFromDB) {
                        pastEventFromDB.setMark(pastEventFromServer.getMark());
                        pastEventFromDB.setComment(pastEventFromServer.getComment());
                        pastEventFromDB.setNumber(pastEventFromServer.getNumber());
                        pastEventFromDB.setLastModified(pastEventFromServer.getLastModified());
                        pastEventFromDB.setDateEventHappened(pastEventFromServer.getDateEventHappened());
                        Controller.updatePastEvent(pastEventFromDB);
                    }else if (pastEventFromDBAfterPastEventFromServer) {
                        syncUpdatePastEvent.add(pastEventFromDB);
                    }
                }
            }
        }
    }

    private void updateEventInDB(Event eventFromDB, Event eventFromServer) {
        eventFromDB.setName(eventFromServer.getName());
        eventFromDB.setDescription(eventFromServer.getDescription());
        eventFromDB.setIsComment(eventFromServer.isComment());
        eventFromDB.setIsNumber(eventFromServer.isNumber());
        eventFromDB.setIsMark(eventFromServer.isMark());
        eventFromDB.setLastModified(eventFromServer.getLastModified());
        Controller.updateEvent(eventFromDB);
    }

    private void deleteEventOnServer(Event eventFromServer) {
        syncDeleteEvent.add(eventFromServer);
    }

    private void deleteEventFromDB(Event eventFromDB) {
        eventFromDB.setIsDeleted(true);
        for (PastEvent pastEvent : eventFromDB.getListHappenedEvent()) {
            pastEvent.setIsDeleted(true);
            Controller.updatePastEvent(pastEvent);
        }
        Controller.updateEvent(eventFromDB);
    }

    private void startAsyncTasksThatPostDataOnServer() {
        try {
            AsyncTaskDeleteEventOnServer asyncTaskDeleteEventOnServer = new AsyncTaskDeleteEventOnServer();
            asyncTaskDeleteEventOnServer.execute(syncDeleteEvent.toArray(new Event[syncDeleteEvent.size()])).get();

            AsyncTaskDeletePastEventOnServer asyncTaskDeletePastEventOnServer = new AsyncTaskDeletePastEventOnServer();
            asyncTaskDeletePastEventOnServer.execute(syncDeletePastEvent.toArray(new PastEvent[syncDeletePastEvent.size()])).get();

            AsyncTaskPostNewEventOnServer asyncTaskPostNewEventOnServer = new AsyncTaskPostNewEventOnServer();
            asyncTaskPostNewEventOnServer.execute(syncNewEvent.toArray(new Event[syncNewEvent.size()])).get();

            AsyncTaskPostNewPastEventOnServer asyncTaskPostNewPastEventOnServer = new AsyncTaskPostNewPastEventOnServer();
            asyncTaskPostNewPastEventOnServer.execute(syncNewPastEvent.toArray(new PastEvent[syncNewPastEvent.size()])).get();

            AsyncTaskUpdateEventOnServer asyncTaskUpdateEventOnServer = new AsyncTaskUpdateEventOnServer();
            asyncTaskUpdateEventOnServer.execute(syncUpdateEvent.toArray(new Event[syncUpdateEvent.size()])).get();

            AsyncTaskUpdatePastEventOnServer asyncTaskUpdatePastEventOnServer = new AsyncTaskUpdatePastEventOnServer();
            asyncTaskUpdatePastEventOnServer.execute(syncUpdatePastEvent.toArray(new PastEvent[syncUpdatePastEvent.size()])).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
