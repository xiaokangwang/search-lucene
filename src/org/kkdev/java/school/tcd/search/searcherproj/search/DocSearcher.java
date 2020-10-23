package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.FSDirectory;
import org.kkdev.java.school.tcd.search.searcherproj.data.Query;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchResult;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class DocSearcher {
    private SearchPolicy SearchPolicy;
    private String Location;

    private Analyzer analyzer = new StandardAnalyzer();

    public DocSearcher(org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy searchPolicy, String location) {
        SearchPolicy = searchPolicy;
        Location = location;
    }

    public List<SearchResult> Search(Query query) throws IOException, ParseException {
        LinkedList<SearchResult> sr = new LinkedList<>();
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Location)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BM25Similarity());
        BooleanQuery.Builder booleanQueryBuilder = new BooleanQuery.Builder();
        booleanQueryBuilder.add(CreateQuery("title",query.getText()), BooleanClause.Occur.SHOULD);
        booleanQueryBuilder.add(CreateQuery("text",query.getText()), BooleanClause.Occur.SHOULD);
        booleanQueryBuilder.add(CreateQuery("author",query.getText()), BooleanClause.Occur.SHOULD);
        TopDocs docs = searcher.search(booleanQueryBuilder.build(), SearchPolicy.getMaxDocument());
        for (ScoreDoc docsw:docs.scoreDocs
             ) {
            Integer id = Integer.valueOf(searcher.doc(docsw.doc).get("id"));
            sr.add(new SearchResult(id, docsw.score));
        }
        return sr;
    }

    private org.apache.lucene.search.Query CreateQuery(String field, String query) throws ParseException {
        QueryParser qp = new QueryParser(field,analyzer);
        org.apache.lucene.search.Query qr = qp.parse(query);
        switch (field){
            case "title":
                qr = new BoostQuery(qr,SearchPolicy.getTitleAuthority());
                break;
            case "text":
                qr = new BoostQuery(qr,SearchPolicy.getTextAuthority());
                break;
            case "author":
                qr = new BoostQuery(qr,SearchPolicy.getAuthorAuthority());
                break;
        }
        return qr;
    }
}
