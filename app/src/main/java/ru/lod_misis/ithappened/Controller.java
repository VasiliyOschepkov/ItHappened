package ru.lod_misis.ithappened;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.Parameter;
import ru.lod_misis.ithappened.model.PastEvent;

public class Controller {
    private static Realm realm = Realm.getDefaultInstance();

    public static RealmResults<Event> getEvents() {
        return realm.where(Event.class).findAll();
    }

    public static Event getEvent(long id) {
        return realm.where(Event.class).equalTo("id", id).findFirst();
    }

    public static void saveEvent(final String nameEvent, final List<Parameter> parameters) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long id = UUID.randomUUID().hashCode();
                Event event = realm.createObject(Event.class, id);
                event.setName(nameEvent);

                RealmList<Parameter> parameterRealmList = event.getParameters();
                for (Parameter parameter : parameters) {
                    parameterRealmList.add(parameter);
                }
            }
        });
    }

    public static void updateEvent(final Event event, final String nameEvent, final List<Parameter> parameters) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                event.setName(nameEvent);
                RealmList<Parameter> parameterRealmList = event.getParameters();
                for (Parameter parameter : parameters) {
                    parameterRealmList.add(parameter);
                }
            }
        });
    }

    public static void savePastEvent(final String comment, final int number, final int mark) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                long id = UUID.randomUUID().hashCode();
                PastEvent pastEvent = realm.createObject(PastEvent.class, id);
                pastEvent.setComment(comment);
                pastEvent.setNumber(number);
                pastEvent.setMark(mark);
                pastEvent.setDateEvent(new Date());
                pastEvent.setDelete(false);
            }
        });
    }
}
