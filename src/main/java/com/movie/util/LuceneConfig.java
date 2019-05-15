package com.movie.util;

import com.movie.analyzer.NGramAnalyzer;
import lombok.Getter;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Paths;

@Getter
public class LuceneConfig {

    private static Logger logger = LoggerFactory.getLogger(LuceneConfig.class);
    private Directory directory;
    private IndexWriterConfig indexWriterConfig;
    private Analyzer analyzer;

    private IndexReader indexReader;
    private IndexSearcher indexSearcher;

    private int hitsPerPage;

    public LuceneConfig() {
        try {
            String directoryPath = PropertiesReader.get("movie.index.directory.path");

            directory = FSDirectory.open(Paths.get(directoryPath));
            analyzer = new NGramAnalyzer();
            indexWriterConfig = new IndexWriterConfig(analyzer);
            indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            indexReader = DirectoryReader.open(directory);
            indexSearcher = new IndexSearcher(indexReader);

            hitsPerPage = 10;
        }catch (IOException e){
            logger.error("config error {}", e);
        }
    }
}
