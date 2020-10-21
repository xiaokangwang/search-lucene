package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class DocumentHolder {
    public HashMap<Integer, Document> getContains() {
        return contains;
    }

    public void setContains(HashMap<Integer, Document> contains) {
        this.contains = contains;
    }

    private HashMap<Integer,Document> contains = new HashMap<>();

    public DocumentHolder(HashMap<Integer, Document> contains) {
        this.contains = contains;
    }

    public DocumentHolder(DotFileArray dfa) {
        for (DotFile df:dfa.getContent()
             ) {
            Document doc = new Document(df.getData());
            contains.put(doc.getId(),doc);
        }
    }
}
