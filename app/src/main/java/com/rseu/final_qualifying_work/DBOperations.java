package com.rseu.final_qualifying_work;

import java.util.Collection;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public interface DBOperations<T extends RealmObject> {

    public  void createData (Realm realm, T object);

    public  void deleteData(Realm realm, T object, String fieldName, String fieldValue);

    public  void updateData(Realm realm, T object);

    public  RealmResults<T>  readData(Realm realm, Class<T> tClass) ;

}
