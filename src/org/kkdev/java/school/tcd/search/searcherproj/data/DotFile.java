package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class DotFile {
    public DotFile(HashMap<String, String> data) {
        Data = data;
    }

    public DotFile() {
        Data = new HashMap<>();
    }

    public HashMap<String, String> getData() {
        return Data;
    }

    public void setData(HashMap<String, String> data) {
        Data = data;
    }

    private HashMap<String,String> Data;
}
