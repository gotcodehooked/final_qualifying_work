package com.rseu.final_qualifying_work.model;

import org.bson.types.ObjectId;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Lesson extends RealmObject {

    @PrimaryKey
    private ObjectId objectId = new ObjectId();
    private String lessonName;
    private Group group;
    private Discipline discipline;
    private String dateTime;

    public Lesson(){

    }
    public Lesson(String lessonName, Group group,Discipline discipline){
        this.lessonName = lessonName;
        this.group = group;
        this.discipline = discipline;

    }

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lesson lesson = (Lesson) o;

        if (!objectId.equals(lesson.objectId)) return false;
        return lessonName.equals(lesson.lessonName);
    }

    @Override
    public int hashCode() {
        int result = objectId.hashCode();
        result = 31 * result + lessonName.hashCode();
        return result;
    }

}
