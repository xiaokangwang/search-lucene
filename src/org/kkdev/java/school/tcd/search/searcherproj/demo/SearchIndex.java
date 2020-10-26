package org.kkdev.java.school.tcd.search.searcherproj.demo;

import org.apache.lucene.queryparser.classic.ParseException;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;
import org.kkdev.java.school.tcd.search.searcherproj.data.QueryHolder;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchPolicy;
import org.kkdev.java.school.tcd.search.searcherproj.data.SearchResult;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.DotFileLoader;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.SearchResultOutputer;
import org.kkdev.java.school.tcd.search.searcherproj.search.DocSearcher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SearchIndex {
    public static void main(String[] Args) throws IOException, ParseException {
        System.out.println("Defining Search Policy....");
        SearchPolicy sp = new SearchPolicy(Float.valueOf((float) 0.1f), Float.valueOf((float) 0.8f), Float.valueOf((float) 0.60f),Float.valueOf((float) 0.02f), 200);
        System.out.println("Opening index for read....");
        String IndexLocation = "./index";
        DocSearcher ds = new DocSearcher(sp, IndexLocation);
        System.out.println("Loading Query file....");
        File qry = new File("./data/cran.qry");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);
        System.out.println("Parsing Query file....");
        QueryHolder qh = new QueryHolder(new DotFileArray(dfa.getContent()));
        System.out.println("Executing Query file....");
        SearchResultOutputer sro = new SearchResultOutputer();
        Integer seqid = 1;
        for (Integer i : qh.getContains().keySet()) {
            //if (i == 225) {
            List<SearchResult> sr = ds.Search(qh.getContains().get(i));
            System.out.println(qh.getContains().get(i).toString());;
            sro.OutputSearchResult(sr, seqid);
            //}
            for (SearchResult srw : sr){
                System.out.println(srw.toString());;
            }
            seqid++;

        }
        Files.deleteIfExists(Paths.get("result.txt"));
        System.out.println("Writing Result To file....");
        PrintWriter out = new PrintWriter("result.txt");
        out.print(sro.getSb().toString());
        out.close();
    }
}
