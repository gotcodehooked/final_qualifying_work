package com.rseu.final_qualifying_work;

import com.rseu.final_qualifying_work.model.Group;

import org.jetbrains.annotations.NotNull;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DBOperationsImpl<T extends RealmObject> implements DBOperations<T> {

    @Override
    public void createData(Realm realm, T object) {

        realm.beginTransaction();
        realm.insert(object);
        realm.commitTransaction();

    }

    @Override
    public void deleteData(Realm realm, Class<T> tClass, String fieldName, String fieldValue) {

        RealmObject realmObjects = realm.where(tClass).equalTo(fieldName, fieldValue).findFirst();
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if (realmObjects != null) {
                    realmObjects.deleteFromRealm();
                }
            }
        });
    }

    @Override
    public void updateData(Realm realm, Class<T> tClass, String fieldName, String fieldValue, T saveObject) {

        realm.beginTransaction();

        T object = realm.where(tClass).equalTo(fieldName, fieldValue).findFirst();
        if (object != null) {
            realm.insert(object);
        }
        realm.commitTransaction();
    }

    @Override
    public RealmResults<T> readData(Realm realm, Class<T> tClass) {

        realm.beginTransaction();
        RealmResults<? extends RealmObject> realmResults = realm.where(tClass).findAll();
        realm.commitTransaction();
        return (RealmResults<T>) realmResults;
    }
}
