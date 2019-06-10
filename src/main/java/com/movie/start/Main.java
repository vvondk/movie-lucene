package com.movie.start;

import com.movie.config.LuceneConfig;
import com.movie.domain.Movie;
import com.movie.domain.SearchResult;
import com.movie.index.Indexer;
import com.movie.index.MovieIndexFunction;
import com.movie.search.Searcher;
import com.movie.util.PropertiesReader;
import com.movie.view.Printer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Function;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String END_COMMAND = "0";

    private static LuceneConfig luceneConfig;
    private static Indexer indexer;
    private static Searcher searcher;

    static {
        luceneConfig = new LuceneConfig();
        indexer = new Indexer(luceneConfig.getDirectory(), luceneConfig.getIndexWriterConfig());
        searcher = new Searcher(luceneConfig.getIndexSearcher(), luceneConfig.getHitsPerPage(), luceneConfig.getQueryParser(), luceneConfig.getAnalyzer());
    }

    public static void main(String[] args) throws Exception {
        String cmd = "";
        logger.info("Do you want to index? (yes)");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        cmd = bufferedReader.readLine();

        if("yes".equals(cmd.toLowerCase())) {
            String fileName = PropertiesReader.get("movie.index.file");
            Function movieIndexFunction = new MovieIndexFunction();
            indexer.run(fileName, movieIndexFunction, Movie.class);
        }

        Printer printer = new Printer();
        logger.info("start searching...");
        SearchResult searchResult = null;

        while(!END_COMMAND.equals(cmd = bufferedReader.readLine())){
            searchResult = searcher.search(cmd);
            printer.print(searchResult);
        }
    }
}
