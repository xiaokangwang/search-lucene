package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class DocumentHolder {
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
