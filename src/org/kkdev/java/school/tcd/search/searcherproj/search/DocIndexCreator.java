package org.kkdev.java.school.tcd.search.searcherproj.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.kkdev.java.school.tcd.search.searcherproj.Ayer;
import org.kkdev.java.school.tcd.search.searcherproj.data.Document;
import org.kkdev.java.school.tcd.search.searcherproj.data.DocumentHolder;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.function.BiConsumer;

public class DocIndexCreator {
    private String Location;
    private DocumentHolder hd;
    private Similarity sim = new BM25Similarity();

    public DocIndexCreator(String location, DocumentHolder hd) {
        Location = location;
        this.hd = hd;
    }

    public void ConstructIndex() throws IOException {
        Analyzer analyzer = Ayer.analyzer;
        Directory directory = FSDirectory.open(Paths.get(Location));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setSimilarity(sim);
        IndexWriter iwriter = new IndexWriter(directory, config);


        // This is the field setting for metadata field.
        FieldType fieldTypeMetadata = new FieldType();
        fieldTypeMetadata.setOmitNorms( true );
        fieldTypeMetadata.setIndexOptions( IndexOptions.DOCS );
        fieldTypeMetadata.setStored( true );
        fieldTypeMetadata.setTokenized( false );
        fieldTypeMetadata.freeze();

// This is the field setting for normal text field.
        FieldType fieldTypeText = new FieldType();
        fieldTypeText.setIndexOptions( IndexOptions.DOCS_AND_FREQS_AND_POSITIONS );
        fieldTypeText.setStoreTermVectors( true );
        fieldTypeText.setStoreTermVectorPositions( true );
        fieldTypeText.setTokenized( true );
        fieldTypeText.setStored( true );
        fieldTypeText.freeze();

        class Itel implements BiConsumer<Integer,Document> {

            @Override
            public void accept(Integer integer, Document document) {
                org.apache.lucene.document.Document LDoc = new org.apache.lucene.document.Document();
                LDoc.add(new Field("id", String.valueOf(document.getId()), fieldTypeMetadata));
                LDoc.add(new Field("author", String.valueOf(document.getAuthor()), fieldTypeText));
                LDoc.add(new Field("date", String.valueOf(document.getDateString()), fieldTypeText));
                LDoc.add(new Field("title", String.valueOf(document.getTitle()), fieldTypeText));
                LDoc.add(new Field("text", String.valueOf(document.getText()), fieldTypeText));
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
