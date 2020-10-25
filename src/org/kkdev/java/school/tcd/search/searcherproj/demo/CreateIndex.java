package org.kkdev.java.school.tcd.search.searcherproj.demo;

import org.kkdev.java.school.tcd.search.searcherproj.data.DocumentHolder;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.DotFileLoader;
import org.kkdev.java.school.tcd.search.searcherproj.search.DocIndexCreator;

import java.io.File;
import java.io.IOException;

public class CreateIndex {
    public static void main(String[] Args) throws IOException {
        File qry = new File("./data/cran.all.1400");
        System.out.println("Loading Files....");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);

        System.out.println("Parsing Files into document....");
        DocumentHolder dc = new DocumentHolder(new DotFileArray(dfa.getContent()));
        System.out.println("Constructing index....");
        DocIndexCreator docc = new DocIndexCreator("./index",dc);

        docc.ConstructIndex();
        System.out.println("Index Created!");
    }
}
