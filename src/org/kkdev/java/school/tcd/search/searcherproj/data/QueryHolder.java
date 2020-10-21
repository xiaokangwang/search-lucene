package org.kkdev.java.school.tcd.search.searcherproj.data;

import java.util.HashMap;

public class QueryHolder {
    private HashMap<Integer,Query> contains = new HashMap<>();

    public QueryHolder(HashMap<Integer, Query> contains) {
        this.contains = contains;
    }

    public QueryHolder(DotFileArray dfa) {
        for (DotFile df:dfa.getContent()
        ) {
            Query doc = new Query(df.getData());
            contains.put(doc.getId(),doc);
        }
    }
}
