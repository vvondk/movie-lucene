package com.movie.start;

import com.movie.domain.Movie;
import com.movie.index.Indexer;
import com.movie.search.Searcher;
import com.movie.util.LuceneConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        LuceneConfig luceneConfig = new LuceneConfig();
        logger.info("start indexing...");
        Indexer indexer = new Indexer(luceneConfig.getDirectory(), luceneConfig.getAnalyzer(), luceneConfig.getIndexWriterConfig());
        List<Movie> movieList = indexer.parse();
        indexer.makeIndex(movieList);

        Searcher searcher = new Searcher(luceneConfig.getIndexSearcher(), luceneConfig.getHitsPerPage());

        logger.info("start search...");
        String cmd = "";
        while(!(cmd = bufferedReader.readLine()).equals("0")){
            searcher.search(cmd);
            logger.info("=============================");

        }
    }
}
