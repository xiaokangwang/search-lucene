package org.kkdev.java.school.tcd.search.searcherproj.data.loader;

import org.junit.jupiter.api.Test;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class DotFileLoaderTest {

    @Test
    void loadQryDotFile() throws FileNotFoundException {
        File qry = new File("./data/cran.qry");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);
        dfa.getContent();
    }

    @Test
    void loadDocDotFile() throws FileNotFoundException {
        File qry = new File("./data/cran.all.1400");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);
        //241
        dfa.getContent();
    }
}