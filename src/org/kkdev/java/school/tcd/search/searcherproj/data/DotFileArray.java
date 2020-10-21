package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.LinkedList;

public class DotFileArray {
    LinkedList<DotFile> Content;

    public DotFileArray(LinkedList<DotFile> content) {
        Content = content;
    }
    public DotFileArray() {
        Content = new LinkedList<>();
    }

    public LinkedList<DotFile> getContent() {
        return Content;
    }

    public void setContent(LinkedList<DotFile> content) {
        Content = content;
    }
}
