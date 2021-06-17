package com.rseu.final_qualifying_work;

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
    public void deleteData(Realm realm, T object , String fieldName, String fieldValue) {

        realm.beginTransaction();
                RealmObject realmObjects = realm.where(object.getClass()).equalTo("disciplineName", fieldName).findFirst();
                if (realmObjects != null) {
                    realmObjects.deleteFromRealm();
                }
        realm.commitTransaction();
    }

    @Override
    public void updateData(Realm realm, T object) {

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (object != null) {
                    realm.copyToRealmOrUpdate(object);
                }
            }

        });
    }

    @Override
    public RealmResults<T> readData(Realm realm, Class<T> tClass) {

        realm.beginTransaction();
        RealmResults<? extends RealmObject> realmResults = realm.where(tClass).findAll();
        realm.commitTransaction();
        return (RealmResults<T>) realmResults;
    }



}
