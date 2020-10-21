package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.junit.jupiter.api.Test;
import org.kkdev.java.school.tcd.search.searcherproj.data.DocumentHolder;
import org.kkdev.java.school.tcd.search.searcherproj.data.DotFileArray;
import org.kkdev.java.school.tcd.search.searcherproj.data.loader.DotFileLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DocIndexCreatorTest {

    @Test
    void constructIndex() throws IOException {
        File qry = new File("./data/cran.all.1400");
        DotFileArray dfa = DotFileLoader.LoadDotFile(qry);
        DocumentHolder dc = new DocumentHolder(new DotFileArray(dfa.getContent()));
        DocIndexCreator docc = new DocIndexCreator("./index",dc);
        docc.ConstructIndex();

    }
}