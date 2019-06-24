package com.movie.view;

import com.movie.domain.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Printer {
    private static Logger logger = LoggerFactory.getLogger(Printer.class);

    public void print(SearchResult searchResult){

        long hitsLength = searchResult.getHitsLength();

        logger.info("검색 결과 : "+ hitsLength +" / " + searchResult.getTotalHits().value);

        List docs = searchResult.getDocResults();
        docs.stream().forEach(System.out::println);
    }
}
