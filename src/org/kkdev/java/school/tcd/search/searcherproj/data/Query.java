package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class Query {
    private Integer Id;
    private String Text;

    public Query(Integer id, String text) {
        Id = id;
        Text = text;
    }

    public Query(HashMap<String, String> data) {
        Id = Integer.valueOf(data.get(".I"));
        Text = data.get(".W");
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getText() {

        return Text.replace('?',' ').replace('*',' ');
    }

    public void setText(String text) {
        Text = text;
    }

    @Override
    public String toString() {
        return "Query{" +
                "Id=" + Id +
                ", Text='" + Text + '\'' +
                '}';
    }
}
