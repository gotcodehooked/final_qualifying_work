package com.rseu.final_qualifying_work.model;



import org.bson.types.ObjectId;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Discipline extends RealmObject {

    @PrimaryKey
    private ObjectId _id = new ObjectId();
    private String disciplineName;

    public Discipline(){

    }

    public Discipline(String disciplineName){
        this.disciplineName = disciplineName;
    }

    public String getDisciplineName() {
        return disciplineName;
    }

    public void setDisciplineName(String disciplineName) {
        this.disciplineName = disciplineName;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discipline that = (Discipline) o;

        if (!Objects.equals(_id, that._id)) return false;
        return Objects.equals(disciplineName, that.disciplineName);
    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (disciplineName != null ? disciplineName.hashCode() : 0);
        return result;
    }
}
