package org.kkdev.java.school.tcd.search.searcherproj;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardTokenizer;

public class Ayer {
    public static Analyzer analyzer = new EnglishAnalyzer();
}
