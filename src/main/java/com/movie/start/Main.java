package com.movie.start;

import com.movie.config.LuceneConfig;
import com.movie.domain.Movie;
import com.movie.domain.MovieIndexFunction;
import com.movie.index.Indexer;
import com.movie.search.Searcher;
import com.movie.view.Printer;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String END_COMMAND = "0";

    public static void main(String[] args) throws Exception {

        logger.info("start...");
        LuceneConfig luceneConfig = new LuceneConfig();
        Indexer indexer = new Indexer(luceneConfig.getDirectory(), luceneConfig.getIndexWriterConfig());
        Searcher searcher = new Searcher(luceneConfig.getIndexSearcher(), luceneConfig.getHitsPerPage());
        logger.info("complete configuration");

        logger.info("indexing...");
        long indexStartTime = System.currentTimeMillis();
        String fileName = "/Users/won/workspace/study-serving/movie.csv";

        List list = indexer.parse(fileName, Movie.class);

        Function movieIndexFunction = new MovieIndexFunction();

        indexer.makeIndex(list, movieIndexFunction, Movie.class);

        long indexEndTime = System.currentTimeMillis();
        logger.info("complete indexing  > "+ (indexEndTime - indexStartTime) + " ms");

        Printer printer = new Printer();

        logger.info("searching...");
        String cmd = "";

        Map searchResult = null;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(!END_COMMAND.equals(cmd = bufferedReader.readLine())){

            searchResult = searcher.search(cmd);

            printer.print(searchResult);
        }
    }
}
