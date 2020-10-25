package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.jupiter.api.Test;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;
import org.kkdev.java.school.tcd.search.searcherproj.data.QueryHolder;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchResult;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.DotFileLoader;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.SearchResultOutputer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DocSearcherTest {

    @Test
    void search() throws IOException, ParseException {
        SearchPolicy sp = new SearchPolicy(Float.valueOf((float) 0.04f), Float.valueOf((float) 0.35f), Float.valueOf((float) 0.65f),Float.valueOf((float) 0.02f), 200);
        String IndexLocation = "./index";
        DocSearcher ds = new DocSearcher(sp, IndexLocation);

        File qry = new File("./data/cran.qry");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);
        QueryHolder qh = new QueryHolder(new DotFileArray(dfa.getContent()));

        SearchResultOutputer sro = new SearchResultOutputer();
        Integer seqid = 1;
        for (Integer i : qh.getContains().keySet()) {
            //if (i == 225) {
                List<SearchResult> sr = ds.Search(qh.getContains().get(i));

                sro.OutputSearchResult(sr, seqid);
            //}
            seqid++;

        }
        Files.deleteIfExists(Paths.get("result.txt"));
        PrintWriter out = new PrintWriter("result.txt");
        out.print(sro.getSb().toString());
        out.close();
    }
}