package com.rseu.final_qualifying_work.model;

import org.bson.types.ObjectId;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;


@RealmClass(embedded=true)
public class Student extends RealmObject  {



    private ObjectId objectId = new ObjectId();

    private String firstName;
    private String secondName;


    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student students = (Student) o;

        if (!objectId.equals(students.objectId)) return false;
        if (!Objects.equals(firstName, students.firstName))
            return false;
        return Objects.equals(secondName, students.secondName);
    }

    @Override
    public int hashCode() {
        int result = objectId.hashCode();
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (secondName != null ? secondName.hashCode() : 0);
        return result;
    }


}
