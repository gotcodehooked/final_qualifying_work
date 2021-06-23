package com.rseu.final_qualifying_work;

import android.icu.text.Edits;
import android.widget.Toast;

import com.rseu.final_qualifying_work.model.Group;
import com.rseu.final_qualifying_work.model.Student;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class DBOGroupImpl implements DBOperations<Group> {
    @Override
    public void createData(Realm realm, Group object) {

        realm.beginTransaction();
        realm.insert(object);
        realm.commitTransaction();

    }

    @Override
    public void deleteData(Realm realm, Class<Group> groupClass, String fieldName, String fieldValue) {


        realm.beginTransaction();
        RealmObject realmObjects = realm.where(groupClass).equalTo(fieldName, fieldValue).findFirstAsync();
        if (realmObjects != null) {
            realmObjects.deleteFromRealm();
        }

        realm.commitTransaction();

    }

    @Override
    public void updateData(Realm realm, Class<Group> groupClass, String fieldName, String fieldValue, Group saveObject) {

        Group group = realm.where(groupClass)
                .equalTo(fieldName, fieldValue)
                .findFirst();

        if (group != null) {

            saveObject.getStudentsList().sort(fieldName);
            group.getStudentsList().sort(fieldName);
            Iterator<Student> iterator = saveObject.getStudentsList().iterator();
            Iterator<Student> iterator1 = group.getStudentsList().iterator();

            realm.beginTransaction();

            while (iterator.hasNext()) {

                if (!(iterator1.next().equals(iterator.next()))) {
                    group.getStudentsList().add(iterator.next());
                }
            }

            group.setName(saveObject.getName());
            group.setGroupType(saveObject.getGroupType());
            realm.copyToRealmOrUpdate(group);
            realm.commitTransaction();
        }
    }

    @Override
    public RealmResults<Group> readData(Realm realm, Class<Group> groupClass) {
        realm.beginTransaction();
        RealmResults<? extends RealmObject> realmResults = realm.where(groupClass).findAll();
        realm.commitTransaction();
        return (RealmResults<Group>) realmResults;
    }
}
