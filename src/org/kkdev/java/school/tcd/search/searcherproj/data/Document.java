package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class Document {
    public Document(Integer id, String title, String author, String dateString, String text) {
        Id = id;
        Title = title;
        Author = author;
        DateString = dateString;
        Text = text;
    }

    public Document(HashMap<String, String> data) {
        Id = Integer.valueOf(data.get(".I"));
        Title = data.get(".T");
        Author = data.get(".A");
        DateString = data.get(".B");
        Text = data.get(".W");
    }

    private Integer Id;
    private String Title;
    private String Author;
    private String DateString;
    private String Text;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getDateString() {
        return DateString;
    }

    public void setDateString(String dateString) {
        DateString = dateString;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }
}
