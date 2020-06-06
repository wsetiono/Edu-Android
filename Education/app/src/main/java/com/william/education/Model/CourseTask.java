package com.william.education.Model;

public class CourseTask {

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getNote() {
        return note;
    }

    public CourseTask(Integer id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    private String title,note;
    private Integer id;

}
