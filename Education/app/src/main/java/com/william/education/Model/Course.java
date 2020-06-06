package com.william.education.Model;

public class Course {
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getPrice() {
        return price;
    }

    public Course(Integer id, String name, String content, String picture, Integer price) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.picture = picture;
        this.price = price;
    }

    private String name,content,picture;
    private Integer id,price;

}
