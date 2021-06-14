package com.rseu.final_qualifying_work;

import io.realm.Realm;

public interface DataBaseOperations {

    public void createData(Realm realm);

    public void deleteData(Realm realm);

    public void updateData(Realm realm);

    public void readData(Realm realm);
}
