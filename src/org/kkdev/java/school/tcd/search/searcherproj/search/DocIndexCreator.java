package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.kkdev.java.school.tcd.search.searcherproj.data.Document;
import org.kkdev.java.school.tcd.search.searcherproj.data.DocumentHolder;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

public class DocIndexCreator {
    private String Location;
    private DocumentHolder hd;

    public DocIndexCreator(String location, DocumentHolder hd) {
        Location = location;
        this.hd = hd;
    }

    public void ConstructIndex() throws IOException {
        Analyzer analyzer = new StandardAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(Location));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        class Itel implements BiConsumer<Integer,Document> {

            @Override
            public void accept(Integer integer, Document document) {
                org.apache.lucene.document.Document LDoc = new org.apache.lucene.document.Document();
                LDoc.add(new Field("id", String.valueOf(document.getId()), TextField.TYPE_STORED));
                LDoc.add(new Field("author", String.valueOf(document.getAuthor()), TextField.TYPE_STORED));
                LDoc.add(new Field("date", String.valueOf(document.getDateString()), TextField.TYPE_STORED));
                LDoc.add(new Field("title", String.valueOf(document.getTitle()), TextField.TYPE_STORED));
                LDoc.add(new Field("text", String.valueOf(document.getText()), TextField.TYPE_STORED));
                try {
                    iwriter.addDocument(LDoc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public BiConsumer<Integer, Document> andThen(BiConsumer<? super Integer, ? super Document> biConsumer) {
                return null;
            }
        }
        hd.getContains().forEach(new Itel());
        iwriter.close();
    }
}
