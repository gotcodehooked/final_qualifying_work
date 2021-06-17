package com.rseu.final_qualifying_work.model;


import android.os.Parcel;
import android.os.Parcelable;

import org.bson.types.ObjectId;


import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;



public class Group extends RealmObject {


    @PrimaryKey
    private ObjectId objectId = new ObjectId();
    private String name;

    private RealmList<Student> studentsList;

    public Group(String name) {
        this.name = name;
    }

    public Group() {
    }

    public RealmList<Student> getStudentsList() {
        return studentsList;
    }

    private String groupType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public void setStudentsList(RealmList<Student> studentsList) {
        this.studentsList = studentsList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!Objects.equals(objectId, group.objectId))
            return false;
        return Objects.equals(name, group.name);
    }

    @Override
    public int hashCode() {
        int result = objectId != null ? objectId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

}
