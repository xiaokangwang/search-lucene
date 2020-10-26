package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.similarities.*;
import org.apache.lucene.store.FSDirectory;
import org.kkdev.java.school.tcd.search.searcherproj.Ayer;
import org.kkdev.java.school.tcd.search.searcherproj.data.Query;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchResult;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DocSearcher {
    private SearchPolicy SearchPolicy;
    private String Location;

    private Analyzer analyzer = Ayer.analyzer;
    ;

    public Similarity getSim() {
        return sim;
    }

    public void setSim(Similarity sim) {
        this.sim = sim;
    }

    private Similarity sim = new BM25Similarity();

    public DocSearcher(org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy searchPolicy, String location) {
        SearchPolicy = searchPolicy;
        Location = location;
    }

    public List<SearchResult> Search(Query query) throws IOException, ParseException {
        LinkedList<SearchResult> sr = new LinkedList<>();
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(Location)));
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(sim);


        HashMap<String, Float> boostedScores = new HashMap<String, Float>();
        boostedScores.put("title", SearchPolicy.getTitleAuthority());
        boostedScores.put("author", SearchPolicy.getAuthorAuthority());
        boostedScores.put("date", SearchPolicy.getDateAuthority());
        boostedScores.put("text", SearchPolicy.getTextAuthority());
        MultiFieldQueryParser parser = new MultiFieldQueryParser(
                new String[]{"title", "author", "date", "text"},
                analyzer, boostedScores);

        org.apache.lucene.search.Query qu = parser.parse(QueryParser.escape(query.getText()));

        TopDocs docs = searcher.search(qu, SearchPolicy.getMaxDocument());
        Integer Rank = 0;
        for (ScoreDoc docsw : docs.scoreDocs
        ) {
            Integer id = Integer.valueOf(searcher.doc(docsw.doc).get("id"));
            sr.add(new SearchResult(id, docsw.score,Rank));
            Rank+=1;
        }
        return sr;
    }

    private org.apache.lucene.search.Query CreateQuery(String field, String query) throws ParseException {
        QueryParser qp = new QueryParser(field, analyzer);
        org.apache.lucene.search.Query qr = qp.parse(query);
        switch (field) {
            case "title":
                qr = new BoostQuery(qr, SearchPolicy.getTitleAuthority());
                break;
            case "text":
                qr = new BoostQuery(qr, SearchPolicy.getTextAuthority());
                break;
            case "author":
                qr = new BoostQuery(qr, SearchPolicy.getAuthorAuthority());
                break;
        }
        return qr;
    }

    private String ExplainDoc(org.apache.lucene.search.Query qu, Integer id, IndexSearcher searcher) throws ParseException, IOException {
        org.apache.lucene.search.Query qud = CreateQuery("id", String.valueOf(id));
        TopDocs docs = searcher.search(qud, 1);
        Integer docid = docs.scoreDocs[0].doc;
        return searcher.explain(qu, docid).toString();
    }
}
